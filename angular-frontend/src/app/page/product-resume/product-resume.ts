import { Component, OnInit, Inject, PLATFORM_ID, signal, computed } from '@angular/core';
import { isPlatformBrowser, CommonModule } from '@angular/common';
import { BaseChartDirective } from 'ng2-charts';
import { ProductService } from '../../services/product-service';
import { ProductReportDto } from '../../types/product.types';

// 1. IMPORTANTE: Importar e registrar o Chart.js
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

  // 2. Usando Signal para o relatório (Padrão Angular 22)
  report = signal<ProductReportDto | null>(null);

  // 3. Computeds reativos: eles se autodeclaram prontos assim que o report muda
  colorChartData = computed<ChartConfiguration<'pie'>['data'] | null>(() => {
    const data = this.report();
    if (!data) return null;
    return {
      labels: data.colorSummary.map(c => c.color),
      datasets: [{
        data: data.colorSummary.map(c => this.parseNumber(c.amount)),
        backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0', '#9966FF', '#FF9F40']
      }]
    };
  });

  brandChartData = computed<ChartConfiguration<'bar'>['data'] | null>(() => {
    const data = this.report();
    if (!data) return null;
    return {
      labels: data.brandSummary.map(b => b.brand),
      datasets: [{
        label: 'Quantidade',
        data: data.brandSummary.map(b => this.parseNumber(b.amount)),
        backgroundColor: '#36A2EB'
      }]
    };
  });

  sizeChartData = computed<ChartConfiguration<'bar'>['data'] | null>(() => {
    const data = this.report();
    if (!data) return null;
    return {
      labels: data.sizeSummary.map(s => s.size),
      datasets: [{
        label: 'Quantidade',
        data: data.sizeSummary.map(s => this.parseNumber(s.amount)),
        backgroundColor: '#4BC0C0'
      }]
    };
  });

  // Configurações estáticas permanecem iguais
  pieOptions: ChartConfiguration<'pie'>['options'] = {
    responsive: true,
    maintainAspectRatio: false
  };

  barOptions: ChartConfiguration<'bar'>['options'] = {
    indexAxis: 'y',
    responsive: true,
    maintainAspectRatio: false
  };

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
        console.log('Relatório recebido com sucesso:', data);
        // Atualiza o Signal. Os "computeds" acima vão recalcular automaticamente!
        this.report.set(data);
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
    return isNaN(n) ? 0 : n;
  }
}