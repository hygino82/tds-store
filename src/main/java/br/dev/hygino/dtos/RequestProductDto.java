package br.dev.hygino.dtos;

import br.dev.hygino.enums.Color;
import jakarta.validation.constraints.*;

public record RequestProductDto(
        @NotBlank
        @Size(min = 3, max = 100)
        String description,
        
        @NotBlank
        @Size(min = 3, max = 100)
        String brand,
        
        @NotNull
        Color color,
        
        @NotNull
        Short size,
        
        @NotNull
        Double price,
        
        @NotNull
        Integer amount) {

}
