package com.fpt.a2thuantran.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class NewsArticleRequest {

    @NotBlank(message = "Tieu de khong duoc de trong")
    private String newsTitle;

    private String headline;

    @NotBlank(message = "Noi dung khong duoc de trong")
    private String newsContent;

    private String newsSource;

    @NotNull(message = "Danh muc khong duoc de trong")
    private Integer categoryId;

    @NotNull(message = "Trang thai khong duoc de trong")
    private Boolean newsStatus;

    // list of tag names, for example ["Sport", "Technology"]
    // if a tag name does not exist yet, the service will create a new Tag for it
    private List<String> tagNames;

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public String getNewsSource() {
        return newsSource;
    }

    public void setNewsSource(String newsSource) {
        this.newsSource = newsSource;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Boolean getNewsStatus() {
        return newsStatus;
    }

    public void setNewsStatus(Boolean newsStatus) {
        this.newsStatus = newsStatus;
    }

    public List<String> getTagNames() {
        return tagNames;
    }

    public void setTagNames(List<String> tagNames) {
        this.tagNames = tagNames;
    }
}
