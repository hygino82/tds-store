package br.dev.hygino.controllers;

import br.dev.hygino.dtos.RequestChangeProductAmount;
import br.dev.hygino.dtos.RequestProductDto;
import br.dev.hygino.dtos.ResponseProductDto;
import br.dev.hygino.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/product")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ResponseProductDto> createProduct(@RequestBody RequestProductDto request) {
        ResponseProductDto response = service.createProduct(request);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseProductDto> getById(@PathVariable long id) {
        ResponseProductDto res = service.getProductById(id);
        return ResponseEntity.status(200).body(res);
    }

    @GetMapping
    public ResponseEntity<Page<ResponseProductDto>> getProducts(
            Pageable pageable,
            @RequestParam(defaultValue = "") String brand,
            @RequestParam(defaultValue = "") String size) {
        logger.info("Brand: {}", brand);
        logger.info("Size: {}", size);
        Page<ResponseProductDto> res = service.getProducts(pageable, brand, size);
        return ResponseEntity.status(200).body(res);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseProductDto> updateProduct(@PathVariable long id, @RequestBody RequestProductDto request) {
        ResponseProductDto response = service.updateProduct(id, request);
        logger.info("Produto atualizado: {}", response);
        return ResponseEntity.status(200).body(response);
    }

    @PatchMapping
    public ResponseEntity<ResponseProductDto> updateProductAmount(@RequestBody RequestChangeProductAmount request) {
        ResponseProductDto response = service.changeProductAmount(request);
        return ResponseEntity.status(200).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeProductById(@PathVariable long id) {
        service.removeProduct(id);
        return ResponseEntity.status(400).build();
    }
}
