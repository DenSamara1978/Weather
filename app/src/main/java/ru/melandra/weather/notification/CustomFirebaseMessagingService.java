package ru.melandra.weather.notification;

import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import ru.melandra.weather.R;

public class CustomFirebaseMessagingService extends FirebaseMessagingService
{
    private int messageId = 0;

    public CustomFirebaseMessagingService ()
    {
    }

    @Override
    public void onMessageReceived( RemoteMessage remoteMessage) {
        Log.d("PushMessage", remoteMessage.getNotification().getBody());
        String title = remoteMessage.getNotification().getTitle();
        if (title == null)
            title = "Push Message";
        String text = remoteMessage.getNotification().getBody();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "2")
                .setSmallIcon ( R.mipmap.ic_launcher )
                .setContentTitle(title)
                .setContentText(text);
        NotificationManager notificationManager = (NotificationManager) this.getSystemService( Context.NOTIFICATION_SERVICE);
        notificationManager.notify(messageId++, builder.build());
    }

    @Override
    public void onNewToken(String token) {
        Log.d("PushMessage", "Token " + token);
    }
}
