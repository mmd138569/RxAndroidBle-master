package com.polidea.rxandroidble2.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.polidea.rxandroidble2.sample.example1_scanning.ScanActivity;

public class allowAppAlways extends AppCompatActivity {
    Button button;
    TextView txt_action;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Configuration config = getResources().getConfiguration();
        if(config.smallestScreenWidthDp>300){
            setContentView(R.layout.activity_allow_app_always);
        }
        else {
            setContentView(R.layout.allowappalways_smallphone);
        }
        button =findViewById(R.id.allowAppBtn);
        ignoreBatteryOptimization();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   if(Build.VERSION.SDK_INT>=26) {

                // }
                   /* if (isMyServiceRunning(MyService.class)) {
                        // The service is running
                    } else {
                        startService(new Intent(getApplicationContext(), MyService.class));
                    }

                if (isMyServiceRunning(MyService.class)) {
                    // The service is running
                } else {
                    startService(new Intent(getApplicationContext(), MyService.class));
                }*/

                Intent in=new Intent(allowAppAlways.this, AllowAppAlwaysRun2.class);
                ActivityOptions options =
                        ActivityOptions.makeCustomAnimation(allowAppAlways.this, R.anim.animationint, R.anim.anim);
                allowAppAlways.this.startActivity(in, options.toBundle());
            }
        });

       /* if (!foregroundServiceRunning()) {
            Intent serviceinetnt = new Intent(allowAppAlways.this, myservice.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(serviceinetnt);
            }

        }*/

        txt_action =findViewById(R.id.txt_action);
        txt_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(allowAppAlways.this,doNotDisturb.class);
                startActivity(in);
                finish();
            }
        });
    }
    private void ignoreBatteryOptimization() {
        Intent intent = new Intent();
        String packageName = getPackageName();
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (pm != null && !pm.isIgnoringBatteryOptimizations(packageName)) {
                intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + packageName));
                startActivity(intent);
            }
        }
    }
    public boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
    public boolean foregroundServiceRunning(){
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo service: activityManager.getRunningServices(Integer.MAX_VALUE)) {
            if(myservice.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}