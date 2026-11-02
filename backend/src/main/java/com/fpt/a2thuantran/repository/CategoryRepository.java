package com.fpt.a2thuantran.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpt.a2thuantran.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    List<Category> findByCategoryNameContainingIgnoreCase(String keyword);
}
