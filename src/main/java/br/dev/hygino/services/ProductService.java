package br.dev.hygino.services;

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
}
