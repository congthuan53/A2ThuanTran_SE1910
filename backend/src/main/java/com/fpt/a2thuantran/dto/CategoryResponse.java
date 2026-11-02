package com.fpt.a2thuantran.dto;

public class CategoryResponse {

    private Integer categoryId;
    private String categoryName;
    private String categoryDescription;
    private Integer parentCategoryId;
    private Boolean isActive;

    public CategoryResponse(Integer categoryId, String categoryName, String categoryDescription,
                             Integer parentCategoryId, Boolean isActive) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
        this.parentCategoryId = parentCategoryId;
        this.isActive = isActive;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public Integer getParentCategoryId() {
        return parentCategoryId;
    }

    public Boolean getIsActive() {
        return isActive;
    }
}
