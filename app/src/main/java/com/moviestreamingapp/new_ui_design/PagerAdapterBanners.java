package com.moviestreamingapp.new_ui_design;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.moviestreamingapp.R;
import com.moviestreamingapp.new_ui_design.retrofit_singlton_pattern.models.MovieModel;

import java.util.List;

public class PagerAdapterBanners extends PagerAdapter {

    Context context;
    List<MovieModel> movieModelList;

    public PagerAdapterBanners(Context context, List<MovieModel> movieModelList) {
        this.context = context;
        this.movieModelList = movieModelList;
    }

    @Override
    public int getCount() {
        return movieModelList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_banner_main_movies,null);

        ImageView banner_imageView= view.findViewById(R.id.banner_imageView);
        TextView movieName= view.findViewById(R.id.movieName);

        movieName.setText(movieModelList.get(position).getTitle());
        Glide.with(context).load("https://image.tmdb.org/t/p/w500"+movieModelList.get(position).getBackdrop_path()).placeholder(R.drawable.ic_launcher_background).into(banner_imageView);
        container.addView(view);

        return view;
    }
}
