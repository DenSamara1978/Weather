package ru.melandra.weather.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import ru.melandra.weather.R;
import ru.melandra.weather.global.GlobalSettings;

public class RecentlyCitiesActivity extends BaseActivity
{
    private ListView recentlyCitiesListVew;

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_recently_citiesactivity );

        recentlyCitiesListVew = findViewById ( R.id.recentlyList );
        fillRecentlyCitiesList ();
    }

    private void fillRecentlyCitiesList ()
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<> ( getApplicationContext (), android.R.layout.simple_list_item_1, GlobalSettings.getInstance ().getRecentlyCities ());
        recentlyCitiesListVew.setAdapter(adapter);
        recentlyCitiesListVew.setOnItemClickListener ( new AdapterView.OnItemClickListener ()
        {
            @Override
            public void onItemClick ( AdapterView< ? > adapterView, View view, int i, long l )
            {
            showCityWeather ((( TextView ) view ).getText().toString());
            }
        } );
    }

    private void showCityWeather ( String cityName )
    {
        GlobalSettings.getInstance ().setCityName ( cityName );
        Intent intent = new Intent();
        intent.putExtra( cityNameParam, cityName );
        setResult ( RESULT_OK, intent );
        finish ();
    }
}
