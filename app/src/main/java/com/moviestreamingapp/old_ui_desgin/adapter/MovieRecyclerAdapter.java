package com.moviestreamingapp.old_ui_desgin.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.moviestreamingapp.R;
import com.moviestreamingapp.old_ui_desgin.adapter.retrofit_singlton_pattern.models.MovieModel;

import java.util.List;
import java.util.Locale;

public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.MovieViewHolder> {
    List<MovieModel> movieModelList;

    public MovieRecyclerAdapter(List<MovieModel> movieModelList) {
        this.movieModelList = movieModelList;
    }

    @NonNull
    @Override
    public MovieRecyclerAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_list_item, viewGroup, false);
        return new MovieViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieRecyclerAdapter.MovieViewHolder holder, int position) {


        holder.movie_title.setText(movieModelList.get(position).getTitle());
        holder.movie_release_date.setText("Release Date"+movieModelList.get(position).getRelease_date());
        holder.original_language.setText("Language: "+movieModelList.get(position).getOriginal_language().toUpperCase(Locale.ROOT));
        holder.rating_bar.setRating((movieModelList.get(position).getVote_average())/2);

        Glide.with(holder.itemView.getContext()).load("https://image.tmdb.org/t/p/w500"+movieModelList.get(position).getPoster_path()).placeholder(R.drawable.ic_launcher_background).into(holder.movie_img);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(holder.itemView.getContext(), MovieDetailActivity.class);
                intent.putExtra("movie",movieModelList.get(holder.getAdapterPosition()));
                holder.itemView.getContext().startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        if(movieModelList!=null) {
            return movieModelList.size();
        }else {
            return 0;
        }
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        TextView movie_title, movie_release_date, original_language;
        ImageView movie_img;
        RatingBar rating_bar;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            movie_title = itemView.findViewById(R.id.movie_title);
            movie_release_date = itemView.findViewById(R.id.movie_release_date);
            original_language = itemView.findViewById(R.id.original_language);
            movie_img = itemView.findViewById(R.id.movie_img);
            rating_bar = itemView.findViewById(R.id.rating_bar);

        }
    }
}
