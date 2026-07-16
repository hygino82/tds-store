package br.dev.hygino.dtos;

import br.dev.hygino.enums.Color;

public record ColorSummaryDto(
        Color color,
        Long quantity
) {
}