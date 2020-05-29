package ru.melandra.weather.global;

import android.app.Activity;
import android.os.Build;
import android.os.Handler;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.melandra.weather.BuildConfig;
import ru.melandra.weather.datasources.OpenWeather;
import ru.melandra.weather.datasources.RequestWeatherReciever;
import ru.melandra.weather.datasources.WeatherDayData;
import ru.melandra.weather.global.Constants;

public class NetInteraction implements Constants
{
    private volatile static NetInteraction instance = null;
    private static final Object monitor = new Object ();

    private OpenWeather openWeather;

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
    }

    public void requestWeather ( final String cityName, final RequestWeatherReciever receiver ) {
        openWeather.loadWeather(cityName, BuildConfig.API_KEY)
                .enqueue(new Callback<WeatherDayData> () {
                    @Override
                    public void onResponse( Call<WeatherDayData> call, Response<WeatherDayData> response) {
                        if (response.body() != null) {
                            receiver.onResult ( response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherDayData> call, Throwable thr) {
                        receiver.onError ( thr );
                    }
                });
    }
}
