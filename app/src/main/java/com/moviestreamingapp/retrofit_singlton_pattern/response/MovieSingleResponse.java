package com.moviestreamingapp.retrofit_singlton_pattern.response;


import com.google.gson.annotations.SerializedName;
import com.moviestreamingapp.retrofit_singlton_pattern.models.MovieModel;

import java.util.List;

//this class is for getting 1 movie data
//https://api.themoviedb.org/3/movie/550?api_key=459351d1311a26f26c93016a0d788db7
// id=550

public class MovieSingleResponse {


    @SerializedName("results")
    private MovieModel results;

    public MovieModel getResults() {
        return results;
    }

    @Override
    public String toString() {
        return "MoviesIdResponse{" +
                "results=" + results +
                '}';
    }
}
