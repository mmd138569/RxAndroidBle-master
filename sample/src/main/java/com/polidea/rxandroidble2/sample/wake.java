package com.polidea.rxandroidble2.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class wake extends AppCompatActivity {
    AlarmManager alarmManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wake);
        //Button dismiss=findViewById(R.id.dismiss);
       // dismiss.setOnClickListener(new View.OnClickListener() {
         //   @Override
          //  public void onClick(View v) {
             //   dismiss.setVisibility(View.INVISIBLE);
             //   cancelAlarm();
          //  }
       // });
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O_MR1){
            setShowWhenLocked(true);
            setTurnScreenOn(true);
            KeyguardManager keyguardManager=(KeyguardManager) getSystemService(this.KEYGUARD_SERVICE);
            keyguardManager.requestDismissKeyguard(this,null);
        }
        else{
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON|
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

    }
    public void cancelAlarm() {
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent("Myservice");
        PendingIntent sender = PendingIntent.getBroadcast(this,0,intent,0);
        alarmManager.cancel(sender);
        sender.cancel();
        Toast.makeText(getApplicationContext(),"Canceled",Toast.LENGTH_SHORT).show();
    }
}