package br.dev.hygino.dtos;

import br.dev.hygino.entities.Product;
import br.dev.hygino.enums.Color;
import java.time.LocalDateTime;

public record ResponseProductDto(
        Long id,
        String description,
        String brand,
        Color color,
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
                entity.getSize(),
                entity.getPrice(),
                entity.getAmount(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
