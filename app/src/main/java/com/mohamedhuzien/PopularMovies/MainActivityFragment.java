package com.mohamedhuzien.PopularMovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    GridView MovieGridView ;
    private CustomAdapter adapter ;
    private int result_pageNumber = 1;
    private List<Movie> moviesList;
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView =  inflater.inflate(R.layout.fragment_main , container , false);
        moviesList = new ArrayList<Movie>();


        URL url ;
        try {
            RequestBuilder Builder = new RequestBuilder();
             url = Builder.BuildDiscoverUrl(Settings.sortBypopularity(), result_pageNumber++);
            FatchMoviesInfo fatchMoviesInfo = new FatchMoviesInfo();
            fatchMoviesInfo.execute(url);


        }catch (IOException e)
        {
            Log.e("url error" , e.toString());
        }



        adapter = new CustomAdapter(getActivity() , moviesList);

         MovieGridView = (GridView) rootView.findViewById(R.id.movies_gridview);
        MovieGridView.setAdapter(adapter);

        MovieGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                if (MovieGridView.getLastVisiblePosition() >= MovieGridView.getCount() - MovieGridView.getNumColumns()) {
                    URL url;
                    try {
                        RequestBuilder Builder = new RequestBuilder();
                        url = Builder.BuildDiscoverUrl(Settings.sortBypopularity(), result_pageNumber++);
                        FatchMoviesInfo fatchMoviesInfo = new FatchMoviesInfo();
                        fatchMoviesInfo.execute(url);


                    } catch (IOException e) {
                        Log.e("error", e.toString());
                    }
                }

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        MovieGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent intent = new Intent(getContext() , DetailsActivity.class);
                intent.putExtra("Movie_id" , moviesList.get(position).getID());
                startActivity(intent);


            }
        });



        return  rootView;

    }

    public  class FatchMoviesInfo extends AsyncTask<URL , Void , Movie[]>
    {
        @Override
        protected Movie[] doInBackground(URL... params) {

            HttpURLConnection uriconnect = null ;
            BufferedReader reader  = null ;


            Movie[] MovieList ;


            try {

                URL url = params[0];
                uriconnect = (HttpURLConnection) url.openConnection();
                Log.v("request url" , url.toString());
                uriconnect.connect();

                InputStream inputStream = uriconnect.getInputStream();
                StringBuffer buffer = new StringBuffer();



                if(inputStream == null)
                    return null ;


                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line ;
                while ( (line = reader.readLine()) != null  )
                {
                    buffer.append(line + "\n") ;
                }

                String JsonString = buffer.toString() ;
                //  Log.e("Json" , JsonString + "no things");
                JSONParser parser = new JSONParser();
                MovieList = parser.getMoveiesList(JsonString);


                return MovieList ;

            }catch (IOException e)
            {
                Log.e("IO" , e.toString());

            }catch (JSONException e)
            {
                Log.e("JSON" , e.toString());
            }





            return null;
        }

        @Override
        protected void onPostExecute(Movie[] movies) {




            for(int i = 0 ; i <movies.length ; i++) {
                moviesList.add(movies[i]);
            }
            adapter.notifyDataSetChanged();


        }
    }


}
