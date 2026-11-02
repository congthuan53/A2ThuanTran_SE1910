package com.fpt.a2thuantran.dto;

import java.time.LocalDateTime;
import java.util.List;

public class NewsArticleResponse {

    private Integer newsArticleId;
    private String newsTitle;
    private String headline;
    private String newsContent;
    private String newsSource;
    private Integer categoryId;
    private String categoryName;
    private Boolean newsStatus;
    private Integer createdById;
    private String createdByName;
    private LocalDateTime createdDate;
    private String updatedByName;
    private LocalDateTime modifiedDate;
    private List<String> tagNames;

    public NewsArticleResponse(Integer newsArticleId, String newsTitle, String headline, String newsContent,
                                String newsSource, Integer categoryId, String categoryName, Boolean newsStatus,
                                Integer createdById, String createdByName, LocalDateTime createdDate,
                                String updatedByName, LocalDateTime modifiedDate, List<String> tagNames) {
        this.newsArticleId = newsArticleId;
        this.newsTitle = newsTitle;
        this.headline = headline;
        this.newsContent = newsContent;
        this.newsSource = newsSource;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.newsStatus = newsStatus;
        this.createdById = createdById;
        this.createdByName = createdByName;
        this.createdDate = createdDate;
        this.updatedByName = updatedByName;
        this.modifiedDate = modifiedDate;
        this.tagNames = tagNames;
    }

    public Integer getNewsArticleId() {
        return newsArticleId;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public String getHeadline() {
        return headline;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public String getNewsSource() {
        return newsSource;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public Boolean getNewsStatus() {
        return newsStatus;
    }

    public Integer getCreatedById() {
        return createdById;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public String getUpdatedByName() {
        return updatedByName;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public List<String> getTagNames() {
        return tagNames;
    }
}
