package ru.melandra.weather.datasources;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.melandra.weather.datasources.WeatherDayData;

public interface OpenWeather
{
    @GET ("data/2.5/weather")
    Call< WeatherDayData > loadWeather( @Query("q") String cityCountry, @Query ("appid") String keyApi);
}
