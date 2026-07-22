package br.dev.hygino.repositories;

import br.dev.hygino.dtos.BrandSummaryDto;
import br.dev.hygino.dtos.ColorSummaryDto;
import br.dev.hygino.dtos.SizeSummaryDto;
import br.dev.hygino.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query("""
			SELECT obj FROM Product obj
			WHERE obj.brand LIKE CONCAT('%', COALESCE(:brand, ''), '%')
			  AND obj.size LIKE CONCAT('%', COALESCE(:size, ''), '%')
			""")
	Page<Product> findProducts(Pageable pageable, String brand, String size);

	@Query("""
			    SELECT new br.dev.hygino.dtos.ColorSummaryDto(
			        p.color,
			        SUM(p.amount)
			    )
			    FROM Product p
			    GROUP BY p.color
			    ORDER BY p.color
			""")
	List<ColorSummaryDto> getAmountByColor();

	@Query("""
			    SELECT new br.dev.hygino.dtos.BrandSummaryDto(
			        p.brand,
			        SUM(p.amount)
			    )
			    FROM Product p
			    GROUP BY p.brand
			    ORDER BY p.brand
			""")
	List<BrandSummaryDto> getAmountByBrand();

	@Query("""
			    SELECT new br.dev.hygino.dtos.SizeSummaryDto(
			        p.size,
			        SUM(p.amount)
			    )
			    FROM Product p
			    GROUP BY p.size
			    ORDER BY p.size
			""")
	List<SizeSummaryDto> getAmountBySize();

	@Query("""
			    SELECT new br.dev.hygino.dtos.CategorySummaryDto(
			        p.category,
			        SUM(p.amount)
			    )
			    FROM Product p
			    GROUP BY p.category
			    ORDER BY p.category
			""")
	List<ColorSummaryDto> getAmountByCategory();
}
