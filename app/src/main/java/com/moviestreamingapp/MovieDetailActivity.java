package com.moviestreamingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.moviestreamingapp.retrofit_singlton_pattern.models.MovieModel;

import java.util.Locale;

public class MovieDetailActivity extends AppCompatActivity {

    TextView movie_title, movie_release_date, original_language,movie_overView;
    ImageView movie_img;
    RatingBar rating_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        movie_title = findViewById(R.id.movie_title);
        movie_release_date = findViewById(R.id.movie_release_date);
        original_language = findViewById(R.id.original_language);
        movie_img = findViewById(R.id.movie_img);
        rating_bar = findViewById(R.id.rating_bar);
        movie_overView = findViewById(R.id.movie_overView);

        //get data from list view
        if(getIntent().hasExtra("movie")){
            MovieModel movieModel = getIntent().getParcelableExtra("movie");

            movie_title.setText(movieModel.getTitle());
            movie_release_date.setText("Release Date"+movieModel.getRelease_date());
            original_language.setText("Language: "+movieModel.getOriginal_language().toUpperCase(Locale.ROOT));
            movie_overView.setText(movieModel.getOverview());
            rating_bar.setRating((movieModel.getVote_average())/2);

            Glide.with(this).load("https://image.tmdb.org/t/p/w500"+movieModel.getPoster_path()).placeholder(R.drawable.ic_launcher_background).into(movie_img);

        }
    }
}