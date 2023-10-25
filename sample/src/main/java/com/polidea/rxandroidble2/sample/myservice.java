package com.polidea.rxandroidble2.sample;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

public class myservice extends Service {
    int time=0;

    public myservice() {
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        //onTaskRemoved(intent);
        Handler handler=new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                notif("it should be always run ", time);
                time= time+100;
                //  Toast.makeText(getApplicationContext(),"This is a Service running in Background", Toast.LENGTH_SHORT).show();
                //we write our stuff that want to run here this service is already run at the back ground
                handler.postDelayed(this, 20000);
            }
        },20000);
        return START_STICKY;
    }
    @Override
    public void onCreate() {

        //startForeground(0,);
    }
    @Override
    public IBinder onBind(Intent intent) {

        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Intent restartServiceIntent = new Intent(getApplicationContext(),this.getClass());
        restartServiceIntent.setPackage(getPackageName());
        startService(restartServiceIntent);
        super.onTaskRemoved(rootIntent);
    }
    public void notif(String str,int BloodNum){
        String chanellID ="this is our id notif";

        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.splashlogo);
        Bitmap bitmap2= BitmapFactory.decodeResource(getResources(),R.drawable.splashlogo);

        //Bitmap bitmap2= chart.lineChart.getChartBitmap();
        // chart.customchart();
        NotificationCompat.Builder builder=new NotificationCompat.Builder(getApplicationContext(),chanellID);

        Notification notification=builder.setSmallIcon(R.drawable.baseline_notifications_active_24)
                .setContentTitle("warning!")
                .setContentText(str+BloodNum)
                .setAutoCancel(true)
                .setOngoing(true)
                .setVisibility(NotificationCompat.VISIBILITY_SECRET)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap))
                .build();
        startForeground(1001,notification);
     /*   if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            stopForeground(true);
        }
        else {
            stopForeground(true);
        }
        stopSelf();*/
        // builder.setLargeIcon(bitmap);
        //   builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap).bigLargeIcon(null));

        builder.setSmallIcon(R.drawable.baseline_notifications_active_24)
                .setContentTitle("warning")
                .setContentText(str+BloodNum)
                .setLargeIcon(bitmap)
                .setAutoCancel(false)
                .setOngoing(true)
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap2).bigLargeIcon(null))
                .build();
        //here
        Intent intent=new Intent(getApplicationContext(),warning.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("data","some value come here");
        PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),0,intent,PendingIntent.FLAG_MUTABLE);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.BASE){
            NotificationChannel notificationChannel=notificationManager.getNotificationChannel(chanellID);
            if(notificationChannel==null){
                int importance=NotificationManager.IMPORTANCE_HIGH;
                notificationChannel=new NotificationChannel(chanellID,"somethings",importance);
                notificationChannel.setLightColor(Color.GREEN);
                notificationChannel.enableVibration(true);
                assert notificationManager!=null;
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
        notificationManager.notify(1001,builder.build());
    }
}
