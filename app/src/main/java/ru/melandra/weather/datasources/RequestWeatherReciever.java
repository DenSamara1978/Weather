package ru.melandra.weather.datasources;

import android.app.Activity;

import ru.melandra.weather.data.WeatherRequest;

public interface RequestWeatherReciever
{
    void onResult ( WeatherRequest weatherRequest );
    void onError ( Exception e );
    Activity getWeatherReceiverActivity ();
}
