package ru.melandra.weather.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.Gson;

import ru.melandra.weather.data.WeatherRequest;
import ru.melandra.weather.datasources.WeatherDayDataSource;
import ru.melandra.weather.datasources.WeatherDaySourceBuilder;
import ru.melandra.weather.global.Constants;
import ru.melandra.weather.global.GlobalSettings;
import ru.melandra.weather.R;
import ru.melandra.weather.ui.activities.SettingsActivity;
import ru.melandra.weather.ui.adapters.WeatherDayAdapter;

import static android.app.Activity.RESULT_OK;
import static androidx.constraintlayout.widget.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends Fragment implements Constants
{
    private TextView cityNameLabel;
    private ImageButton settingsButton;
    private RecyclerView threeDaysList;
    private TextView temperatureView;
    private TextView humidityView;

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
        temperatureView = view.findViewById ( R.id.temperatureView );
        humidityView = view.findViewById ( R.id.humidityView );

        String cityName = ( getArguments () == null ) ? GlobalSettings.getInstance ().getCityName () : getCurrentCityName ();
        cityNameLabel.setText ( cityName );

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
                startActivityForResult ( intent, REQUEST_CODE );
            }
        } );

        initThreeDaysList ();

        requestWeather ( cityName );
        return view;
    }

    @Override
    public void onActivityResult ( int requestCode, int resultCode, Intent data )
    {
        if ( requestCode == REQUEST_CODE )
            getActivity ().recreate ();
        else
            super.onActivityResult(requestCode, resultCode, data);
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

    private void requestWeather ( String cityName ) {
        try{
            String url = WEATHER_URL + "q=" + cityName + "&appid=240af58b6f095eb759a3ecd2d282d448";
            final URL uri = new URL(url);
            final Handler handler = new Handler();
            new Thread(new Runnable() {
                @RequiresApi (api = Build.VERSION_CODES.N)
                @Override
                public void run() {
                    HttpsURLConnection urlConnection = null;
                    try{
                        urlConnection = (HttpsURLConnection) uri.openConnection();
                        urlConnection.setRequestMethod("GET");
                        urlConnection.setReadTimeout(10000);
                        BufferedReader in = new BufferedReader(new InputStreamReader (urlConnection.getInputStream()));
                        String result = getLines(in);
                        Gson gson = new Gson();
                        final WeatherRequest request = gson.fromJson(result, WeatherRequest.class);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                displayWeather ( request );
                            }
                        });
                    }catch(Exception e){
                        Toast.makeText ( getContext (), "Connection is failed", Toast.LENGTH_SHORT ).show ();
                        e.printStackTrace();
                    }
                }

                @RequiresApi(api = Build.VERSION_CODES.N)
                private String getLines(BufferedReader in) {
                    return in.lines().collect( Collectors.joining("\n"));
                }

            }).start();
        }catch(Exception e){
            Toast.makeText ( getContext (), "Connection is failed", Toast.LENGTH_SHORT).show ();
        }

    }

    private void displayWeather ( WeatherRequest request ) {
        cityNameLabel.setText ( request.getName ());
        temperatureView.setText(String.format("%d°C", (int)(request.getMain().getTemp() - 273.16f )));
        humidityView.setText(String.format("%d%%", request.getMain().getHumidity()));
    }
}
