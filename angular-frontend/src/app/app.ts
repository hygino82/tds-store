import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ProductForm } from "./page/product-form/product-form";
import { ProductList } from "./page/product-list/product-list";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, ProductForm, ProductList],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('angular-frontend');
}
