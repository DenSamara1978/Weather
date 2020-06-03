package ru.melandra.weather.notification;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.core.app.NotificationCompat;

import ru.melandra.weather.R;
import ru.melandra.weather.global.Constants;

public class CustomReceiver extends BroadcastReceiver implements Constants
{
    private static int messageId = 0;

    @Override
    public void onReceive ( Context context, Intent intent )
    {
        if ( messageId++ == 0 )
            return; // Первым всегда приходит сообщение текущего состояния сети - его пропускаем

        String contentText;
        if ( intent.getAction ().equals ( CONNECTION_INTENT ))
        {
            final ConnectivityManager connMgr = ( ConnectivityManager ) context.getSystemService ( Context.CONNECTIVITY_SERVICE );
            final NetworkInfo wifi = connMgr.getNetworkInfo ( ConnectivityManager.TYPE_WIFI );
            final NetworkInfo mobile = connMgr.getNetworkInfo ( ConnectivityManager.TYPE_MOBILE );
            if ( wifi.isConnected () || mobile.isConnected () )
                contentText = context.getString ( R.string.NetworkTurnedOn );
            else
                contentText = context.getString ( R.string.NetworkTurnedOff );
        }
        else
            contentText = context.getString( R.string.LowBattaryCharge);

        NotificationCompat.Builder builder = new NotificationCompat.Builder ( context, BROADCAST_CHANNEL_ID )
                .setSmallIcon ( R.mipmap.ic_launcher )
                .setContentTitle ( context.getString( R.string.ImportantNotify) )
                .setContentText ( contentText );

        NotificationManager notificationManager = ( NotificationManager ) context.getSystemService ( Context.NOTIFICATION_SERVICE );
        notificationManager.notify ( messageId++, builder.build () );
    }
}
