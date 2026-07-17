import { Component, OnInit, Inject, PLATFORM_ID, signal, computed } from '@angular/core';
import { isPlatformBrowser, CommonModule } from '@angular/common';
import { BaseChartDirective } from 'ng2-charts';
import { ProductService } from '../../services/product-service';
import { colorNameMap, ProductReportDto } from '../../types/product.types';

import { Chart, registerables, ChartConfiguration } from 'chart.js';
Chart.register(...registerables);

@Component({
  selector: 'app-product-resume',
  standalone: true,
  imports: [CommonModule, BaseChartDirective],
  templateUrl: './product-resume.html',
  styleUrls: ['./product-resume.css']
})
export class ProductResume implements OnInit {
  isBrowser = false;

  report = signal<ProductReportDto | null>(null);

  pieOptions: ChartConfiguration<'pie'>['options'] = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: { position: 'bottom' }
    }
  };

  barOptions: ChartConfiguration<'bar'>['options'] = {
    indexAxis: 'y',
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: { display: false }
    }
  };

  // Helper para pegar o valor independente se vier quantity ou amount
  private getQty(item: any): number {
    const raw = item?.quantity?? item?.amount?? 0;
    if (typeof raw === 'number') return raw;
    // se vier como string "1.200,50"
    return this.parseNumber(raw);
  }

  colorChartData = computed<ChartConfiguration<'pie'>['data'] | null>(() => {
    const data = this.report();
    if (!data?.colorSummary?.length) return null;

    return {
      labels: data.colorSummary.map((c: any) => colorNameMap[c.color?.toUpperCase()] ?? c.color),
      datasets: [
        {
          data: data.colorSummary.map((c: any) => this.getQty(c)),
          backgroundColor: [
            '#111827', // BLACK
            '#2563eb', // BLUE
            '#16a34a', // GREEN
            '#9ca3af', // GREY
            '#dc2626', // RED
            '#e5e7eb', // WHITE
            '#eab308' // YELLOW
          ],
          borderWidth: 2
        }
      ]
    };
  });

  brandChartData = computed<ChartConfiguration<'bar'>['data'] | null>(() => {
    const data = this.report();
    if (!data?.brandSummary?.length) return null;

    const sorted = [...data.brandSummary].sort((a: any, b: any) => this.getQty(b) - this.getQty(a));

    return {
      labels: sorted.map((b: any) => b.brand),
      datasets: [
        {
          label: 'Quantidade',
          data: sorted.map((b: any) => this.getQty(b)),
          backgroundColor: '#36A2EB'
        }
      ]
    };
  });

  sizeChartData = computed<ChartConfiguration<'bar'>['data'] | null>(() => {
    const data = this.report();
    if (!data?.sizeSummary?.length) return null;

    const sorted = [...data.sizeSummary].sort((a: any, b: any) => Number(a.size) - Number(b.size));

    return {
      labels: sorted.map((s: any) => `Tam. ${s.size}`),
      datasets: [
        {
          label: 'Quantidade',
          data: sorted.map((s: any) => this.getQty(s)),
          backgroundColor: '#4BC0C0'
        }
      ]
    };
  });

  constructor(
    @Inject(PLATFORM_ID) private platformId: Object,
    private readonly productService: ProductService
  ) {
    this.isBrowser = isPlatformBrowser(this.platformId);
  }

  ngOnInit(): void {
    if (this.isBrowser) {
      this.getReport();
    }
  }

  getReport(): void {
    this.productService.gerarRelatorio().subscribe({
      next: (data) => {
        console.log('Relatório recebido:', data);
        this.report.set(data);
        // debug: veja se agora vem com valores
        console.log('colorChartData', this.colorChartData());
      },
      error: (err) => console.error('Erro ao buscar o relatório:', err)
    });
  }

  private parseNumber(value: any): number {
    if (value == null) return 0;
    const s = String(value).trim();
    if (s === '') return 0;
    const only = s.replace(/\s/g, '');
    const lastComma = only.lastIndexOf(',');
    const lastDot = only.lastIndexOf('.');
    let normalized = only;
    if (lastComma > lastDot) {
      normalized = only.replace(/\./g, '').replace(',', '.');
    } else {
      normalized = only.replace(/,/g, '');
    }
    const n = Number(normalized);
    return isNaN(n)? 0 : n;
  }
}