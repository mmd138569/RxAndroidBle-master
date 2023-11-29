package com.polidea.rxandroidble2.sample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver
{
        MediaPlayer media;
@Override
public void onReceive(Context context, Intent intent) {
        Intent alarmintent= new Intent(context,wake.class);
        alarmintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        media=MediaPlayer.create(context, R.raw.alarm);
        media.start();

        Toast.makeText(context, "Alarm....", Toast.LENGTH_LONG).show();
        }
}