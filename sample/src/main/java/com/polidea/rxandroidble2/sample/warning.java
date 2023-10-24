package com.polidea.rxandroidble2.sample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.polidea.rxandroidble2.sample.example1_scanning.ScanActivity;

public class warning extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_warning);

        setContentView(R.layout.activity_warning);
        Button buttonEnableBluetooth = findViewById(R.id.warningsection);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O_MR1){
            if(ContextCompat.checkSelfPermission(warning.this, Manifest.permission.POST_NOTIFICATIONS)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(warning.this,new String[]{Manifest.permission.POST_NOTIFICATIONS},101);
            }
        }
        buttonEnableBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(warning.this, ScanActivity.class);
                startActivity(in);
                finish();
            }
        });
    }
}