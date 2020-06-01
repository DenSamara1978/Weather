package ru.melandra.weather.datasources;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeather
{
    @GET ("data/2.5/weather")
    Call< WeatherDayData > loadCurrentWeather( @Query("q") String cityCountry, @Query ("appid") String keyApi);
}
