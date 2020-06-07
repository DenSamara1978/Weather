package ru.melandra.weather.global;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;

import androidx.core.app.ActivityCompat;
import androidx.room.Room;

import ru.melandra.weather.dao.HistoryDao;
import ru.melandra.weather.database.WeatherDatabase;
import ru.melandra.weather.datasources.RequestLocationReciever;

public class App extends Application
{
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static App instance;
    private WeatherDatabase db;
    private Location currentLocation;

    public static App getInstance ()
    {
        return instance;
    }

    @Override
    public void onCreate ()
    {
        super.onCreate ();
        instance = this;
        db = Room.databaseBuilder (
                getApplicationContext (),
                WeatherDatabase.class,
                "weather_database" )
                .allowMainThreadQueries ()
                .build ();
        GlobalSettings.getInstance ().load ();
    }

    public HistoryDao getHistoryDao ()
    {
        return db.getHistoryDao ();
    }

    private boolean isLocationPermissioned ()
    {
        return ( ActivityCompat.checkSelfPermission ( this, Manifest.permission.ACCESS_COARSE_LOCATION ) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission ( this, Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED );
    }

    private void requestPemissions ( Activity activity )
    {
        if ( !isLocationPermissioned () )
        {
            if ( !ActivityCompat.shouldShowRequestPermissionRationale ( activity, Manifest.permission.CALL_PHONE ) )
            {
                ActivityCompat.requestPermissions ( activity,
                        new String[]{
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION
                        },
                        PERMISSION_REQUEST_CODE );
            }
        }
    }

    public void requestLocation ( Activity activity, final RequestLocationReciever receiver )
    {
        if ( !isLocationPermissioned () )
            requestPemissions ( activity );

        if ( !isLocationPermissioned () )
            return;
        else
        {
            LocationManager locationManager = ( LocationManager ) getSystemService ( LOCATION_SERVICE );
            Criteria criteria = new Criteria ();
            criteria.setAccuracy ( Criteria.ACCURACY_COARSE );

            String provider = locationManager.getBestProvider ( criteria, true );
            if ( provider != null )
            {
                locationManager.requestSingleUpdate ( provider, new LocationListener ()
                {
                    @Override
                    public void onLocationChanged ( Location location )
                    {
                        currentLocation = location;
                        receiver.onResult ( location );
                    }

                    @Override
                    public void onStatusChanged ( String provider, int status, Bundle extras )
                    {

                    }

                    @Override
                    public void onProviderEnabled ( String provider )
                    {

                    }

                    @Override
                    public void onProviderDisabled ( String provider )
                    {

                    }
                }, Looper.myLooper () );
            }
        }
    }
}
