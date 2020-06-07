package ru.melandra.weather.notification;

import android.content.Intent;

import ru.melandra.weather.datasources.WeatherDayData;
import ru.melandra.weather.global.App;
import ru.melandra.weather.global.Constants;

public class WeatherAlerter implements Constants
{
    public static void inspect ( WeatherDayData weather )
    {
        if ( weather.getWindSpeed () > 10.0 )
        {
            Intent intent = new Intent();
            intent.setAction(WEATHER_ALERTER_MSG);
            // Добавим параметр.
            intent.putExtra(NAME_MSG, "Сейчас очень ветрено!");
            App.getInstance ().getApplicationContext ().sendBroadcast ( intent );
        }
    }
}
