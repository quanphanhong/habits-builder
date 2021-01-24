package com.example.habitsbuilder;

import android.app.Notification ;
import android.app.NotificationChannel ;
import android.app.NotificationManager ;
import android.content.BroadcastReceiver ;
import android.content.Context ;
import android.content.Intent ;

public class NotificationPublisher extends BroadcastReceiver {
    public static String NOTIFICATION_ID = "habit_builder_notification" ;
    public static String NOTIFICATION = "Habit Builder" ;

    public void onReceive (Context context , Intent intent) {
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context. NOTIFICATION_SERVICE ) ;
        Notification notification = intent.getParcelableExtra( NOTIFICATION ) ;
        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
            int importance = NotificationManager. IMPORTANCE_HIGH ;
            NotificationChannel notificationChannel = new NotificationChannel("10001" , "HABIT_BUILDER_CHANNEL" , importance) ;
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel) ;
        }
        int id = intent.getIntExtra(NOTIFICATION_ID, 0 ) ;
        assert notificationManager != null;
        notificationManager.notify(id , notification) ;
    }
}