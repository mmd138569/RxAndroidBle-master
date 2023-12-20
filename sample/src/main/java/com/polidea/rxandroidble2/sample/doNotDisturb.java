package com.polidea.rxandroidble2.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.polidea.rxandroidble2.sample.example1_scanning.ScanActivity;

public class doNotDisturb extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /*Configuration config = getResources().getConfiguration();
        if(config.smallestScreenWidthDp>300){
            setContentView(R.layout.activity_do_not_disturb);
        }
        else {
            setContentView(R.layout.donotdisturb_smallphone);
        }*/
        DisplayMetrics displayMetrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenhight=displayMetrics.heightPixels;
        if(screenhight>=1000){
            setContentView(R.layout.activity_do_not_disturb);
        }
        else if(screenhight<=1000){
            setContentView(R.layout.donotdisturb_smallphone);
        }
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Button NextBtn = findViewById(R.id.NextBtn);
            NextBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (notificationManager.isNotificationPolicyAccessGranted()) {
                            // notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_PRIORITY);
                           Intent intent = new Intent(doNotDisturb.this, allowAppAlways.class);
                            ActivityOptions options =
                                    ActivityOptions.makeCustomAnimation(doNotDisturb.this, R.anim.animationint, R.anim.anim);
                            doNotDisturb.this.startActivity(intent, options.toBundle());
                        } else {
                            Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                            startActivity(intent);
                        }
                    }
                }
            });

            TextView txt_action = findViewById(R.id.txt_action);
            txt_action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(doNotDisturb.this, allertSound.class);
                    ActivityOptions options =
                            ActivityOptions.makeCustomAnimation(doNotDisturb.this, R.anim.animationint, R.anim.anim);
                    doNotDisturb.this.startActivity(in, options.toBundle());
                }
            });
            ImageView txt = findViewById(R.id.txt);
            txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(doNotDisturb.this, allertSound.class);
                    startActivity(in);
                    finish();
                }
            });
       /* NextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in =new Intent(doNotDisturb.this,allowAppAlways.class);
                startActivity(in);
                finish();
            }
        });*/

        }
}