package com.fpt.a2thuantran.service;

import java.util.List;

import com.fpt.a2thuantran.dto.CategoryRequest;
import com.fpt.a2thuantran.dto.CategoryResponse;

public interface CategoryService {

    List<CategoryResponse> getAllCategories(String keyword);

    // used by the public news page (no login needed) to build the category filter
    List<CategoryResponse> getActiveCategories();

    CategoryResponse getCategoryById(Integer categoryId);

    CategoryResponse createCategory(CategoryRequest request);

    CategoryResponse updateCategory(Integer categoryId, CategoryRequest request);

    void deleteCategory(Integer categoryId);
}
