package ru.melandra.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;

public class WeatherActivity extends AppCompatActivity implements Constants
{

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.weather_layout );

        if ( getResources ().getConfiguration ().orientation == Configuration.ORIENTATION_LANDSCAPE ) {
            finish ();
            return;
        }

        if ( savedInstanceState == null ) {
            WeatherFragment detail = new WeatherFragment ();
            detail.setArguments ( getIntent ().getExtras () );
            getSupportFragmentManager ().beginTransaction ().replace ( R.id.fragment_container, detail ).commit ();
        }
    }
}
