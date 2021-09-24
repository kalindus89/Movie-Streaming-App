package com.moviestreamingapp.new_ui_design;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;

import com.moviestreamingapp.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class PlayVideoActivity extends AppCompatActivity {


  //  static final String FILE_URL = "https://www.youtube.com/watch?v=BdJKm16Co6M";
    static final String FILE_URL = "https://sample-videos.com/video123/mp4/720/big_buck_bunny_720p_1mb.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);


        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = getIntent().getStringExtra("videoId");
                youTubePlayer.loadVideo(videoId, 0);
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}