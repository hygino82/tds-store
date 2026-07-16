import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { InsertProductDto, ProductDto, ProductPage } from '../types/product.types';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private readonly API_URL = 'http://localhost:8080/api/v1/product';

  constructor(private readonly http: HttpClient) {}

  salvarProduto(produto: InsertProductDto): Observable<ProductDto> {
    return this.http.post<ProductDto>(this.API_URL, produto);
  }

  buscarProdutos(brand: string, size: string): Observable<ProductPage> {
    return this.http.get<ProductPage>(`${this.API_URL}?brand=${brand}&size=${size}`);
  }
}
