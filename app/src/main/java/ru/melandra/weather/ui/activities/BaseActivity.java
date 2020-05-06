package ru.melandra.weather.ui.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ru.melandra.weather.R;
import ru.melandra.weather.global.Constants;
import ru.melandra.weather.global.GlobalSettings;

public abstract class BaseActivity extends AppCompatActivity implements Constants
{
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isDarkTheme()) {
            setTheme( R.style.AppDarkTheme );
        } else {
            setTheme ( R.style.AppTheme );
        }
    }

    protected boolean isDarkTheme() {
        return GlobalSettings.getInstance ().isDarkTheme ();
    }

    protected void setDarkTheme(boolean isDarkTheme) {
        GlobalSettings.getInstance ().setDarkTheme ( isDarkTheme );
    }
}
