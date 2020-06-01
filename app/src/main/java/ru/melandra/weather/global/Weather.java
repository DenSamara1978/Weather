package ru.melandra.weather.global;

public class Weather
{
    public static String getTemperature ( float temperature ) {
        float temperatureKelvin = temperature - 273.16f;
        if ( GlobalSettings.getInstance ().getFahrenheitScale ())
            return String.format("%d°F", (int)(temperatureKelvin * 1.8f ) + 32 );
        else
            return String.format("%d°C", (int)(temperatureKelvin));
    }
}
