package br.dev.hygino.dtos;

import br.dev.hygino.enums.Category;

public record CategorySummaryDto(Category Category, Long quantity) {
}