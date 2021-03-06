package com.moviestreamingapp.new_ui_design.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.moviestreamingapp.R;
import com.moviestreamingapp.new_ui_design.MovieDetailDisplay;
import com.moviestreamingapp.new_ui_design.retrofit_singlton_pattern.models.MovieModel;

import java.util.List;

public class ItemRecyclerAdapter extends RecyclerView.Adapter<ItemRecyclerAdapter.MainViewHolder> {

    Context context;
    List<MovieModel> movieModelList;

    public ItemRecyclerAdapter(Context context, List<MovieModel> movieModelList) {
        this.context = context;
        this.movieModelList = movieModelList;
    }

    @NonNull
    @Override
    public ItemRecyclerAdapter.MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new MainViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recycler_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRecyclerAdapter.MainViewHolder holder, int position) {

        holder.movieName.setText(movieModelList.get(position).getTitle());
        Glide.with(context).load("https://image.tmdb.org/t/p/w500"+movieModelList.get(position).getBackdrop_path()).placeholder(R.drawable.loading_image).into(holder.item_image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(holder.itemView.getContext(), MovieDetailDisplay.class);
                intent.putExtra("mTitle",movieModelList.get(holder.getAdapterPosition()).getTitle());
                intent.putExtra("mOverView",movieModelList.get(holder.getAdapterPosition()).getOverview());
                intent.putExtra("imgUrl",movieModelList.get(holder.getAdapterPosition()).getBackdrop_path());
                intent.putExtra("movieId",movieModelList.get(holder.getAdapterPosition()).getId());
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

    public class MainViewHolder extends RecyclerView.ViewHolder {

        ImageView item_image;
        TextView movieName;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            item_image=itemView.findViewById(R.id.item_image);
            movieName=itemView.findViewById(R.id.movieName);
        }
    }
}
