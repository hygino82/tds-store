import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductResume } from './product-resume';

describe('ProductResume', () => {
  let component: ProductResume;
  let fixture: ComponentFixture<ProductResume>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProductResume],
    }).compileComponents();

    fixture = TestBed.createComponent(ProductResume);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
