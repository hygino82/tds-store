package br.dev.hygino.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.dev.hygino.dtos.RequestProductDto;
import br.dev.hygino.dtos.ResponseProductDto;
import br.dev.hygino.services.ProductService;

@RestController
@RequestMapping("api/v1/product")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ResponseProductDto> createProduct(@RequestBody RequestProductDto request) {
        ResponseProductDto response = service.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseProductDto> getById(@PathVariable long id) {
        ResponseProductDto res = service.getProductById(id);
        return ResponseEntity.status(200).body(res);
    }

    @GetMapping
    public ResponseEntity<Page<ResponseProductDto>> getProducts(Pageable pageable) {
        Page<ResponseProductDto> res = service.getProducts(pageable);
        return ResponseEntity.status(200).body(res);
    }
}
