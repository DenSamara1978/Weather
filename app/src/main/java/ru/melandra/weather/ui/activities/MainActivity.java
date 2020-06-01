package ru.melandra.weather.ui.activities;

import android.os.Bundle;

import ru.melandra.weather.R;
import ru.melandra.weather.global.GlobalSettings;

public class MainActivity extends BaseActivity
{
    @Override
    protected void onCreate ( final Bundle savedInstanceState )
    {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
    }

    @Override
    protected void onStop ()
    {
        super.onStop ();
    }
}
