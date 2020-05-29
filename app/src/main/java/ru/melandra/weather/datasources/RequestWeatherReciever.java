package ru.melandra.weather.datasources;

import ru.melandra.weather.datasources.WeatherDayData;

public interface RequestWeatherReciever
{
    void onResult ( WeatherDayData weather );
    void onError ( Throwable thr );
}
