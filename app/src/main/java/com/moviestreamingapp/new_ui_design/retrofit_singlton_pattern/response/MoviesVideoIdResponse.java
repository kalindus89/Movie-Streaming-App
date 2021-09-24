package com.moviestreamingapp.new_ui_design.retrofit_singlton_pattern.response;


import com.google.gson.annotations.SerializedName;
import com.moviestreamingapp.new_ui_design.retrofit_singlton_pattern.models.MovieModel;
import com.moviestreamingapp.new_ui_design.retrofit_singlton_pattern.models.VideoModel;

import java.util.List;

//this class is for getting multiple movies in results array
//https://api.themoviedb.org/3/movie/popular?api_key=459351d1311a26f26c93016a0d788db7

public class MoviesVideoIdResponse {



    @SerializedName("results")
    //@Expose() then you can uses different name for object
    private List<VideoModel> results;

    public List<VideoModel> getResults() {
        return results;
    }

    @Override
    public String toString() {
        return "MoviesVideoIdResponse{" +
                "results=" + results +
                '}';
    }
}
