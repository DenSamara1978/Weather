package ru.melandra.weather.ui.activities;

import android.content.res.Configuration;
import android.os.Bundle;

import ru.melandra.weather.R;
import ru.melandra.weather.ui.fragments.WeatherFragment;

public class WeatherActivity extends BaseActivity
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
