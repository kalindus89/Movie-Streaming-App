package com.moviestreamingapp.new_ui_design;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.MainViewHolder> {

    Context context;
    List<AllMovieCategories> allMovieCategoriesList;

    public MainRecyclerAdapter(Context context, List<AllMovieCategories> allMovieCategoriesList) {
        this.context = context;
        this.allMovieCategoriesList = allMovieCategoriesList;
    }

    @NonNull
    @Override
    public MainRecyclerAdapter.MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MainRecyclerAdapter.MainViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return allMovieCategoriesList.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
