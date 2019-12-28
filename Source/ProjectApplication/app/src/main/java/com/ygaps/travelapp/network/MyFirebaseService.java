package com.ygaps.travelapp.network;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.ygaps.travelapp.R;
import com.ygaps.travelapp.manager.MyApplication;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ygaps.travelapp.view.NotificationTab;

import java.util.Map;

public class MyFirebaseService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG,"\n"+remoteMessage.getData());

        // handle a notification payload.
        sendNotification(remoteMessage.getData());
    }

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        MyApplication app = (MyApplication) MyFirebaseService.this.getApplication();
        app.setFcmToken(token);
    }

    private void sendNotification(Map<String,String> messageBody) {

        Intent intent1 = new Intent(this, NotificationTab.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent1 = PendingIntent.getActivity(this, 0, intent1, PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.project_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        int type=Integer.valueOf(messageBody.get("type"));
        String content="";
        NotificationCompat.Builder notificationBuilder;
        NotificationManager notificationManager;

        switch (type){
            case 3:
                content="";

                notificationBuilder =
                        new NotificationCompat.Builder(this, channelId)
                                .setSmallIcon(R.drawable.ic_launcher_background)
                                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background))
                                .setContentTitle("Speed limit notification on road")
                                .setContentText(content)
                                .setAutoCancel(true)
                                .setSound(defaultSoundUri)
                                .setDefaults(Notification.DEFAULT_ALL)
                                .setWhen(0)
                                .setPriority(NotificationManager.IMPORTANCE_HIGH);

                notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                // Since android Oreo notification channel is needed.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel(
                            channelId,
                            "Channel human readable title",
                            NotificationManager.IMPORTANCE_DEFAULT);

                    notificationManager.createNotificationChannel(channel);
                }

                notificationManager.notify(0, notificationBuilder.build());
                break;
            case 6:
                content=messageBody.get("hostId")+" has invited you to tour "+messageBody.get("name");

                notificationBuilder =
                        new NotificationCompat.Builder(this, channelId)
                                .setSmallIcon(R.drawable.ic_launcher_background)
                                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background))
                                .setContentTitle("Tour Invitation")
                                .setContentText(content)
                                .setAutoCancel(true)
                                .setSound(defaultSoundUri)
                                .setContentIntent(pendingIntent1)
                                .setDefaults(Notification.DEFAULT_ALL)
                                .setWhen(0)
                                .setPriority(NotificationManager.IMPORTANCE_HIGH);

                notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                // Since android Oreo notification channel is needed.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel(
                            channelId,
                            "Channel human readable title",
                            NotificationManager.IMPORTANCE_DEFAULT);

                    notificationManager.createNotificationChannel(channel);
                }

                notificationManager.notify(0, notificationBuilder.build());
                break;
        }
    }
}