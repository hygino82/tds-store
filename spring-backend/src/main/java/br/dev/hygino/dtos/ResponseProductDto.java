package br.dev.hygino.dtos;

import java.time.LocalDateTime;

import br.dev.hygino.entities.Product;
import br.dev.hygino.enums.Category;
import br.dev.hygino.enums.Color;

public record ResponseProductDto(
        Long id,
        String description,
        String brand,
        Color color,
        Category category,
        String size,
        Double price,
        Integer amount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {

    public ResponseProductDto(Product entity) {
        this(
                entity.getId(),
                entity.getDescription(),
                entity.getBrand(),
                entity.getColor(),
                entity.getCategory(),
                entity.getSize(),
                entity.getPrice(),
                entity.getAmount(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
