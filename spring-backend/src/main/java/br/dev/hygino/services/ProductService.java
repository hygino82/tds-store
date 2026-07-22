package br.dev.hygino.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.dev.hygino.dtos.BrandSummaryDto;
import br.dev.hygino.dtos.ColorSummaryDto;
import br.dev.hygino.dtos.DashboardDto;
import br.dev.hygino.dtos.RequestChangeProductAmount;
import br.dev.hygino.dtos.RequestProductDto;
import br.dev.hygino.dtos.ResponseProductDto;
import br.dev.hygino.dtos.SizeSummaryDto;
import br.dev.hygino.entities.Product;
import br.dev.hygino.repositories.ProductRepository;
import br.dev.hygino.services.exceptions.DatabaseException;
import br.dev.hygino.services.exceptions.InvalidAmountException;
import br.dev.hygino.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {
	private final ProductRepository repository;

	public ProductService(ProductRepository repository) {
		this.repository = repository;
	}

	@Transactional
	public ResponseProductDto createProduct(RequestProductDto request) {
		var product = new Product();
		dtoToEntity(request, product);
		product = repository.save(product);
		return new ResponseProductDto(product);
	}

	private void dtoToEntity(RequestProductDto request, Product product) {
		product.setDescription(request.description());
		product.setPrice(request.price());
		product.setAmount(request.amount());
		product.setBrand(request.brand());
		product.setColor(request.color());
		product.setCategory(request.category());
		product.setSize(request.size());
	}

	@Transactional(readOnly = true)
	public ResponseProductDto getProductById(long id) {
		final Product res = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
		return new ResponseProductDto(res);
	}

	@Transactional(readOnly = true)
	public Page<ResponseProductDto> getProducts(Pageable pageable, String brand, String size) {
		final Page<Product> page = repository.findProducts(pageable, brand.isEmpty() ? null : brand,
				size.isEmpty() ? null : size);
		return page.map(ResponseProductDto::new);
	}

	@Transactional
	public ResponseProductDto updateProduct(long id, RequestProductDto dto) {
		try {
			final var product = repository.getReferenceById(id);
			dtoToEntity(dto, product);
			product.setUpdatedAt(LocalDateTime.now());
			repository.save(product);
			return new ResponseProductDto(product);
		} catch (EntityNotFoundException e) {
			throw new DatabaseException("Product does not exists!");
		}
	}

	@Transactional
	public ResponseProductDto changeProductAmount(RequestChangeProductAmount request) {
		try {
			final var product = repository.getReferenceById(request.id());
			final var newAmount = product.getAmount() + request.amount();

			if (newAmount < 0) {
				throw new InvalidAmountException("Wrong amount!");
			}

			product.setAmount(newAmount);
			product.setUpdatedAt(LocalDateTime.now());
			repository.save(product);
			return new ResponseProductDto(product);
		} catch (EntityNotFoundException e) {
			throw new DatabaseException("Product does not exists!");
		}
	}

	@Transactional
	public void removeProduct(long id) {
		repository.deleteById(id);
	}

	@Transactional(readOnly = true)
	public DashboardDto getSummary() {
		final List<ColorSummaryDto> colorSummary = repository.getAmountByColor();
		final List<BrandSummaryDto> brandSummary = repository.getAmountByBrand();
		final List<SizeSummaryDto> sizeSummary = repository.getAmountBySize();

		if (colorSummary.isEmpty() || brandSummary.isEmpty() || sizeSummary.isEmpty()) {
			throw new ResourceNotFoundException("Não existem produtos cadastrados!");
		}

		return new DashboardDto(colorSummary, brandSummary, sizeSummary);
	}
}
