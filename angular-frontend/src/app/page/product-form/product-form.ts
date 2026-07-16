import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { InsertProductDto, ProductPage } from '../../types/product.types';
import { ProductService } from '../../services/product-service';

@Component({
  standalone: true,
  selector: 'app-product-form',
  imports: [FormsModule],
  templateUrl: './product-form.html',
  styleUrl: './product-form.css',
})
export class ProductForm {
  description: string = '';
  brand: string = '';
  price: number = 0;
  amount: number = 0;
  color: string = 'BLUE';
  size: string = 'M';
  productPage: ProductPage | null = null;

  constructor(private readonly productService: ProductService) {}

  salvarProduto() {
    const produto: InsertProductDto = {
      description: this.description,
      brand: this.brand,
      price: this.price,
      amount: this.amount,
      color: this.color,
      size: this.size,
    };

    console.log('Produto a ser salvo:', produto);

    this.productService.salvarProduto(produto).subscribe({
      next: (savedProduct) => {
        console.log('Produto salvo com sucesso:', savedProduct);
        this.limparFormulario();
      },
      error: (error) => {
        console.error('Erro ao salvar produto:', error);
        console.log(error.status);
        console.log(error.error);
      },
    });
  }

  limparFormulario() {
    this.description = '';
    this.brand = '';
    this.price = 0;
    this.amount = 0;
    this.color = 'BLUE';
    this.size = 'M';
  }
}
