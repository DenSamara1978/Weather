package ru.melandra.weather.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import ru.melandra.weather.datasources.WeatherDayDataSource;
import ru.melandra.weather.datasources.WeatherDaySourceBuilder;
import ru.melandra.weather.global.Constants;
import ru.melandra.weather.global.GlobalSettings;
import ru.melandra.weather.R;
import ru.melandra.weather.ui.activities.SettingsActivity;
import ru.melandra.weather.ui.adapters.WeatherDayAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends Fragment implements Constants
{
    private TextView cityNameLabel;
    private ImageButton settingsButton;
    private RecyclerView threeDaysList;

    private final static int REQUEST_CODE = 1;

    public WeatherFragment ()
    {
        // Required empty public constructor
    }

    public static WeatherFragment create ( String cityName ) {
        WeatherFragment fragment = new WeatherFragment ();

        Bundle args = new Bundle ();
        args.putString ( cityNameParam, cityName );
        fragment.setArguments ( args );
        return fragment;
    }

    @Override
    public View onCreateView ( LayoutInflater inflater, ViewGroup container,
                               Bundle savedInstanceState )
    {
        View view = inflater.inflate ( R.layout.fragment_weather, container, false );

        cityNameLabel = view.findViewById ( R.id.cityView );
        Button aboutButton = view.findViewById ( R.id.contextAboutCityButton );
        ImageButton settingsButton = view.findViewById ( R.id.settingsButton );
        threeDaysList = view.findViewById ( R.id.threeDaysList );

        if ( getArguments () == null )
            cityNameLabel.setText ( GlobalSettings.getInstance ().getCityName ());
        else
            cityNameLabel.setText ( getCurrentCityName ());

        aboutButton.setOnClickListener ( new View.OnClickListener ()
        {
            @Override
            public void onClick ( View view )
            {

                Intent intent = new Intent ( Intent.ACTION_VIEW, Uri.parse ("https://ru.wikipedia.org/wiki/" + cityNameLabel.getText ()));
                startActivity ( intent );
            }
        } );

        settingsButton.setOnClickListener ( new View.OnClickListener ()
        {
            @Override
            public void onClick ( View view )
            {
                Intent intent = new Intent ( getContext (), SettingsActivity.class );
                startActivity ( intent );
            }
        } );

        initThreeDaysList ();

        return view;
    }

    public String getCurrentCityName () {
        Bundle args = getArguments ();
        return ( args != null ) ? args.getString ( cityNameParam, "" ) : "";
    }

    private void initThreeDaysList () {
        threeDaysList.setHasFixedSize ( true );
        threeDaysList.setLayoutManager ( new LinearLayoutManager ( getContext (), RecyclerView.VERTICAL, false));

        WeatherDayDataSource data  = new WeatherDaySourceBuilder ().build ();
        threeDaysList.setAdapter ( new WeatherDayAdapter ( data ));

        Context context = getContext ();
        DividerItemDecoration itemDecoration = new DividerItemDecoration ( context, LinearLayoutManager.VERTICAL );
        itemDecoration.setDrawable ( context.getDrawable(R.drawable.separator ));
        threeDaysList.addItemDecoration(itemDecoration);
    }
}
