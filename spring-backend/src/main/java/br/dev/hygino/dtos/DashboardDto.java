package br.dev.hygino.dtos;

import java.util.List;

public record DashboardDto(
        List<ColorSummaryDto> colorSummary,
        List<BrandSummaryDto> brandSummary,
        List<SizeSummaryDto> sizeSummary
) {
}
