package com.daejangjangi.backend.category;

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
