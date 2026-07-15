import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ProductForm } from "./page/product-form/product-form";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, ProductForm],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('angular-frontend');
}
