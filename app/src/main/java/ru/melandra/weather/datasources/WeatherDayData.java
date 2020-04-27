package ru.melandra.weather.datasources;

public class WeatherDayData
{
    private String date;
    private int temperature;
    private int humidity;
    private int pressure;
    private int windStrength;
    private String windDirection;
    private int cloudy;

    public WeatherDayData ( String date, int temperature, int humidity, int pressure, int windStrength, String windDirection, int cloudy ) {
        this.date = date;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.windStrength = windStrength;
        this.windDirection = windDirection;
        this.cloudy = cloudy;
    }

    public int getTemperature ()
    {
        return temperature;
    }

    public int getHumidity ()
    {
        return humidity;
    }

    public int getPressure ()
    {
        return pressure;
    }

    public int getWindStrength ()
    {
        return windStrength;
    }

    public String getWindDirection ()
    {
        return windDirection;
    }

    public int getCloudy ()
    {
        return cloudy;
    }

    public String getDate ()
    {
        return date;
    }
}
