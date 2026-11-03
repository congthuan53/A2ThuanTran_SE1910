package com.fpt.a2thuantran.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fpt.a2thuantran.dto.CategoryRequest;
import com.fpt.a2thuantran.dto.CategoryResponse;
import com.fpt.a2thuantran.entity.Category;
import com.fpt.a2thuantran.exception.BadRequestException;
import com.fpt.a2thuantran.exception.ResourceNotFoundException;
import com.fpt.a2thuantran.repository.CategoryRepository;
import com.fpt.a2thuantran.repository.NewsArticleRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final NewsArticleRepository newsArticleRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, NewsArticleRepository newsArticleRepository) {
        this.categoryRepository = categoryRepository;
        this.newsArticleRepository = newsArticleRepository;
    }

    @Override
    public List<CategoryResponse> getAllCategories(String keyword) {
        List<Category> categories;

        if (StringUtils.hasText(keyword)) {
            categories = categoryRepository.findByCategoryNameContainingIgnoreCase(keyword);
        } else {
            categories = categoryRepository.findAll();
        }

        return categories.stream()
                .map(this::toCategoryResponse)
                .toList();
    }

    @Override
    public List<CategoryResponse> getActiveCategories() {
        return categoryRepository.findByIsActiveTrue().stream()
                .map(this::toCategoryResponse)
                .toList();
    }

    @Override
    public CategoryResponse getCategoryById(Integer categoryId) {
        Category category = findCategoryOrThrow(categoryId);
        return toCategoryResponse(category);
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        Category category = new Category();
        category.setCategoryName(request.getCategoryName());
        category.setCategoryDescription(request.getCategoryDescription());
        category.setParentCategoryId(request.getParentCategoryId());
        category.setIsActive(request.getIsActive());

        Category savedCategory = categoryRepository.save(category);
        return toCategoryResponse(savedCategory);
    }

    @Override
    public CategoryResponse updateCategory(Integer categoryId, CategoryRequest request) {
        Category category = findCategoryOrThrow(categoryId);

        category.setCategoryName(request.getCategoryName());
        category.setCategoryDescription(request.getCategoryDescription());
        category.setParentCategoryId(request.getParentCategoryId());
        category.setIsActive(request.getIsActive());

        Category updatedCategory = categoryRepository.save(category);
        return toCategoryResponse(updatedCategory);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = findCategoryOrThrow(categoryId);

        long newsInThisCategory = newsArticleRepository.countByCategory_CategoryId(categoryId);
        if (newsInThisCategory > 0) {
            throw new BadRequestException("Không thể xóa danh mục này vì đã có bài viết sử dụng");
        }

        categoryRepository.delete(category);
    }

    private Category findCategoryOrThrow(Integer categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy danh mục với id = " + categoryId));
    }

    private CategoryResponse toCategoryResponse(Category category) {
        return new CategoryResponse(
                category.getCategoryId(),
                category.getCategoryName(),
                category.getCategoryDescription(),
                category.getParentCategoryId(),
                category.getIsActive()
        );
    }
}
