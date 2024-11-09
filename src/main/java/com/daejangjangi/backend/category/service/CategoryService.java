package com.daejangjangi.backend.category.service;

import com.daejangjangi.backend.category.domain.Category;
import com.daejangjangi.backend.category.exception.NotManagedCategoryException;
import com.daejangjangi.backend.category.repository.CategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

  private final CategoryRepository categoryRepository;

  /**
   * 카테고리 조회 by 카테고리명 목록
   *
   * @param names 카테고리명 목록
   * @return List<Category>
   */
  public List<Category> findByNames(List<String> names) {
    List<Category> categories = categoryRepository.findByNameIn(names);
    if (names.size() != categories.size()) {
      throw new NotManagedCategoryException();
    }
    return categories;
  }

  public Category findByName(String name) {
    return categoryRepository.findByName(name).orElseThrow(NotManagedCategoryException::new);
  }
}
