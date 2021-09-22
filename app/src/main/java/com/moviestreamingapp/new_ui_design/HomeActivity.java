package com.moviestreamingapp.new_ui_design;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;
import com.moviestreamingapp.R;
import com.moviestreamingapp.new_ui_design.retrofit_singlton_pattern.Credentials;
import com.moviestreamingapp.new_ui_design.retrofit_singlton_pattern.MovieApiInterface;
import com.moviestreamingapp.new_ui_design.retrofit_singlton_pattern.ServiceClass;
import com.moviestreamingapp.new_ui_design.retrofit_singlton_pattern.models.MovieModel;
import com.moviestreamingapp.new_ui_design.retrofit_singlton_pattern.response.MoviesSearchResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {


    PagerAdapterBanners pagerAdapterBanners;
    ViewPager banner_view_pager;
    TabLayout tabLayout;
    List<MovieModel> movieModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tabLayout = findViewById(R.id.tab_layout_home);
        banner_view_pager = findViewById(R.id.banner_view_pager);

        setBannerSlider();


    }

    private void setBannerSlider() {

        pagerAdapterBanners = new PagerAdapterBanners(this, movieModelList);
        banner_view_pager.setAdapter(pagerAdapterBanners);
        getPopularMovies();
    }

    private void getPopularMovies() {

        MovieApiInterface movieApiInterface = ServiceClass.getMovieApiInterface();

        movieApiInterface.getPopularMovies(Credentials.API_KEY).enqueue(new Callback<MoviesSearchResponse>() {
            @Override
            public void onResponse(Call<MoviesSearchResponse> call, Response<MoviesSearchResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().getResults().size() > 6) {
                        for (int i = 0; i < 6; i++) {
                            movieModelList.add(response.body().getResults().get(i));
                        }
                    } else {
                        movieModelList.addAll(response.body().getResults());
                    }
                    pagerAdapterBanners.notifyDataSetChanged();

                } else {

                    try {
                        Log.v("Tag", response.errorBody().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onFailure(Call<MoviesSearchResponse> call, Throwable t) {

            }
        });
    }
}