package com.moviestreamingapp.new_ui_design;

public class AllMovieCategories {

    String categoryTitle;
    int categoryId;

    public AllMovieCategories(String categoryTitle, int categoryId) {
        this.categoryTitle = categoryTitle;
        this.categoryId = categoryId;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public int getCategoryId() {
        return categoryId;
    }
}
