package com.fpt.a2thuantran.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpt.a2thuantran.entity.NewsArticle;

public interface NewsArticleRepository extends JpaRepository<NewsArticle, Integer> {

    // public page only shows active news
    List<NewsArticle> findByNewsStatusTrue();

    List<NewsArticle> findByNewsStatusTrueAndNewsTitleContainingIgnoreCase(String keyword);

    List<NewsArticle> findByNewsStatusTrueAndCategory_CategoryId(Integer categoryId);

    // staff screen: search among all news (active and inactive)
    List<NewsArticle> findByNewsTitleContainingIgnoreCase(String keyword);

    // staff screen: view news history created by the logged-in staff
    List<NewsArticle> findByCreatedBy_AccountId(Integer accountId);

    // used to check business rule before deleting a category
    long countByCategory_CategoryId(Integer categoryId);

    // used to check business rule before deleting an account
    long countByCreatedBy_AccountId(Integer accountId);
}
