package br.dev.hygino.repositories;

import br.dev.hygino.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("""
            SELECT obj FROM Product obj
            WHERE obj.brand LIKE CONCAT('%', COALESCE(:brand, ''), '%')
              AND obj.size = COALESCE(:size, obj.size)
            """)
    Page<Product> findProducts(Pageable pageable, String brand, String size);
}
