package ru.melandra.weather.global;

import java.util.ArrayList;
import java.util.List;

public class GlobalSettings
{
    private volatile static GlobalSettings instance = null;
    private static final Object monitor = new Object ();

    private boolean windShow;
    private boolean fahrenheitScale;
    private boolean darkTheme;
    private String cityName;

    private List<String> recentlyCities = new ArrayList<> ();

    private GlobalSettings ()
    {
        windShow = false;
        fahrenheitScale = false;
        darkTheme = false;
        cityName = "Moscow";
    }

    public static GlobalSettings getInstance ()
    {
        if ( instance == null )
        {
            synchronized ( monitor )
            {
                if ( instance == null )
                    instance = new GlobalSettings ();
            }
        }
        return instance;
    }

    public boolean getWindShow ()
    {
        return windShow;
    }

    public boolean getFahrenheitScale ()
    {
        return fahrenheitScale;
    }

    public String getCityName () {
        return cityName;
    }

    public void setWindShow ( boolean windShow )
    {
        this.windShow = windShow;
    }

    public void setFahrenheitScale ( boolean fahrenheitScale )
    {
        this.fahrenheitScale = fahrenheitScale;
    }

    public void setCityName ( String cityName ) {
        this.cityName = cityName;
        addRecentlyCity ( cityName );
    }

    public boolean isDarkTheme ()
    {
        return darkTheme;
    }

    public void setDarkTheme ( boolean darkTheme )
    {
        this.darkTheme = darkTheme;
    }

    private void addRecentlyCity ( String cityName )
    {
        if ( recentlyCities.contains ( cityName ))
            return;
        recentlyCities.add ( cityName );
        if ( recentlyCities.size () > 6 )
            recentlyCities.remove ( 0 );
    }

    public List<String> getRecentlyCities ()
    {
        return recentlyCities;
    }
}

