package ru.melandra.weather.global;

import android.location.Location;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.melandra.weather.BuildConfig;
import ru.melandra.weather.datasources.OpenWeather;
import ru.melandra.weather.datasources.RequestWeatherReciever;
import ru.melandra.weather.datasources.WeatherDayData;
import ru.melandra.weather.model.History;
import ru.melandra.weather.notification.WeatherAlerter;

public class NetInteraction implements Constants
{
    private volatile static NetInteraction instance = null;
    private static final Object monitor = new Object ();

    private OpenWeather openWeather;
    private SimpleDateFormat dateFormat;

    public static NetInteraction getInstance ()
    {
        if ( instance == null )
        {
            synchronized ( monitor )
            {
                if ( instance == null )
                    instance = new NetInteraction ();
            }
        }
        return instance;
    }

    private NetInteraction ()
    {
        Retrofit retrofit;
        retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        openWeather = retrofit.create(OpenWeather.class);
        dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
    }

    private Callback<WeatherDayData> getCallback (final RequestWeatherReciever receiver)
    {
        return new Callback< WeatherDayData > ()
        {
            @Override
            public void onResponse ( Call< WeatherDayData > call, Response< WeatherDayData > response )
            {
                if ( response.body () != null )
                {
                    WeatherDayData weather = response.body ();
                    weather.setDate ( new Date () );
                    History history = new History ();
                    history.cityName = weather.getCityName ();
                    history.date = dateFormat.format ( weather.getDate () );
                    history.temperature = Weather.getTemperature ( weather.getTemperature () );
                    HistoryList.getInstance ().updateCity ( history );
                    receiver.onResult ( weather );

                    WeatherAlerter.inspect ( weather );
                }
            }

            @Override
            public void onFailure ( Call< WeatherDayData > call, Throwable thr )
            {
                receiver.onError ( thr );
            }
        };
    }

    public void requestCurrentWeatherByCoord ( final Location location, final RequestWeatherReciever receiver )
    {
        Double latitude = location.getLatitude ();
        Double longitude = location.getLongitude ();
        openWeather.loadCurrentWeather ( latitude.toString (), longitude.toString (), BuildConfig.API_KEY )
                .enqueue ( getCallback ( receiver ));
    }

    public void requestCurrentWeatherByCityname ( final String cityName, final RequestWeatherReciever receiver )
    {
        openWeather.loadCurrentWeather ( cityName, BuildConfig.API_KEY )
                .enqueue ( getCallback ( receiver ));
    }

    public void loadImage ( ImageView imageView ) {
        Picasso.get()
                .load("https://icon-icons.com/downloadimage.php?id=134157&root=2211/PNG/512/&file=weather_sun_sunny_cloud_icon_134157.png")
                .into(imageView);
    }
}
