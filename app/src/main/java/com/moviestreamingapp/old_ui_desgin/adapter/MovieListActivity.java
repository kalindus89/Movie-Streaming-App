package com.moviestreamingapp.old_ui_desgin.adapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.moviestreamingapp.R;
import com.moviestreamingapp.old_ui_desgin.adapter.MovieRecyclerAdapter;
import com.moviestreamingapp.old_ui_desgin.adapter.retrofit_singlton_pattern.Credentials;
import com.moviestreamingapp.old_ui_desgin.adapter.retrofit_singlton_pattern.MovieApiInterface;
import com.moviestreamingapp.old_ui_desgin.adapter.retrofit_singlton_pattern.ServiceClass;
import com.moviestreamingapp.old_ui_desgin.adapter.retrofit_singlton_pattern.models.MovieModel;
import com.moviestreamingapp.old_ui_desgin.adapter.retrofit_singlton_pattern.response.MoviesSearchResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MovieRecyclerAdapter movieRecyclerAdapter;
    Toolbar toolbar;
    SearchView search_view;
    List<MovieModel> movieModelList = new ArrayList<>();
    int pageNumberStart=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        toolbar = findViewById(R.id.toolbar);
        search_view = findViewById(R.id.search_view);

        setSupportActionBar(toolbar);

        getSearchResults();

        LinearLayoutManager layoutManageSimple = new LinearLayoutManager(this);
        layoutManageSimple.setOrientation(RecyclerView.HORIZONTAL);

        recyclerView.setLayoutManager(layoutManageSimple);

        movieRecyclerAdapter = new MovieRecyclerAdapter(movieModelList);
        recyclerView.setAdapter(movieRecyclerAdapter);

        getSearchMovieResponse("Comedy",pageNumberStart);

        //Auto load data to recycler view. Pagination
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if(!recyclerView.canScrollHorizontally(1)){
                    getSearchMovieResponse("Comedy",pageNumberStart+1);
                }
            }
        });


    }
    private void getSearchResults() {

        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                getSearchMovieResponse(query,pageNumberStart);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }
    private void getMovieFromId() {

        MovieApiInterface movieApiInterface = ServiceClass.getMovieApiInterface();

        movieApiInterface.getMovieFromId(550, Credentials.API_KEY).enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {

                if (response.isSuccessful()) {

                    Log.v("aaaaa ", response.body().getTitle());

                } else {

                    try {
                        Log.v("Tag", response.errorBody().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {

            }
        });
    }


    private void getSearchMovieResponse(String query,int pageNumber) {

        pageNumberStart=pageNumber;
      //  movieModelList.clear();

        MovieApiInterface movieApiInterface = ServiceClass.getMovieApiInterface();

        movieApiInterface.getSearchMovies(Credentials.API_KEY, query, pageNumber).enqueue(new Callback<MoviesSearchResponse>() {
            @Override
            public void onResponse(Call<MoviesSearchResponse> call, Response<MoviesSearchResponse> response) {

                if (response.isSuccessful()) {
                    movieModelList.addAll(response.body().getResults());
                    movieRecyclerAdapter.notifyDataSetChanged();

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