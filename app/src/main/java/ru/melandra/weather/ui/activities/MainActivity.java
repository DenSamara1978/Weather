package ru.melandra.weather.ui.activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;

import ru.melandra.weather.R;
import ru.melandra.weather.global.App;
import ru.melandra.weather.notification.CustomReceiver;

public class MainActivity extends BaseActivity
{
    private CustomReceiver customReceiver;

    @Override
    protected void onCreate ( final Bundle savedInstanceState )
    {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );

        initNotificationChannel ();
        customReceiver = new CustomReceiver ();
        registerReceiver ( customReceiver, new IntentFilter ( Intent.ACTION_BATTERY_LOW ));
        registerReceiver ( customReceiver, new IntentFilter ( CONNECTION_INTENT ) );
        registerReceiver ( customReceiver, new IntentFilter ( WEATHER_ALERTER_MSG ) );
    }

    @Override
    protected void onStop ()
    {
        super.onStop ();
        unregisterReceiver ( customReceiver );
    }

    private void initNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) getSystemService( Context.NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel(BROADCAST_CHANNEL_ID, "Battary", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
