package br.dev.hygino.dtos;

import br.dev.hygino.enums.Category;
import br.dev.hygino.enums.Color;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RequestProductDto(
		@NotBlank @Size(min = 3, max = 100) String description,

		@NotBlank @Size(min = 3, max = 100) String brand,

		@NotNull Color color,

		@NotNull Category category,

		@NotNull String size,

		@NotNull Double price,

		@NotNull Integer amount, 
		
		String imageUrl) {
}
