package ru.melandra.weather.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ru.melandra.weather.global.Constants;
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
