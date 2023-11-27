package com.polidea.rxandroidble2.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
            public void run() {
                // Intent in = new Intent(MainActivity.this, EnterTransmitterSN.class);
                Intent in =new Intent(MainActivity.this,start.class);
                startActivity(in);
                finish();
            }
        };
        h.postDelayed(r, 1500);
    }
}