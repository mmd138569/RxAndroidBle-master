package com.polidea.rxandroidble2.sample;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.polidea.rxandroidble2.sample.example4_characteristic.CharacteristicOperationExampleActivity;
public class myservice extends Service {
    Bitmap bitmap2;
    double q=0;
    int f=0;
    int NOTIFICATION_ID = (int) (System.currentTimeMillis()%10000);
    int width = CharacteristicOperationExampleActivity.lineChart.getChartBitmap().getWidth();
    int height = CharacteristicOperationExampleActivity.lineChart.getChartBitmap().getWidth();
    int time=0;
    int songUrl;
    public myservice() {
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        if (intent != null && intent.getExtras() != null){
            songUrl = intent.getIntExtra("YOUR_KEY_SONG_NAME",0);
        }

        //onTaskRemoved(intent);
        Handler handler=new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                notification("it should be always run ", songUrl);
                if(songUrl>250){
                    startAlert();
                }
                time= time+100;
                //  Toast.makeText(getApplicationContext(),"This is a Service running in Background", Toast.LENGTH_SHORT).show();
                //we write our stuff that want to run here this service is already run at the back ground
                handler.postDelayed(this, 80000);
            }
        },80000);
        return START_STICKY;
    }
    @Override
    public void onCreate() {

        Handler h = new Handler();
        if(time==0) {
            Runnable r = new Runnable() {
                @Override
                public void run() {

                    notification("first time", songUrl);


                }
            };
            h.postDelayed(r, 5000);
        }
        //notification("oncreate", songUrl);

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
    /*public void notif(String str,int BloodNum){
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            startMyOwnForeground();
        else
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

      /*  builder.setSmallIcon(R.drawable.baseline_notifications_active_24)
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
    }*/
      @RequiresApi(api = Build.VERSION_CODES.N)
      public void notification(String str, int BloodNum) {

          String chanellID = "this is our id notify";
       //   Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.splashlogo);
          Bitmap bitmap=CharacteristicOperationExampleActivity.pieChart.getChartBitmap();
          int maxHeight = 402, maxWidth = 360; // Maximum width for the bitmap in pixels
         // Bitmap bitmap2= BitmapFactory.decodeResource(getResources(),R.drawable.splashlogo);
          Bitmap bitmap2=CharacteristicOperationExampleActivity.pieChart.getChartBitmap();
          float aspectRatio = (float) width / height;
         if (width > maxWidth || height > maxHeight) {
              // The bitmap is larger than the maximum dimensions, so resize it
              if (width > height) {
                  width = maxWidth;
                  height = (int) (width / aspectRatio);
              } else {
                  height = maxHeight;
                  width = (int) (height * aspectRatio);
              }

          bitmap2 = CharacteristicOperationExampleActivity.lineChart.getChartBitmap();
          //bitmap2 = Bitmap.createScaledBitmap(bitmap2, width, height, true);

            bitmap2 = Bitmap.createScaledBitmap(bitmap2, width, height, true);

        }
         // CharacteristicOperationExampleActivity.customchart();
          NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), chanellID);
          Notification notification = builder.setSmallIcon(R.drawable.baseline_notifications_active_24)
                  .setContentTitle("warning!")
                  .setContentText(str + BloodNum)
                  //remove the notification after clicking on it
                 // .setAutoCancel(true)
                  .setOngoing(true)
                  .setVibrate(null)
                  .setSound(null)
                  .setVisibility(NotificationCompat.VISIBILITY_SECRET)
                  .setPriority(NotificationCompat.PRIORITY_LOW)
                  .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap))
                  .build();
          builder.setLargeIcon(bitmap);
          builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap).bigLargeIcon(null));
          builder.setSmallIcon(R.drawable.baseline_notifications_active_24)
                  .setContentTitle("warning")
                  .setContentText(str + BloodNum)
                  .setLargeIcon(bitmap)
                 // .setAutoCancel(false)
                  .setOngoing(true)
                  .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap2).bigLargeIcon(null))
                  .build();
          //here if you wanna intent to an activity you should difine the mac address first to forbid the null exception then remove it from comment
          //Intent intent = new Intent(getApplicationContext(), CharacteristicOperationExampleActivity.class);
         // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
         // intent.putExtra("data", "some value come here");
         // PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_MUTABLE);
         // builder.setContentIntent(pendingIntent);
          NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE) {
              NotificationChannel notificationChannel = null;
              if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                  notificationChannel = notificationManager.getNotificationChannel(chanellID);
              }
              if (notificationChannel == null) {
                  //here we mute the notification
                  int importance = NotificationManager.IMPORTANCE_NONE;
                  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                      notificationChannel = new NotificationChannel(chanellID, "somethings", importance);
                  notificationChannel.setLightColor(Color.GREEN);
                  notificationChannel.setDescription("hi there");
                  notificationChannel.enableVibration(true);
                  notificationManager.createNotificationChannel(notificationChannel); }

                /*  q=Math.random();
                  q=q*1001;
                  f=(int)q;
                  startForeground(f, builder.build());*/
                  startForeground(NOTIFICATION_ID,notification);


                  }
          }

          notificationManager.notify(NOTIFICATION_ID, builder.build());
      }
    public void startAlert () {
        int i = 200000;
        //   int i = Integer.parseInt(text.getText().toString());
        // if((i<50)||(i>300)) {
        Intent intent = new Intent(myservice.this, MyBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this.getApplicationContext(), 0, intent, PendingIntent.FLAG_MUTABLE);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                + (0), pendingIntent);
        Toast.makeText(this, "Alarm set in now", Toast.LENGTH_LONG).show();
        //}
    }
    private void startMyOwnForeground(){
        String NOTIFICATION_CHANNEL_ID = "com.example.simpleapp";
        String channelName = "My Background Service";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.drawable.baseline_notifications_active_24)
                .setContentTitle("App is running in background")
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        startForeground(2, notification);
    }

}
