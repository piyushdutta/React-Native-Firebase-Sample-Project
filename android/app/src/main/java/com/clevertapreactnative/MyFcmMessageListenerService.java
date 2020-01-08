package com.clevertapreactnative;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.core.app.NotificationCompat;
import android.util.Log;

import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.NotificationInfo;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Random;

public class MyFcmMessageListenerService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage message){

        super.onMessageReceived(message);
        Log.d("onMessageReceived: ", message.getData().toString());
        try {
//             if (message.getNotification() != null) {

//                 RemoteMessage.Notification notification = message.getNotification();
//                 Map<String, String> data = message.getData();

//                 sendNotification(notification, data);

// //                Log.e("Notification Type", "Notification body: " + message.getNotification().getBody());
// //                createNotification(remoteMessage.getNotification());
//             }

            if (message.getData().size() > 0) {
                Bundle extras = new Bundle();
                for (Map.Entry<String, String> entry : message.getData().entrySet()) {
                    extras.putString(entry.getKey(), entry.getValue());
                }


                Map<String, String> params = message.getData();
                JSONObject object = new JSONObject(params);
                Log.e("JSON_OBJECT", object.toString());

                NotificationInfo info = CleverTapAPI.getNotificationInfo(extras);
                if (info.fromCleverTap) {
                    CleverTapAPI.createNotificationChannel(getApplicationContext(),"ChannelID","channel_name","chaneel",3,true);
                    CleverTapAPI.createNotification(getApplicationContext(), extras);
                }
                // else
                // {
                //      RemoteMessage.Notification notification = message.getNotification();
                //     Map<String, String> data = message.getData();

                //     sendNotification(notification, data);
                // }

//                for (String key: extras.keySet())
//                {
//                    Log.d ("notification", key + " is a key in the bundle");
//                }

//                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "channel_id")
//                        .setContentTitle(message.getNotification().getTitle())
//                        .setContentText(message.getNotification().getBody())
//                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                        .setStyle(new NotificationCompat.BigTextStyle())
//                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
//                        .setSmallIcon(R.mipmap.ic_launcher)
//                        .setAutoCancel(true);
//
//                NotificationManager notificationManager =
//                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//                notificationManager.notify(0, notificationBuilder.build());

//                // InsiderX Notification Handler
//                if (message != null && message.getData().containsKey("source") && message.getData().get("source").equals("Insider")) {
//                    Insider.Instance.handleNotification(getApplicationContext(), message);
//                    return;
//                }


                // CleverTap Notification Handler
                


            }
        } catch (Throwable t) {
            Log.d("MYFCMLIST", "Error parsing FCM message", t);
        }
    }


    @Override
    public void onNewToken(String token) {
        Log.e("onNewToken", token.toString());
        CleverTapAPI.getDefaultInstance(this).pushFcmRegistrationId(token,true);
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        //sendRegistrationToServer(token);
    }

    private void sendNotification(RemoteMessage.Notification notification, Map<String, String> data) {
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "channel_id")
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getBody())
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent)
                .setContentInfo(notification.getTitle())
                .setLargeIcon(icon)
                .setColor(Color.RED)
                .setLights(Color.RED, 1000, 300)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setSmallIcon(R.mipmap.ic_launcher);

        try {
            String picture_url = data.get("picture_url");
            if (picture_url != null && !"".equals(picture_url)) {
                URL url = new URL(picture_url);
                Bitmap bigPicture = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                notificationBuilder.setStyle(
                        new NotificationCompat.BigPictureStyle().bigPicture(bigPicture).setSummaryText(notification.getBody())
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Notification Channel is required for Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "channel_id", "channel_name", NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription("channel description");
            channel.setShowBadge(true);
            channel.canShowBadge();
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0, notificationBuilder.build());
    }
}
