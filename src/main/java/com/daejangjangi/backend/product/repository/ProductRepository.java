package com.daejangjangi.backend.product.repository;

import com.daejangjangi.backend.product.domain.entity.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

  @Query(value = """
        SELECT DISTINCT p FROM Product p
        JOIN p.diseases pd ON p = pd.product
        JOIN pd.disease d ON pd.disease = d
        JOIN p.categories pc ON p = pc.product
        JOIN pc.category c ON pc.category = c
        WHERE 1=1
        AND (c.name IN :categoryNames OR d.name IN :diseaseNames)
        ORDER BY RAND()
        LIMIT :count
      """)
  List<Product> findMyProductList(
      @Param("diseaseNames") List<String> diseaseNames,
      @Param("categoryNames") List<String> categoryNames,
      @Param("count") int count
  );

  @Query(value = """
      SELECT DISTINCT p FROM Product p
      ORDER BY RAND()
      LIMIT :count
      """)
  List<Product> findRandomProductList(@Param("count") int count);
}
