package ru.melandra.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements Constants
{
    @Override
    protected void onCreate ( final Bundle savedInstanceState )
    {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
    }
}
