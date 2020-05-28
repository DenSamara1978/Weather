package ru.melandra.weather.datasources;

import android.app.Activity;

import com.google.gson.Gson;

import ru.melandra.weather.data.WeatherRequest;

public class WeatherJsonParser
{
    private static Gson gson = new Gson ();

    public static void ParseJson ( final String request, final RequestWeatherReciever receiver ) {
        new Thread ( new Runnable ()
        {
            final Activity activity = receiver.getWeatherReceiverActivity ();

            @Override
            public void run ()
            {
                try
                {
                    final WeatherRequest weatherRequest = gson.fromJson ( request, WeatherRequest.class );
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
                } catch ( final Exception e ) {
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
            }
        }).start ();
    }
}
