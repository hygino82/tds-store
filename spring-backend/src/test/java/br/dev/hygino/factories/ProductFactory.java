package br.dev.hygino.factories;

import br.dev.hygino.dtos.RequestProductDto;
import br.dev.hygino.entities.Product;
import br.dev.hygino.enums.Color;

public class ProductFactory {

    public static RequestProductDto createRequestProduct() {
        return new RequestProductDto("Jaqueta Jeans", "Lee", Color.GREY, "7", 35.0, 9);
    }

    public static Product createProduct() {
        return new Product(21L, "Jaqueta Jeans", "Lee", Color.GREY, "7", 35.0, 9);
    }
}
