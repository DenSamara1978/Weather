package ru.melandra.weather.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import ru.melandra.weather.data.WeatherRequest;
import ru.melandra.weather.datasources.NetInteraction;
import ru.melandra.weather.datasources.RequestWeatherReciever;
import ru.melandra.weather.datasources.WeatherDayDataSource;
import ru.melandra.weather.datasources.WeatherDaySourceBuilder;
import ru.melandra.weather.global.Constants;
import ru.melandra.weather.global.GlobalSettings;
import ru.melandra.weather.R;
import ru.melandra.weather.ui.adapters.WeatherDayAdapter;



/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends Fragment implements Constants, RequestWeatherReciever
{
    private TextView cityNameLabel;
    private RecyclerView threeDaysList;
    private TextView temperatureView;
    private TextView humidityView;

    private final static int REQUEST_CODE = 1;
    private AlertDialog alert;

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
        threeDaysList = view.findViewById ( R.id.threeDaysList );
        temperatureView = view.findViewById ( R.id.temperatureView );
        humidityView = view.findViewById ( R.id.humidityView );

        String cityName = ( getArguments () == null ) ? GlobalSettings.getInstance ().getCityName () : getCurrentCityName ();
        cityNameLabel.setText ( cityName );
        temperatureView.setText ( "-" );
        humidityView.setText ( "-" );

        aboutButton.setOnClickListener ( new View.OnClickListener ()
        {
            @Override
            public void onClick ( View view )
            {
                Intent intent = new Intent ( Intent.ACTION_VIEW, Uri.parse ("https://ru.wikipedia.org/wiki/" + cityNameLabel.getText ()));
                startActivity ( intent );
            }
        } );

        initThreeDaysList ();
        initErrorDialog ();

        NetInteraction.requestWeather ( cityName, this );
        return view;
    }

    private void initErrorDialog () {
        AlertDialog.Builder builder = new AlertDialog.Builder ( getContext ());
        builder.setTitle ( getString( R.string.important_message) )
                .setMessage ( getString( R.string.connection_error) )
                .setPositiveButton ( getString( R.string.ok), new DialogInterface.OnClickListener ()
                {
                    @Override
                    public void onClick ( DialogInterface dialogInterface, int i )
                    {
                    }
                } );
        alert = builder.create ();
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

    private void displayWeather ( WeatherRequest request ) {
        cityNameLabel.setText ( request.getName ());
        if ( GlobalSettings.getInstance ().getFahrenheitScale ())
            temperatureView.setText(String.format("%d°F", (int)((request.getMain().getTemp() - 273.16f ) * 1.8f ) + 32 ));
        else
            temperatureView.setText(String.format("%d°C", (int)(request.getMain().getTemp() - 273.16f )));
        humidityView.setText(String.format("%d%%", request.getMain().getHumidity()));
    }

    @Override
    public void onResult ( WeatherRequest weatherRequest )
    {
        displayWeather ( weatherRequest );
    }

    @Override
    public void onError ( Exception e )
    {
        alert.show ();
    }

    @Override
    public Activity getWeatherReceiverActivity ()
    {
        return getActivity ();
    }
}
