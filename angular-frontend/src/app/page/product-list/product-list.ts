import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../services/product-service';
import { colorMap, colorNameMap, ProductPage } from '../../types/product.types';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './product-list.html',
  styleUrl: './product-list.css',
})
export class ProductList implements OnInit {
  productPage: ProductPage | null = null;

  readonly colorMap = colorMap;
  readonly colorNameMap = colorNameMap;

  constructor(private readonly productService: ProductService) {}

  ngOnInit() {
    this.carregarProdutos();
  }

  carregarProdutos() {
    this.productService.buscarProdutos('', '').subscribe({
      next: (productPage) => {
        this.productPage = productPage;
        console.log('Produtos carregados com sucesso:', productPage);
        console.log(productPage.content);
        console.log(Array.isArray(productPage.content));
        console.log(productPage.content.length);
      },
      error: (error) => {
        console.error('Erro ao carregar produtos:', error);
      },
    });
  }
}
