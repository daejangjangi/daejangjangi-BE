package com.daejangjangi.backend.category.service;

import com.daejangjangi.backend.category.repository.CategoryRepository;
import com.daejangjangi.backend.category.exception.NotManagedCategoryException;
import com.daejangjangi.backend.category.domain.entity.Category;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

  private final CategoryRepository categoryRepository;

  public List<Category> findByNames(List<String> names) {
    List<Category> categories = categoryRepository.findByNameIn(names);
    if (names.size() != categories.size()) {
      throw new NotManagedCategoryException();
    }
    return categories;
  }
}
