package com.example.nazarko.taskalarmmanager;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    Button btn;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (TextView)findViewById(R.id.text);
        btn = (Button) findViewById(R.id.test);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //scheduleNotification(10000);
                int id = NotificationID.getID();
                scheduleNotification(getNotification("10 second delay", id), 10000,id);
                id = NotificationID.getID();
                scheduleNotification(getNotification("20 second delay",id), 20000,id);
            }
        });

        Intent  intent = getIntent();
        if (intent!=null){
           if(intent.hasExtra(NotificationPublisher.NOTIFICATION_TEXT)){
               text.setText(intent.getStringExtra(NotificationPublisher.NOTIFICATION_TEXT));
               btn.setVisibility(View.GONE);
           }
        }
    }


    private void scheduleNotification(int delay) {
        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }


    private void scheduleNotification(Notification notification, int delay,int id) {
        Intent notificationIntent = new Intent(this, NotificationPublisher2.class);
        notificationIntent.putExtra(NotificationPublisher2.NOTIFICATION_ID, id);
        notificationIntent.putExtra(NotificationPublisher2.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }




    private Notification getNotification(String content,int id) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Title")
                        .setContentText(content)
                        .setAutoCancel(true);
        Intent resultIntent = new Intent(this, MainActivity.class);
        resultIntent.putExtra(NotificationPublisher2.NOTIFICATION_TEXT,"test "+id);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                id,
                PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        return mBuilder.build();

//        Intent resultIntent = new Intent(this, MainActivity.class);
//        resultIntent.putExtra(NotificationPublisher2.NOTIFICATION_TEXT,"test");
//        PendingIntent resultPendingIntent = PendingIntent.getActivity(this,
//                id, resultIntent,
//                PendingIntent.FLAG_CANCEL_CURRENT);
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
//        builder.setSmallIcon(R.mipmap.ic_launcher)
//                        .setContentTitle("Title")
//                        .setContentText(content)
//                        .setAutoCancel(true);
//        builder.setContentIntent(resultPendingIntent);
//        return builder.build();


    }

}
