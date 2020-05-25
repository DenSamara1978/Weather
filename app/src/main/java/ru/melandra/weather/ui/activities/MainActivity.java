package ru.melandra.weather.ui.activities;

import android.os.Bundle;

import ru.melandra.weather.R;

public class MainActivity extends BaseActivity
{
    @Override
    protected void onCreate ( final Bundle savedInstanceState )
    {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
    }
}
