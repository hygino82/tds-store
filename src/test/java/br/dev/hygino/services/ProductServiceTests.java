package br.dev.hygino.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.dev.hygino.dtos.RequestProductDto;
import br.dev.hygino.entities.Product;
import br.dev.hygino.enums.Color;
import br.dev.hygino.factories.ProductFactory;
import br.dev.hygino.repositories.ProductRepository;
import br.dev.hygino.services.exceptions.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
class ProductServiceTests {

	@InjectMocks
	private ProductService productService;

	@Mock
	private ProductRepository productRepository;

	private RequestProductDto requestProduct;
	private Product createdProduct;

	private long existingId, nonExistingId;

	@BeforeEach
	public void setup() {
		existingId = 21L;
		nonExistingId = 1000L;
		requestProduct = ProductFactory.createRequestProduct();
		createdProduct = ProductFactory.createProduct();
	}

	@Test
	@DisplayName("Insert deve retornar um produto quando os dados forem válidos")
	public void insertShouldReturnProductWhenValidData() {

		when(productRepository.save(any(Product.class))).thenReturn(createdProduct);

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
		when(productRepository.findById(existingId)).thenReturn(Optional.of(createdProduct));

		var res = productService.getProductById(existingId);
		assertNotNull(res);
	}

	@Test
	@DisplayName("Deve lançar ResourceNotFoundException quando o id for inválido")
	public void getProductByIdShouldThrowResourceNotFoundExceptionWhenInvalidId() {
		when(productRepository.findById(nonExistingId)).thenReturn(Optional.empty());

		var res = assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(nonExistingId));
		assertEquals("Product not found!", res.getMessage());
	}
}