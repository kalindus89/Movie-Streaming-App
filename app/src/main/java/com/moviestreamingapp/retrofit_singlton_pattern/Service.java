package com.moviestreamingapp.retrofit_singlton_pattern;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//calls to retrofit
public class Service {


    public static Retrofit retrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl(Credentials.BASE_URl)
                    .addConverterFactory(GsonConverterFactory.create()).build();



    public static MovieApiInterface movieApiInterface = retrofitBuilder.create(MovieApiInterface.class);

    public MovieApiInterface getMovieApiInterface(){
        return  movieApiInterface;
    }


}
