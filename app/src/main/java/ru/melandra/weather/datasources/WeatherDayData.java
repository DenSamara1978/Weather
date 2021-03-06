package ru.melandra.weather.datasources;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import ru.melandra.weather.data.Clouds;
import ru.melandra.weather.data.Main;
import ru.melandra.weather.data.Weather;
import ru.melandra.weather.data.Wind;

public class WeatherDayData
{
    private Weather[] weather;
    @SerializedName ("main")
    @Expose
    private Main main;
    @SerializedName ("wind")
    @Expose
    private Wind wind;
    @SerializedName ("clouds")
    @Expose
    private Clouds clouds;
    @SerializedName ("name")
    @Expose
    private String name;
    private Date date;

    public float getTemperature ()
    {
        return main.getTemp ();
    }

    public int getHumidity ()
    {
        return main.getHumidity ();
    }

    public int getPressure ()
    {
        return main.getPressure ();
    }

    public double getWindSpeed ()
    {
        return wind.getSpeed ();
    }

    public int getWindDirection ()
    {
        return wind.getDeg ();
    }

    public int getCloudy ()
    {
        return clouds.getAll ();
    }

    public String getCityName ()
    {
        return name;
    }

    public void setDate ( Date date ) {
        this.date = date;
    }

    public Date getDate ()
    {
        return date;
    }
}
