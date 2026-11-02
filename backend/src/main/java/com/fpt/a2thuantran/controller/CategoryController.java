package com.fpt.a2thuantran.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.a2thuantran.dto.CategoryRequest;
import com.fpt.a2thuantran.dto.CategoryResponse;
import com.fpt.a2thuantran.security.AuthUtil;
import com.fpt.a2thuantran.service.CategoryService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // public, no login needed : used to show the category filter on the public news page
    @GetMapping("/public")
    public ResponseEntity<List<CategoryResponse>> getActiveCategories() {
        return ResponseEntity.ok(categoryService.getActiveCategories());
    }

    // everything below needs a Staff login
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories(
            @RequestParam(required = false) String keyword,
            HttpServletRequest httpRequest) {
        AuthUtil.requireStaffRole(httpRequest);
        return ResponseEntity.ok(categoryService.getAllCategories(keyword));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable("id") Integer categoryId,
                                                              HttpServletRequest httpRequest) {
        AuthUtil.requireStaffRole(httpRequest);
        return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest request,
                                                             HttpServletRequest httpRequest) {
        AuthUtil.requireStaffRole(httpRequest);
        CategoryResponse createdCategory = categoryService.createCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable("id") Integer categoryId,
                                                             @Valid @RequestBody CategoryRequest request,
                                                             HttpServletRequest httpRequest) {
        AuthUtil.requireStaffRole(httpRequest);
        return ResponseEntity.ok(categoryService.updateCategory(categoryId, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") Integer categoryId,
                                                HttpServletRequest httpRequest) {
        AuthUtil.requireStaffRole(httpRequest);
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }
}
