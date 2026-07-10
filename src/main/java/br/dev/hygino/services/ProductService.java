package br.dev.hygino.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.dev.hygino.dtos.RequestProductDto;
import br.dev.hygino.dtos.ResponseProductDto;
import br.dev.hygino.entities.Product;
import br.dev.hygino.repositories.ProductRepository;

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
        repository.save(product);
        return new ResponseProductDto(product);
    }

    private void dtoToEntity(RequestProductDto request, Product product) {
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setAmount(request.amount());
        product.setBrand(request.brand());
        product.setColor(request.color());
        product.setSize(request.size());
    }

    @Transactional(readOnly = true)
    public ResponseProductDto getProductById(long id) {
        final Product res = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
        return new ResponseProductDto(res);
    }

    @Transactional(readOnly = true)
    public Page<ResponseProductDto> getProducts(Pageable pageable) {
        return repository.findAll(pageable)
                .map(ResponseProductDto::new);
    }
}
