package com.moviestreamingapp.retrofit_singlton_pattern;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//calls to retrofit
public class ServiceClass {


    public static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl(Credentials.BASE_URl)
                    .addConverterFactory(GsonConverterFactory.create());

    public static Retrofit retrofit =retrofitBuilder.build();



    private static MovieApiInterface movieApiInterface = retrofit.create(MovieApiInterface.class);

    public static  MovieApiInterface getMovieApiInterface(){
        return  movieApiInterface;
    }


}
