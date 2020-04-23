package ru.melandra.weather;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import static ru.melandra.weather.Constants.cityNameParam;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends Fragment implements Constants
{
    private TextView cityNameLabel;
    private ImageButton settingsButton;

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

//        cityNameLabel.setOnClickListener ( new View.OnClickListener ()
//        {
//            @Override
//            public void onClick ( View view )
//            {
//                Intent intent = new Intent ( getContext (), MainActivity.class );
//                startActivityForResult ( intent, REQUEST_CODE );
//            }
//       } );

        return view;
    }

    public String getCurrentCityName () {
        Bundle args = getArguments ();
        return ( args != null ) ? args.getString ( cityNameParam, "" ) : "";
    }
}
