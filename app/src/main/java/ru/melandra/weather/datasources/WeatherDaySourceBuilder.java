package ru.melandra.weather.datasources;

public class WeatherDaySourceBuilder
{
    public WeatherDaySource build () {
        WeatherDaySource source = new WeatherDaySource ();
        source.init ();
        return source;
    }
}
