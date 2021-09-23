package com.moviestreamingapp.new_ui_design.retrofit_singlton_pattern;

import com.moviestreamingapp.new_ui_design.retrofit_singlton_pattern.models.MovieModel;
import com.moviestreamingapp.new_ui_design.retrofit_singlton_pattern.response.MoviesSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApiInterface {

    @GET("search/movie")
    Call<MoviesSearchResponse> getSearchMovies(
            @Query("api_key") String api_key,
            @Query("query") String searchQuery,
            @Query("page") int pageNumber
    );

    //https://api.themoviedb.org/3/movie/550?api_key=459351d1311a26f26c93016a0d788db7
    @GET("movie/{movie_id}?")
    Call<MovieModel> getMovieFromId(
            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key
    );

    @GET("movie/now_playing")
    Call<MoviesSearchResponse> getNowPlayingMovies(
            @Query("api_key") String api_key
    );

    @GET("movie/popular")
    Call<MoviesSearchResponse> getPopularMovies(
            @Query("api_key") String api_key
    );

    @GET("movie/upcoming")
    Call<MoviesSearchResponse> getUpcomingMovies(
            @Query("api_key") String api_key
    );

    @GET("movie/top_rated")
    Call<MoviesSearchResponse> getTopRatedMovies(
            @Query("api_key") String api_key
    );

}
