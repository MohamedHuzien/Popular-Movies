package com.mohamedhuzien.PopularMovies;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Mohamed Huzien on 1/29/2016.
 */
public  class CustomAdapter extends ArrayAdapter<Movie>{


    public CustomAdapter(Context context, List<Movie> movies) {
        super(context, 0 ,  movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Movie movie =getItem (position);

        // select root element

        if(convertView == null)
        {
            convertView = LayoutInflater.from( getContext() ).inflate(R.layout.movies_grid_item , parent , false);
        }


        ImageView MovieImage = (ImageView) convertView.findViewById(R.id.movie_image_view_item);
        String imageUrl =  movie.getPoster_path() ;
        // MovieImage.setImageResource(image);
        Picasso.with(getContext() ).load(imageUrl).into(MovieImage);

        return convertView;

    }


}
