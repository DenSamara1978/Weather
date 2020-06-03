package ru.melandra.weather.global;

import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class GlobalSettings
{
    private static final String PREF_FILENAME = "prefFilename";
    private static final String WIND_SHOW = "WIND_SHOW";
    private static final String FAHRENHEIT_SCALE = "FAHRENHEIT_SCALE";
    private static final String DARK_THEME = "DARK_THEME";
    private static final String DEFAULT_CITY = "DEFAULT_CITY";

    private volatile static GlobalSettings instance = null;
    private static final Object monitor = new Object ();

    private boolean windShow;
    private boolean fahrenheitScale;
    private boolean darkTheme;
    private String cityName;

    private GlobalSettings ()
    {
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
        save();
    }

    public void setFahrenheitScale ( boolean fahrenheitScale )
    {
        this.fahrenheitScale = fahrenheitScale;
        save();
    }

    public void setCityName ( String cityName ) {
        this.cityName = cityName;
        HistoryList.getInstance ().addCity ( cityName );
        save();
    }

    public boolean isDarkTheme ()
    {
        return darkTheme;
    }

    public void setDarkTheme ( boolean darkTheme )
    {
        this.darkTheme = darkTheme;
        save();
    }

    public void save () {
        SharedPreferences sharedPref = App.getInstance ().getApplicationContext ().getSharedPreferences(PREF_FILENAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean ( WIND_SHOW, windShow );
        editor.putBoolean ( FAHRENHEIT_SCALE, fahrenheitScale );
        editor.putBoolean ( DARK_THEME, darkTheme );
        editor.putString ( DEFAULT_CITY, cityName );
        editor.apply();
   }

    public void load () {
        SharedPreferences sharedPref = App.getInstance ().getApplicationContext ().getSharedPreferences(PREF_FILENAME, MODE_PRIVATE);
        windShow = sharedPref.getBoolean ( WIND_SHOW,false );
        fahrenheitScale = sharedPref.getBoolean ( FAHRENHEIT_SCALE, false );
        darkTheme = sharedPref.getBoolean ( DARK_THEME, false );
        cityName = sharedPref.getString ( DEFAULT_CITY, "Moscow" );
    }
}

