package ru.melandra.weather.global;

public interface Constants
{
    String fahrenheitScaleParamName = "GLOBAL_SETTINGS.FAHRENHEIT_SCALE";
    String windShowParamName = "GLOBAL_SETTINGS.WIND_SHOW";
    String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?";

    String cityNameParam = "CityName";

    int REQUEST_SETTINGS_CODE = 1;
    int REQUEST_RECENTLY_CITY_CODE = 2;

    String BROADCAST_CHANNEL_ID = "1";
    String CONNECTION_INTENT = "android.net.conn.CONNECTIVITY_CHANGE";

    String WEATHER_ALERTER_MSG = "ru.melandra.broadcastsender.message";
    String NAME_MSG = "MSG";
}
