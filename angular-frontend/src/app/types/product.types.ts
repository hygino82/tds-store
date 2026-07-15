export type InsertProductDto = {
  brand: string;
  description: string;
  price: number;
  amount: number;
  color: string;
  size: string;
};

export type ProductDto = {
  id: number;
  brand: string;
  description: string;
  price: number;
  amount: number;
  color: string;
  size: string;
  createdAt: string;
  updatedAt: string;
};
