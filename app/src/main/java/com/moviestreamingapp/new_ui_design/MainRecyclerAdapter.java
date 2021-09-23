package com.moviestreamingapp.new_ui_design;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moviestreamingapp.R;
import com.moviestreamingapp.new_ui_design.retrofit_singlton_pattern.models.MovieModel;

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
        return  new MainViewHolder(LayoutInflater.from(context).inflate(R.layout.item_main_recycler_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainRecyclerAdapter.MainViewHolder holder, int position) {

        holder.item_category.setText(allMovieCategoriesList.get(position).categoryTitle);

        setItem_recyclerView(holder.itemRecyclerView,allMovieCategoriesList.get(position).getMovieModelList());
    }

    @Override
    public int getItemCount() {
        return allMovieCategoriesList.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {

        TextView item_category;
        RecyclerView itemRecyclerView;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            item_category=itemView.findViewById(R.id.item_category);
            itemRecyclerView=itemView.findViewById(R.id.itemRecyclerView);
        }
    }

    public void setItem_recyclerView( RecyclerView itemRecyclerView,List<MovieModel> movieModelListItem) {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
        itemRecyclerView.setLayoutManager(layoutManager);
        ItemRecyclerAdapter itemRecyclerAdapter = new ItemRecyclerAdapter(context, movieModelListItem);
        itemRecyclerView.setAdapter(itemRecyclerAdapter);
        itemRecyclerAdapter.notifyDataSetChanged();
    }
}
