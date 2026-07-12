package br.dev.hygino.services;

import br.dev.hygino.dtos.RequestProductDto;
import br.dev.hygino.enums.Color;
import br.dev.hygino.factories.ProductFactory;
import br.dev.hygino.repositories.ProductRepository;
import br.dev.hygino.services.exceptions.DatabaseException;
import br.dev.hygino.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ProductServiceTestsIT {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    private RequestProductDto requestProduct;

    private long existingId, nonExistingId;

    @BeforeEach
    public void setup() {
        existingId = 1L;
        nonExistingId = 1000L;
        requestProduct = ProductFactory.createRequestProduct();
    }

    @Test
    @DisplayName("Insert deve retornar um produto quando os dados forem válidos")
    public void insertShouldReturnProductWhenValidData() {
        var res = productService.createProduct(requestProduct);
        assertNotNull(res);
        assertEquals(Color.GREY, res.color());
        assertEquals("Lee", res.brand());
        assertEquals(35.0, res.price());
        assertEquals(9, res.amount());
        assertEquals("7", res.size());
    }

    @Test
    @DisplayName("Deve retornar um produto quando o id for válido")
    public void getProductByIdShouldReturnProductWhenValidId() {
        var res = productService.getProductById(existingId);
        assertNotNull(res);
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException quando o id for inválido")
    public void getProductByIdShouldThrowResourceNotFoundExceptionWhenInvalidId() {
        var res = assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(nonExistingId));
        assertEquals("Product not found!", res.getMessage());
    }

    @Test
    @DisplayName("Deve lançar DatabaseException quando o id for inválido")
    public void updateProductShouldThrowDatabaseExceptionWhenInvalidId() {
        var res = assertThrows(DatabaseException.class, () -> productService.updateProduct(nonExistingId, requestProduct));
        assertEquals("Product does not exists!", res.getMessage());
    }
}