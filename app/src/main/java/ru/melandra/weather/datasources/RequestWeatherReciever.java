package ru.melandra.weather.datasources;

public interface RequestWeatherReciever
{
    void onResult ( WeatherDayData weather );
    void onError ( Throwable thr );
}
