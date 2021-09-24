package com.moviestreamingapp.new_ui_design;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
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
    TabLayout tab_indicator,tab_layout_home;

    List<MovieModel> movieModelList = new ArrayList<>();
    List<MovieModel> movieModelListPopular = new ArrayList<>();
    List<MovieModel> movieModelListUpComing = new ArrayList<>();
    List<MovieModel> movieModelListTopRated = new ArrayList<>();
    List<MovieModel> movieSearchList;

    RecyclerView main_recyclerView,category_recyclerView;
    MainRecyclerAdapter mainRecyclerAdapter;
    ItemRecyclerAdapter itemRecyclerAdapter ;
    List<AllMovieCategories> allMovieCategoriesList = new ArrayList<>();

    AppBarLayout appbar;
    NestedScrollView nested_scroll;
    Toolbar toolbar;
    TextView seeMore;
    String mainCategory;
    public int pageNumberStart=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tab_indicator = findViewById(R.id.tab_indicator);
        tab_layout_home = findViewById(R.id.tab_layout_home);
        banner_view_pager = findViewById(R.id.banner_view_pager);
        main_recyclerView = findViewById(R.id.main_recyclerView);
        category_recyclerView = findViewById(R.id.category_recyclerView);
        appbar = findViewById(R.id.appbar);
        nested_scroll = findViewById(R.id.nested_scroll);
        toolbar = findViewById(R.id.toolbar);
        seeMore = findViewById(R.id.seeMore);

        setSupportActionBar(toolbar);
        //setScrollDefaultState();


        tab_layout_home.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition()){
                    case 1:
                        showOtherTabMovies("Kids");
                        return;
                    case 2:
                        showOtherTabMovies("Comedy");
                        return;
                    case 3:
                        showOtherTabMovies("Action");
                        return;
                    case 4:
                        showOtherTabMovies("Drama");
                        return;
                    case 5:
                        showOtherTabMovies("Romantic");
                        return;
                    default:
                        showHomeTabMovies();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        allMovieCategoriesList.add(new AllMovieCategories("Now Playing", 1, movieModelList));
        allMovieCategoriesList.add(new AllMovieCategories("Popular", 2, movieModelListPopular));
        allMovieCategoriesList.add(new AllMovieCategories("UpComing", 3, movieModelListUpComing));
        allMovieCategoriesList.add(new AllMovieCategories("Top Rated", 4,movieModelListTopRated));

        setMain_recyclerView(allMovieCategoriesList);


        setBannerSlider();
        getPopularMovies();
        getUpcomingMovies();
        getTopRatedMovies();

        seeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSearchedMovies(mainCategory,(pageNumberStart+1));
                seeMore.setVisibility(View.GONE);
            }
        });

    }

    public void showHomeTabMovies(){
        category_recyclerView.setVisibility(View.GONE);
        main_recyclerView.setVisibility(View.VISIBLE);
        seeMore.setVisibility(View.GONE);

    }


    public void showOtherTabMovies(String category){
        mainCategory=category;
        pageNumberStart=1;

        seeMore.setVisibility(View.GONE);
        main_recyclerView.setVisibility(View.GONE);
        category_recyclerView.setVisibility(View.VISIBLE);

        movieSearchList = new ArrayList<>();
        movieSearchList.clear();


      //  RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
       // category_recyclerView.setLayoutManager(layoutManager);

        category_recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        itemRecyclerAdapter = new ItemRecyclerAdapter(this, movieSearchList);
        category_recyclerView.setAdapter(itemRecyclerAdapter);

        getSearchedMovies(category,pageNumberStart);

        /*category_recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if(!recyclerView.canScrollVertically(1)){

                    getSearchedMovies("kids",(pageNumberStart+1));
                   System.out.println(category+" aaaaaaa "+pageNumberStart);
                   // progressBar.setVisibility(View.VISIBLE);
                }

            }
        });*/

    }

    public void setMain_recyclerView(List<AllMovieCategories> allMovieCatList) {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        main_recyclerView.setLayoutManager(layoutManager);
        mainRecyclerAdapter = new MainRecyclerAdapter(this, allMovieCatList);
        main_recyclerView.setAdapter(mainRecyclerAdapter);
    }

    public void setScrollDefaultState(){
        //ui scroll back to original position
        nested_scroll.fullScroll(View.FOCUS_UP);
        nested_scroll.scrollTo(0,0);
        appbar.setExpanded(true);

    }

    Timer sliderTimer;

    private void setBannerSlider() {

        pagerAdapterBanners = new PagerAdapterBanners(this, movieModelList);
        banner_view_pager.setAdapter(pagerAdapterBanners);
        tab_indicator.setupWithViewPager(banner_view_pager);
        getNowPlayingMovies();


        tab_indicator.setupWithViewPager(banner_view_pager, true);

        banner_view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                sliderTimer.cancel();
                sliderTimer = null;
                sliderTimer = new Timer();
                sliderTimer.scheduleAtFixedRate(new AutoSlider(), 6000, 6000);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void getNowPlayingMovies() {

        MovieApiInterface movieApiInterface = ServiceClass.getMovieApiInterface();

        movieApiInterface.getNowPlayingMovies(Credentials.API_KEY).enqueue(new Callback<MoviesSearchResponse>() {
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
                    mainRecyclerAdapter.notifyDataSetChanged();

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

    private void getPopularMovies() {

        MovieApiInterface movieApiInterface = ServiceClass.getMovieApiInterface();

        movieApiInterface.getPopularMovies(Credentials.API_KEY).enqueue(new Callback<MoviesSearchResponse>() {
            @Override
            public void onResponse(Call<MoviesSearchResponse> call, Response<MoviesSearchResponse> response) {

                if (response.isSuccessful()) {

                    movieModelListPopular.addAll(response.body().getResults());
                    mainRecyclerAdapter.notifyDataSetChanged();

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

    private void getUpcomingMovies() {

        MovieApiInterface movieApiInterface = ServiceClass.getMovieApiInterface();

        movieApiInterface.getUpcomingMovies(Credentials.API_KEY).enqueue(new Callback<MoviesSearchResponse>() {
            @Override
            public void onResponse(Call<MoviesSearchResponse> call, Response<MoviesSearchResponse> response) {

                if (response.isSuccessful()) {

                    movieModelListUpComing.addAll(response.body().getResults());
                    mainRecyclerAdapter.notifyDataSetChanged();

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

    private void getTopRatedMovies() {

        MovieApiInterface movieApiInterface = ServiceClass.getMovieApiInterface();

        movieApiInterface.getTopRatedMovies(Credentials.API_KEY).enqueue(new Callback<MoviesSearchResponse>() {
            @Override
            public void onResponse(Call<MoviesSearchResponse> call, Response<MoviesSearchResponse> response) {

                if (response.isSuccessful()) {

                    movieModelListTopRated.addAll(response.body().getResults());
                    mainRecyclerAdapter.notifyDataSetChanged();

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
    private void getSearchedMovies(String searchTerm,int pageNumber) {


        pageNumberStart=pageNumber;

        MovieApiInterface movieApiInterface = ServiceClass.getMovieApiInterface();

        movieApiInterface.getSearchMovies(Credentials.API_KEY,searchTerm,pageNumber).enqueue(new Callback<MoviesSearchResponse>() {
            @Override
            public void onResponse(Call<MoviesSearchResponse> call, Response<MoviesSearchResponse> response) {

                if (response.isSuccessful()) {

                    movieSearchList.addAll(response.body().getResults());
                    itemRecyclerAdapter.notifyDataSetChanged();
                    seeMore.setVisibility(View.VISIBLE);

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


    class AutoSlider extends TimerTask {

        @Override
        public void run() {
            HomeActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (banner_view_pager.getCurrentItem() < movieModelList.size() - 1) {
                        banner_view_pager.setCurrentItem(banner_view_pager.getCurrentItem() + 1);
                    } else {
                        banner_view_pager.setCurrentItem(0);
                    }
                }
            });
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        sliderTimer = new Timer();
        sliderTimer.scheduleAtFixedRate(new AutoSlider(), 6000, 6000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (sliderTimer != null) {
            sliderTimer.cancel();
            sliderTimer = null;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);

        MenuItem menuItem = menu.findItem(R.id.toolbar_search);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setQueryHint(Html.fromHtml("<font color = #D5D2D2>" + getResources().getString(R.string.hintSearchMess) + "</font>"));
        EditText editText =  searchView.findViewById(R.id.search_src_text);
        editText.setTextColor(Color.WHITE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                showOtherTabMovies(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}