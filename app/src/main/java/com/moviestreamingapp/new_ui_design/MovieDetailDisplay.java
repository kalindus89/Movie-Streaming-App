package com.moviestreamingapp.new_ui_design;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.moviestreamingapp.R;

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

    }
}