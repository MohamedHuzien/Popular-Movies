package com.mohamedhuzien.PopularMovies;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Mohamed Huzien on 2/2/2016.
 */
public class DetailsActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_details);

        getFragmentManager().beginTransaction()
            .add(R.id.Fregment_placehodler , new DetailsActivityFragment() )
            .commit();
    }
}
