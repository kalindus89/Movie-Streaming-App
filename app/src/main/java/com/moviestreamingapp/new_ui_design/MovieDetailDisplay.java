package com.moviestreamingapp.new_ui_design;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.moviestreamingapp.R;
import com.moviestreamingapp.new_ui_design.retrofit_singlton_pattern.Credentials;
import com.moviestreamingapp.new_ui_design.retrofit_singlton_pattern.MovieApiInterface;
import com.moviestreamingapp.new_ui_design.retrofit_singlton_pattern.ServiceClass;
import com.moviestreamingapp.new_ui_design.retrofit_singlton_pattern.response.MoviesSearchResponse;
import com.moviestreamingapp.new_ui_design.retrofit_singlton_pattern.response.MoviesVideoIdResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailDisplay extends AppCompatActivity {

    TextView movie_overView,movie_Title;
    Button play_button;
    ImageView movieImage,backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail_display);

        movie_Title=findViewById(R.id.movie_Title);
        movie_overView=findViewById(R.id.movie_overView);
        play_button=findViewById(R.id.play_button);
        backBtn=findViewById(R.id.backBtn);
        movieImage=findViewById(R.id.movieImage);

        movie_Title.setText(getIntent().getStringExtra("mTitle"));
        movie_overView.setText(getIntent().getStringExtra("mOverView"));

        Glide.with(this).load("https://image.tmdb.org/t/p/w500"+getIntent().getStringExtra("imgUrl")).placeholder(R.drawable.loading_image).into(movieImage);


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent intent= new Intent(MovieDetailDisplay.this, PlayVideoActivity.class);
                startActivity(intent);*/
                getPopularMovies(getIntent().getIntExtra("movieId",550));
            }
        });

    }

    private void getPopularMovies(int id) {

        MovieApiInterface movieApiInterface = ServiceClass.getMovieApiInterface();

        movieApiInterface.getVideoId(id,Credentials.API_KEY).enqueue(new Callback<MoviesVideoIdResponse>() {
            @Override
            public void onResponse(Call<MoviesVideoIdResponse> call, Response<MoviesVideoIdResponse> response) {

                if (response.isSuccessful()) {
                    Intent intent= new Intent(MovieDetailDisplay.this, PlayVideoActivity.class);
                    intent.putExtra("videoId",response.body().getResults().get(0).getKey());
                    startActivity(intent);
                } else {
                    try {
                        Log.v("Tag", response.errorBody().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onFailure(Call<MoviesVideoIdResponse> call, Throwable t) {

            }
        });
    }
}