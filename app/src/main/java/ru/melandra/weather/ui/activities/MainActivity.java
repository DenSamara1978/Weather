package ru.melandra.weather.ui.activities;

import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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
}
