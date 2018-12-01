package com.example.notificationapp;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void OnClickButton1(View view){
        message.notify(getApplicationContext(),"This is mirea",2);

    }

    public void OnClickButton2(View view){
        Notification.Builder notificate
                = new Notification.Builder(MainActivity.this);
        notificate.setAutoCancel(true);
        notificate.setSmallIcon(R.mipmap.ic_launcher);
        notificate.setTicker("Lol, i created notificate guys");
        notificate.setWhen(System.currentTimeMillis()+10*1000);
        notificate.setContentTitle("Read this now");
        notificate.setContentText("На русском тоже можно");

        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,100,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        notificate.setContentIntent(pendingIntent);

        NotificationManager notificationService =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notif = notificate.build();
        notificationService.notify(12345,notif);

    }
}
