package com.mohamedhuzien.PopularMovies;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Mohamed Huzien on 2/1/2016.
 */
public class RequestBuilder {


    private final String BaseUrl               = "http://api.themoviedb.org/3/" ;
    private final String BaseUrlDiscoverMovies = BaseUrl + "discover/movie?";
    private final String sort_by               = "sort_by";
    private final String api_key               = "api_key";
    private final String page                  = "page" ;
    private final String BaseUrlMovie         = BaseUrl + "movie/";



    public URL BuildDiscoverUrl( String sortBy_param , int pageNum_param ) throws IOException
    {


        Uri uriBuilder = Uri.parse(BaseUrlDiscoverMovies).buildUpon()
                .appendQueryParameter(sort_by , sortBy_param)
                .appendQueryParameter(page , Integer.toString( pageNum_param ))
                .appendQueryParameter(api_key , BuildConfig.MOVIES_API_KEY)
                .build();

        URL url = new URL(uriBuilder.toString());
        Log.e("network" ,url.toString());
        return url;
    }

    public URL BuildMovieByIdUrl(String MovieId) throws IOException
    {
        Uri uriBuilder = Uri.parse(BaseUrlMovie + MovieId ).buildUpon()
                .appendQueryParameter(api_key , BuildConfig.MOVIES_API_KEY)
                .build();

        URL url = new URL(uriBuilder.toString());

        return url;
    }

}
