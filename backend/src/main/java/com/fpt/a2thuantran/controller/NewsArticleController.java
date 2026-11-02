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

import com.fpt.a2thuantran.dto.NewsArticleRequest;
import com.fpt.a2thuantran.dto.NewsArticleResponse;
import com.fpt.a2thuantran.security.AuthUtil;
import com.fpt.a2thuantran.service.NewsArticleService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/news")
public class NewsArticleController {

    private final NewsArticleService newsArticleService;

    public NewsArticleController(NewsArticleService newsArticleService) {
        this.newsArticleService = newsArticleService;
    }

    // ----- public, no login needed, only active news is shown -----

    @GetMapping("/public")
    public ResponseEntity<List<NewsArticleResponse>> getActiveNewsForPublic(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer categoryId) {
        return ResponseEntity.ok(newsArticleService.getActiveNewsForPublic(keyword, categoryId));
    }

    @GetMapping("/public/{id}")
    public ResponseEntity<NewsArticleResponse> getActiveNewsDetailForPublic(@PathVariable("id") Integer newsArticleId) {
        return ResponseEntity.ok(newsArticleService.getActiveNewsDetailForPublic(newsArticleId));
    }

    // ----- used by Staff, login required -----

    @GetMapping
    public ResponseEntity<List<NewsArticleResponse>> getAllNewsForStaff(
            @RequestParam(required = false) String keyword,
            HttpServletRequest httpRequest) {
        AuthUtil.requireStaffRole(httpRequest);
        return ResponseEntity.ok(newsArticleService.getAllNewsForStaff(keyword));
    }

    @GetMapping("/tags")
    public ResponseEntity<List<String>> getAllTagNames(HttpServletRequest httpRequest) {
        AuthUtil.requireStaffRole(httpRequest);
        return ResponseEntity.ok(newsArticleService.getAllTagNames());
    }

    @GetMapping("/my-history")
    public ResponseEntity<List<NewsArticleResponse>> getMyNewsHistory(HttpServletRequest httpRequest) {
        AuthUtil.requireStaffRole(httpRequest);
        Integer currentAccountId = AuthUtil.getCurrentAccountId(httpRequest);
        return ResponseEntity.ok(newsArticleService.getNewsHistoryOfAccount(currentAccountId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsArticleResponse> getNewsById(@PathVariable("id") Integer newsArticleId,
                                                             HttpServletRequest httpRequest) {
        AuthUtil.requireStaffRole(httpRequest);
        return ResponseEntity.ok(newsArticleService.getNewsById(newsArticleId));
    }

    @PostMapping
    public ResponseEntity<NewsArticleResponse> createNews(@Valid @RequestBody NewsArticleRequest request,
                                                            HttpServletRequest httpRequest) {
        AuthUtil.requireStaffRole(httpRequest);
        Integer currentAccountId = AuthUtil.getCurrentAccountId(httpRequest);
        NewsArticleResponse createdNews = newsArticleService.createNews(request, currentAccountId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNews);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NewsArticleResponse> updateNews(@PathVariable("id") Integer newsArticleId,
                                                            @Valid @RequestBody NewsArticleRequest request,
                                                            HttpServletRequest httpRequest) {
        AuthUtil.requireStaffRole(httpRequest);
        Integer currentAccountId = AuthUtil.getCurrentAccountId(httpRequest);
        return ResponseEntity.ok(newsArticleService.updateNews(newsArticleId, request, currentAccountId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable("id") Integer newsArticleId,
                                            HttpServletRequest httpRequest) {
        AuthUtil.requireStaffRole(httpRequest);
        newsArticleService.deleteNews(newsArticleId);
        return ResponseEntity.noContent().build();
    }
}
