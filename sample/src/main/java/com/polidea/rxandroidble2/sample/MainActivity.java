package com.polidea.rxandroidble2.sample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.polidea.rxandroidble2.sample.example1_scanning.ScanActivity;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    ImageView img;
    Animation bottom_animation,top_animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean isFirstTime = preferences.getBoolean("isFirstTime", true);

        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_main);
        bottom_animation = AnimationUtils.loadAnimation(MainActivity.this, R.animator.animation);
        img = findViewById(R.id.imglogo);
        img.setAnimation(bottom_animation);
        top_animation = AnimationUtils.loadAnimation(MainActivity.this, R.animator.textanimation);
        textView = findViewById(R.id.txt);
        textView.setAnimation(top_animation);
        Handler h = new Handler();
        Runnable r = new Runnable() {
            @Override
            public void run() {/*
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                        //Intent in = new Intent(MainActivity.this, EntertransmitterSN1.class);
                        //Intent in = new Intent(MainActivity.this, EntertransmitterSN1.class);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean("isFirstTime", false);
                        editor.apply();

                        Intent in = new Intent(MainActivity.this, warning.class);
                        startActivity(in);
                        finish();
                    }
                    else if(!isFirstTime){
                        Intent in = new Intent(MainActivity.this, mainlogin.class);
                        startActivity(in);
                        finish();
                    }
                    else if(ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.BLUETOOTH_CONNECT)== PackageManager.PERMISSION_DENIED){
                            Intent in = new Intent(MainActivity.this, bluetooth.class);
                            startActivity(in);
                            finish();
                    }
                    else {
                        Intent in = new Intent(MainActivity.this, EntertransmitterSN1.class);
                        startActivity(in);
                        finish();
                        }
                }
                else if(isFirstTime){
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("isFirstTime", false);
                    editor.apply();

                    Intent in = new Intent(MainActivity.this, warning.class);
                    startActivity(in);
                    finish();

                }
                else{
                    Intent in = new Intent(MainActivity.this, mainlogin.class);
                    startActivity(in);
                    finish();
                }
                
*/
                Intent in = new Intent(MainActivity.this, EntertransmitterSN1.class);
                startActivity(in);
                finish();
            }
        };
        h.postDelayed(r, 1500);
    }
}