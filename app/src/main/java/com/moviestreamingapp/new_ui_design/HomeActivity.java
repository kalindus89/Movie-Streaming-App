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
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {


    PagerAdapterBanners pagerAdapterBanners;
    ViewPager banner_view_pager;
    TabLayout tab_indicator;
    List<MovieModel> movieModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tab_indicator = findViewById(R.id.tab_indicator);
        banner_view_pager = findViewById(R.id.banner_view_pager);

        setBannerSlider();


    }
      Timer  sliderTimer;
    private void setBannerSlider() {

        pagerAdapterBanners = new PagerAdapterBanners(this, movieModelList);
        banner_view_pager.setAdapter(pagerAdapterBanners);
        tab_indicator.setupWithViewPager(banner_view_pager);
        getPopularMovies();




        tab_indicator.setupWithViewPager(banner_view_pager,true);

        banner_view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                sliderTimer.cancel();
                sliderTimer = null;
                 sliderTimer= new Timer();
                sliderTimer.scheduleAtFixedRate(new AutoSlider(),6000,6000);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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

    class AutoSlider extends TimerTask{

        @Override
        public void run() {
            HomeActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(banner_view_pager.getCurrentItem() < movieModelList.size()-1){
                        banner_view_pager.setCurrentItem(banner_view_pager.getCurrentItem()+1);
                    }else{
                        banner_view_pager.setCurrentItem(0);
                    }
                }
            });
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        sliderTimer= new Timer();
        sliderTimer.scheduleAtFixedRate(new AutoSlider(),6000,6000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(sliderTimer!=null) {
            sliderTimer.cancel();
            sliderTimer = null;
        }

    }
}