package com.mohamedhuzien.PopularMovies;


import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.squareup.picasso.Picasso;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by Mohamed Huzien on 2/2/2016.
 *
 */
public class DetailsActivityFragment extends Fragment {

    Movie movieInfo ;
    TextView MovieTitle_header ;
    TextView Moviegenres_header;
    TextView MovieOverView;
    ImageView MoviePoster ;
    TextView MovieTitle_infoSection;
    TextView MovieRating_infoSection;
    TextView MovieReleaseDate_infoSection;
    TextView MovieRuntime_infoSection;
    TextView MovieBudget_infoSection;
    TextView MovieGenres_infoSection;
    TextView MovieTageline_infoSection;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        movieInfo = new Movie();

        Intent intent =  getActivity().getIntent();

        movieInfo.setID(intent.getStringExtra("Movie_id"));


        MovieTitle_header = (TextView)  rootView.findViewById(R.id.movie_title);
        Moviegenres_header = (TextView)  rootView.findViewById(R.id.genres_small);
        MovieOverView = (TextView) rootView.findViewById(R.id.movie_overview);
        MoviePoster  = (ImageView) rootView.findViewById(R.id.movie_poster);
        MovieTitle_infoSection = (TextView) rootView.findViewById(R.id.movie_title_small);
        MovieRating_infoSection = (TextView) rootView.findViewById(R.id.movie_rating);
        MovieReleaseDate_infoSection = (TextView) rootView.findViewById(R.id.movie_release_date);
        MovieRuntime_infoSection = (TextView) rootView.findViewById(R.id.movie_runtime);
        MovieBudget_infoSection = (TextView) rootView.findViewById(R.id.movie_budget);
        MovieGenres_infoSection = (TextView) rootView.findViewById(R.id.movie_genres);
        MovieTageline_infoSection = (TextView) rootView.findViewById(R.id.movie_tagline);

        RequestBuilder urlBuilder = new RequestBuilder();
        try {
            URL url = urlBuilder.BuildMovieByIdUrl(movieInfo.getID());
            FatchMovieInfo fatchMovieInfo = new FatchMovieInfo();
            fatchMovieInfo.execute(url);
        }catch (IOException e) {
            Log.e("Movie url" , e.toString());
        }


        return rootView;

    }

    class FatchMovieInfo extends AsyncTask<URL , Void , Movie>
    {

        @Override
        protected Movie doInBackground(URL... params) {


            HttpURLConnection  urlConnection = null ;
            BufferedReader reader = null ;

            try {
                URL url = params[0];

                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();

                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ( (line = reader.readLine()) != null)
                {
                    buffer.append(line + "/n");
                }

                String jsonString = buffer.toString();

                JSONParser jsonParser = new JSONParser();

                Movie movie = jsonParser.getMovieObject(jsonString);

                return movie ;
            }catch (IOException e)
            {

            }catch (JSONException e)
            {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Movie movie) {
            movieInfo = movie ;

            String genres_header = "" , genres_infoSec = "" ;
            String[] genres  = movie.getGenres();
            String relaseDate = movie.getRelease_date();
            String[] relaseDate_split = relaseDate.split("-");

            for (int i = 0 ; i < genres.length ; i++)
            {
                genres_header += genres[i];
                genres_infoSec += genres[i];

                if(i < genres.length -1) {
                    genres_header += " | ";
                    genres_infoSec += ", ";

                }
            }
            MovieTitle_header.setText(movie.getTitle() + " (" + relaseDate_split[0] + ")");
            Moviegenres_header.setText(genres_header);
            MovieOverView.setText(movie.getDescription());
            Picasso.with(getActivity()).load(movie.getPoster_path()).into(MoviePoster);
            MovieTitle_infoSection.setText(movie.getTitle());
            MovieRating_infoSection.setText( Double.toString(movie.getVote_average()) );
            MovieReleaseDate_infoSection.setText(movie.getRelease_date());
            MovieRuntime_infoSection.setText(Double.toString(movie.getRuntime()) );
            MovieGenres_infoSection.setText(genres_infoSec);
            MovieBudget_infoSection.setText( movie.getBudget());
            MovieTageline_infoSection.setText(movie.getTagline());




        }
    }
}
