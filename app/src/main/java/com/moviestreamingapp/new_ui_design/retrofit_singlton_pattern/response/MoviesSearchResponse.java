package com.moviestreamingapp.new_ui_design.retrofit_singlton_pattern.response;


import com.google.gson.annotations.SerializedName;
import com.moviestreamingapp.new_ui_design.retrofit_singlton_pattern.models.MovieModel;

import java.util.List;

//this class is for getting multiple movies in results array
//https://api.themoviedb.org/3/movie/popular?api_key=459351d1311a26f26c93016a0d788db7

public class MoviesSearchResponse {

    @SerializedName("total_results")
    private int total_results;

    @SerializedName("results")
    //@Expose() then you can uses different name for object
    private List<MovieModel> results;


    public int getTotal_results() {
        return total_results;
    }

    public List<MovieModel> getResults() {
        return results;
    }

    @Override
    public String toString() {
        return "MoviesResponsesdds{" +
                "total_results=" + total_results +
                ", results=" + results +
                '}';
    }
}
