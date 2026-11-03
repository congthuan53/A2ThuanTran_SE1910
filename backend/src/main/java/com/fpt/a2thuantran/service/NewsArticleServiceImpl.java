package com.fpt.a2thuantran.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fpt.a2thuantran.dto.NewsArticleRequest;
import com.fpt.a2thuantran.dto.NewsArticleResponse;
import com.fpt.a2thuantran.entity.Category;
import com.fpt.a2thuantran.entity.NewsArticle;
import com.fpt.a2thuantran.entity.SystemAccount;
import com.fpt.a2thuantran.entity.Tag;
import com.fpt.a2thuantran.exception.ResourceNotFoundException;
import com.fpt.a2thuantran.repository.CategoryRepository;
import com.fpt.a2thuantran.repository.NewsArticleRepository;
import com.fpt.a2thuantran.repository.SystemAccountRepository;
import com.fpt.a2thuantran.repository.TagRepository;

@Service
public class NewsArticleServiceImpl implements NewsArticleService {

    private final NewsArticleRepository newsArticleRepository;
    private final CategoryRepository categoryRepository;
    private final SystemAccountRepository accountRepository;
    private final TagRepository tagRepository;

    public NewsArticleServiceImpl(NewsArticleRepository newsArticleRepository,
                                   CategoryRepository categoryRepository,
                                   SystemAccountRepository accountRepository,
                                   TagRepository tagRepository) {
        this.newsArticleRepository = newsArticleRepository;
        this.categoryRepository = categoryRepository;
        this.accountRepository = accountRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public List<NewsArticleResponse> getActiveNewsForPublic(String keyword, Integer categoryId) {
        List<NewsArticle> newsList;

        if (StringUtils.hasText(keyword)) {
            newsList = newsArticleRepository.findByNewsStatusTrueAndNewsTitleContainingIgnoreCase(keyword);
        } else if (categoryId != null) {
            newsList = newsArticleRepository.findByNewsStatusTrueAndCategory_CategoryId(categoryId);
        } else {
            newsList = newsArticleRepository.findByNewsStatusTrue();
        }

        return newsList.stream()
                .map(this::toNewsArticleResponse)
                .toList();
    }

    @Override
    public NewsArticleResponse getActiveNewsDetailForPublic(Integer newsArticleId) {
        NewsArticle newsArticle = findNewsOrThrow(newsArticleId);

        if (!Boolean.TRUE.equals(newsArticle.getNewsStatus())) {
            throw new ResourceNotFoundException("Bài viết này hiện không còn hoạt động");
        }

        return toNewsArticleResponse(newsArticle);
    }

    @Override
    public List<NewsArticleResponse> getAllNewsForStaff(String keyword) {
        List<NewsArticle> newsList;

        if (StringUtils.hasText(keyword)) {
            newsList = newsArticleRepository.findByNewsTitleContainingIgnoreCase(keyword);
        } else {
            newsList = newsArticleRepository.findAll();
        }

        return newsList.stream()
                .map(this::toNewsArticleResponse)
                .toList();
    }

    @Override
    public NewsArticleResponse getNewsById(Integer newsArticleId) {
        NewsArticle newsArticle = findNewsOrThrow(newsArticleId);
        return toNewsArticleResponse(newsArticle);
    }

    @Override
    public NewsArticleResponse createNews(NewsArticleRequest request, Integer currentAccountId) {
        Category category = findCategoryOrThrow(request.getCategoryId());
        SystemAccount currentAccount = findAccountOrThrow(currentAccountId);

        NewsArticle newsArticle = new NewsArticle();
        newsArticle.setNewsTitle(request.getNewsTitle());
        newsArticle.setHeadline(request.getHeadline());
        newsArticle.setNewsContent(request.getNewsContent());
        newsArticle.setNewsSource(request.getNewsSource());
        newsArticle.setCategory(category);
        newsArticle.setNewsStatus(request.getNewsStatus());
        newsArticle.setCreatedBy(currentAccount);
        newsArticle.setCreatedDate(LocalDateTime.now());
        newsArticle.setTags(findOrCreateTags(request.getTagNames()));

        NewsArticle savedNewsArticle = newsArticleRepository.save(newsArticle);
        return toNewsArticleResponse(savedNewsArticle);
    }

    @Override
    public NewsArticleResponse updateNews(Integer newsArticleId, NewsArticleRequest request, Integer currentAccountId) {
        NewsArticle newsArticle = findNewsOrThrow(newsArticleId);
        Category category = findCategoryOrThrow(request.getCategoryId());
        SystemAccount currentAccount = findAccountOrThrow(currentAccountId);

        newsArticle.setNewsTitle(request.getNewsTitle());
        newsArticle.setHeadline(request.getHeadline());
        newsArticle.setNewsContent(request.getNewsContent());
        newsArticle.setNewsSource(request.getNewsSource());
        newsArticle.setCategory(category);
        newsArticle.setNewsStatus(request.getNewsStatus());
        newsArticle.setUpdatedBy(currentAccount);
        newsArticle.setModifiedDate(LocalDateTime.now());
        newsArticle.setTags(findOrCreateTags(request.getTagNames()));

        NewsArticle updatedNewsArticle = newsArticleRepository.save(newsArticle);
        return toNewsArticleResponse(updatedNewsArticle);
    }

    @Override
    public void deleteNews(Integer newsArticleId) {
        NewsArticle newsArticle = findNewsOrThrow(newsArticleId);
        newsArticleRepository.delete(newsArticle);
    }

    @Override
    public List<NewsArticleResponse> getNewsHistoryOfAccount(Integer accountId) {
        return newsArticleRepository.findByCreatedBy_AccountId(accountId).stream()
                .map(this::toNewsArticleResponse)
                .toList();
    }

    @Override
    public List<String> getAllTagNames() {
        return tagRepository.findAll().stream()
                .map(Tag::getTagName)
                .toList();
    }

    // find an existing tag by name, or create a new one if it does not exist yet
    private List<Tag> findOrCreateTags(List<String> tagNames) {
        List<Tag> tags = new ArrayList<>();

        if (tagNames == null) {
            return tags;
        }

        for (String rawTagName : tagNames) {
            if (!StringUtils.hasText(rawTagName)) {
                continue;
            }

            String tagName = rawTagName.trim();
            Tag tag = tagRepository.findByTagNameIgnoreCase(tagName)
                    .orElseGet(() -> tagRepository.save(new Tag(tagName)));

            tags.add(tag);
        }

        return tags;
    }

    private NewsArticle findNewsOrThrow(Integer newsArticleId) {
        return newsArticleRepository.findById(newsArticleId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy bài viết với id = " + newsArticleId));
    }

    private Category findCategoryOrThrow(Integer categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy danh mục với id = " + categoryId));
    }

    private SystemAccount findAccountOrThrow(Integer accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy tài khoản với id = " + accountId));
    }

    private NewsArticleResponse toNewsArticleResponse(NewsArticle newsArticle) {
        List<String> tagNames = newsArticle.getTags().stream()
                .map(Tag::getTagName)
                .toList();

        String updatedByName = newsArticle.getUpdatedBy() != null
                ? newsArticle.getUpdatedBy().getAccountName()
                : null;

        return new NewsArticleResponse(
                newsArticle.getNewsArticleId(),
                newsArticle.getNewsTitle(),
                newsArticle.getHeadline(),
                newsArticle.getNewsContent(),
                newsArticle.getNewsSource(),
                newsArticle.getCategory().getCategoryId(),
                newsArticle.getCategory().getCategoryName(),
                newsArticle.getNewsStatus(),
                newsArticle.getCreatedBy().getAccountId(),
                newsArticle.getCreatedBy().getAccountName(),
                newsArticle.getCreatedDate(),
                updatedByName,
                newsArticle.getModifiedDate(),
                tagNames
        );
    }
}
