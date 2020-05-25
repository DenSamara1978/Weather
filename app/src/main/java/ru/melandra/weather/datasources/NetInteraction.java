package ru.melandra.weather.datasources;

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

import ru.melandra.weather.BuildConfig;
import ru.melandra.weather.data.WeatherRequest;
import ru.melandra.weather.global.Constants;

public class NetInteraction implements Constants
{
    public static void requestWeather ( String cityName, final RequestWeatherReciever receiver ) {
        try{
            String url = WEATHER_URL + "q=" + cityName.replace ( " ", "%20" ) + "&appid=" + BuildConfig.API_KEY;
            final URL uri = new URL(url);
            new Thread(new Runnable() {
                @RequiresApi (api = Build.VERSION_CODES.N)
                @Override
                public void run() {
                    HttpsURLConnection urlConnection = null;
                    try{
                        urlConnection = (HttpsURLConnection) uri.openConnection();
                        urlConnection.setRequestMethod("GET");
                        urlConnection.setReadTimeout(100);
                        InputStream is = urlConnection.getInputStream ();
                        InputStreamReader isr = new InputStreamReader ( is );
                        BufferedReader in = new BufferedReader ( isr);
                        String result = getLines(in);
 //                       WeatherJsonParser.ParseJson ( result, receiver );
                        Gson gson = new Gson ();
                        final WeatherRequest weatherRequest = gson.fromJson ( result, WeatherRequest.class );
                        Result ( receiver, weatherRequest );
                    }catch ( final Exception e ) {
                        Error ( receiver, e );
                    }
                }

                @RequiresApi(api = Build.VERSION_CODES.N)
                private String getLines(BufferedReader in) {
                    return in.lines().collect( Collectors.joining("\n"));
                }

            }).start();
        }catch(Exception e){
            Error ( receiver, e );
        }
    }

    private static void Error ( final RequestWeatherReciever receiver, final Exception e )
    {
        final Activity activity = receiver.getWeatherReceiverActivity ();
        if ( activity == null )
            receiver.onError ( e );
        else {
            activity.runOnUiThread ( new Runnable ()
            {
                @Override
                public void run ()
                {
                    receiver.onError ( e );
                }
            } );
        }
    }

    private static void Result ( final RequestWeatherReciever receiver, final WeatherRequest weatherRequest )
    {
        final Activity activity = receiver.getWeatherReceiverActivity ();
        if ( activity == null )
            receiver.onResult ( weatherRequest );
        else {
            activity.runOnUiThread ( new Runnable ()
            {
                @Override
                public void run ()
                {
                    receiver.onResult ( weatherRequest );
                }
            } );
        }
    }
}
