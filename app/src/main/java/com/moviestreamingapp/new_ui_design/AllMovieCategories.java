package com.moviestreamingapp.new_ui_design;

import com.moviestreamingapp.new_ui_design.retrofit_singlton_pattern.models.MovieModel;

import java.util.List;

public class AllMovieCategories {

    String categoryTitle;
    int categoryId;
    List<MovieModel> movieModelList = null;

    public AllMovieCategories(String categoryTitle, int categoryId,List<MovieModel> movieModelList) {
        this.categoryTitle = categoryTitle;
        this.categoryId = categoryId;
        this.movieModelList = movieModelList;
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

    public List<MovieModel> getMovieModelList() {
        return movieModelList;
    }

    public void setMovieModelList(List<MovieModel> movieModelList) {
        this.movieModelList = movieModelList;
    }
}
