package com.daejangjangi.backend.category.repository;

import com.daejangjangi.backend.category.domain.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

  List<Category> findByNameIn(List<String> names);
}
