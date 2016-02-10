package com.mohamedhuzien.PopularMovies;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mohamed Huzien on 1/30/2016.
 */
public class JSONParser {



    public Movie[] getMoveiesList( String JSONstring ) throws JSONException
    {
        JSONObject jsonObject = new JSONObject(JSONstring) ;

        JSONArray resultMovie = jsonObject.getJSONArray("results");
        Movie[] movieList  = new Movie[resultMovie.length()  ];
        for (Integer i = 0 ; i < resultMovie.length() ; i++)
        {
            JSONObject movieInfo = resultMovie.getJSONObject(i);

            Movie movie = new Movie();

            movie.setID(  movieInfo.getString("id")  );
            movie.setTitle(movieInfo.getString("title"));
            movie.setDescription(movieInfo.getString("overview"));
            movie.setRelease_date(movieInfo.getString("release_date"));
            movie.setPoster_path("http://image.tmdb.org/t/p/w185/" + movieInfo.getString("poster_path"));
            movie.setBackdrop_path("http://image.tmdb.org/t/p/w185/" + movieInfo.getString("backdrop_path"));
            movie.setVote_average(movieInfo.getDouble("vote_average"));
            movie.setVote_count(movieInfo.getInt("vote_count"));

            movieList[i] = movie ;

        }


        return movieList;
    }


    public Movie getMovieObject(String jsonString) throws JSONException
    {
        JSONObject movieInfo = new JSONObject(jsonString);
        Movie movie = new Movie();

        movie.setID(movieInfo.getString("id"));
        movie.setBudget(movieInfo.getString("budget"));
        movie.setTitle(movieInfo.getString("title"));
        movie.setDescription(movieInfo.getString("overview"));
        movie.setTagline(movieInfo.getString("tagline"));
        movie.setRelease_date(movieInfo.getString("release_date"));
        movie.setPoster_path("http://image.tmdb.org/t/p/w185/" + movieInfo.getString("poster_path"));
        movie.setBackdrop_path("http://image.tmdb.org/t/p/w185/" + movieInfo.getString("backdrop_path"));
        movie.setVote_average(movieInfo.getDouble("vote_average"));
        movie.setVote_count(movieInfo.getInt("vote_count"));
        movie.setRuntime(movieInfo.getDouble("runtime"));
        JSONArray Jsongenres = movieInfo.getJSONArray("genres");

        String[] genres = new String[Jsongenres.length()] ;

        for(int i=0 ; i < genres.length ; i++)
        {

            JSONObject genre = Jsongenres.getJSONObject(i);
            genres[i] = genre.getString("name");
        }

        movie.setGenres(genres);

        return movie;

    }


}
