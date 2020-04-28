package ru.melandra.weather.datasources;

public interface WeatherDayDataSource
{
    WeatherDayData get ( int position );
    int size ();
}
