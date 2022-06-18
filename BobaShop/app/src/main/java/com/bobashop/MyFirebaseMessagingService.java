package com.bobashop;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.icu.text.IDNA;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.bobashop.activity.InformasiFragment;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onNewToken(String s) {
        Log.d("New_Token",s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d("notif", "--Title: " + remoteMessage.getData().get("title") + " --Message: "+
                remoteMessage.getData().get("message"));

        showNotification(remoteMessage.getData().get("title"),remoteMessage.getData().get("message"));
    }

    private void showNotification(String title,String message){
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        String channelId = "channel-01";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        Intent i;
        if (title.equals("Ayo Lakukan Imunisasi"))
            i = new Intent(this, InformasiFragment.class);
        else if (title.equals("Seputar Informasi"))
            i = new Intent(this, InformasiFragment.class);
        else
            i = new Intent(this, InformasiFragment.class);

//        Intent i = new Intent(this,Jadwal.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        long[] pattern = {0,500};
        int color = getResources().getColor(R.color.colorAccent);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,PendingIntent
                .FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,channelId)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(message)
                .setSound(alarmSound)
                .setVibrate(pattern)
                .setColor(color)
                .setLights(getResources().getColor(R.color.colorAccent), 3000, 3000)
                .setSmallIcon(R.mipmap.baru_bulat)
//                .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(),
//                        R.drawable.logo_notif))
                .setContentIntent(pendingIntent);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            manager.createNotificationChannel(mChannel);
        }

        manager.notify(new Random().nextInt(),builder.build());

    }

}
