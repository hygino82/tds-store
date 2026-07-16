import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ProductList } from "./page/product-list/product-list";
import { ProductResume } from "./page/product-resume/product-resume";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, ProductList, ProductResume],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('angular-frontend');
}
