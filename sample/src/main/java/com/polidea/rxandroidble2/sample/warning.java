package com.polidea.rxandroidble2.sample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.polidea.rxandroidble2.sample.example1_scanning.ScanActivity;

public class warning extends AppCompatActivity {
    public NotificationPermissionHelper notificationPermissionHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_warning);
        Button buttonEnableBluetooth = findViewById(R.id.warningsection);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.TIRAMISU){
            if(ContextCompat.checkSelfPermission(warning.this, Manifest.permission.POST_NOTIFICATIONS)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(warning.this,new String[]{Manifest.permission.POST_NOTIFICATIONS},101);
            }
            else{
                notificationPermissionHelper = new NotificationPermissionHelper(this);
            }
        }
//====================================================================================

       /* if (!notificationPermissionHelper.isNotificationPermissionGranted()) {
            if (!notificationPermissionHelper.hasNotificationPermission()) {
                notificationPermissionHelper.requestNotificationPermission();
            }
        }*/

//=====================================================================================

 buttonEnableBluetooth.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent in = new Intent(warning.this, start.class);
            //Intent in = new Intent(warning.this, Transmitter.class);

            ActivityOptions options =
                    ActivityOptions.makeCustomAnimation(warning.this, R.anim.animationint, R.anim.anim);
            warning.this.startActivity(in, options.toBundle());

        }
 });
    }
}


