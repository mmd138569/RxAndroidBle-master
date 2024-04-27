package com.polidea.rxandroidble2.sample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.widget.Toast;

import java.util.logging.LogRecord;

public class MyBroadcastReceiver extends BroadcastReceiver
{
        private boolean isReceiverRegistered = false;
        MediaPlayer media;
@Override
public void onReceive(Context context, Intent intent) {
        Intent alarmintent= new Intent(context,wake.class);
        alarmintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        media=MediaPlayer.create(context, R.raw.alert);
        media.start();

        //Toast.makeText(context, "Alarm....", Toast.LENGTH_LONG).show();
        Handler h = new Handler();
        Runnable runnable=new Runnable() {
                @Override
                public void run() {
                        media.stop();
                }
        };h.postDelayed(runnable,5000);
}

}