package ru.melandra.weather.datasources;

import android.location.Location;

public interface RequestLocationReciever
{
    void onResult ( Location location );
}
