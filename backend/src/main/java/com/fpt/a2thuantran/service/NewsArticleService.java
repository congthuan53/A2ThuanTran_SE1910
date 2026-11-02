package com.fpt.a2thuantran.service;

import java.util.List;

import com.fpt.a2thuantran.dto.NewsArticleRequest;
import com.fpt.a2thuantran.dto.NewsArticleResponse;

public interface NewsArticleService {

    // ----- public, no login needed, only active news -----
    List<NewsArticleResponse> getActiveNewsForPublic(String keyword, Integer categoryId);

    NewsArticleResponse getActiveNewsDetailForPublic(Integer newsArticleId);

    // ----- used by Staff -----
    List<NewsArticleResponse> getAllNewsForStaff(String keyword);

    NewsArticleResponse getNewsById(Integer newsArticleId);

    NewsArticleResponse createNews(NewsArticleRequest request, Integer currentAccountId);

    NewsArticleResponse updateNews(Integer newsArticleId, NewsArticleRequest request, Integer currentAccountId);

    void deleteNews(Integer newsArticleId);

    List<NewsArticleResponse> getNewsHistoryOfAccount(Integer accountId);

    // used by the "create/update news" form to suggest existing tags
    List<String> getAllTagNames();
}
