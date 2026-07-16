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

export type ProductPage = {
  content: ProductDto[];
  page: {
    totalElements: number;
    totalPages: number;
    size: number;
    number: number;
  };
};

export const colorMap: Record<string, string> = {
  RED: '#ff0000',
  BLUE: '#0000ff',
  GREEN: '#008000',
  GREY: '#808080',
  BLACK: '#000000',
  WHITE: '#ffffff',
  YELLOW: '#ffff00',
  PINK: '#ffc0cb',
  PURPLE: '#800080',
  ORANGE: '#ffa500',
};

export const colorNameMap: Record<string, string> = {
  RED: 'Vermelho',
  BLUE: 'Azul',
  GREEN: 'Verde',
  GREY: 'Cinza',
  BLACK: 'Preto',
  WHITE: 'Branco',
  YELLOW: 'Amarelo',
  PINK: 'Rosa',
  PURPLE: 'Roxo',
  ORANGE: 'Laranja',
};

export type BrandReportDto = {
  brand: string;
  amount: number;
};

export type SizeReportDto = {
  size: string;
  amount: number;
};

export type ColorReportDto = {
  color: string;
  amount: number;
};

export type ProductReportDto = {
  brandSummary: BrandReportDto[];
  sizeSummary: SizeReportDto[];
  colorSummary: ColorReportDto[];
};
