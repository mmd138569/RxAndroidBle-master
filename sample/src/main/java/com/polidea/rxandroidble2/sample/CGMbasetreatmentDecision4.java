package com.polidea.rxandroidble2.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.polidea.rxandroidble2.sample.example1_scanning.ScanActivity;

public class CGMbasetreatmentDecision4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Configuration config = getResources().getConfiguration();
        if(config.smallestScreenWidthDp>700){
            setContentView(R.layout.activity_cgmbasetreatment_decision4);
        }
        else {
            setContentView(R.layout.cgmbasetreatmentdecision4_forsmallphone);
        }
        VideoView video =findViewById(R.id.video);
        // Button btnaction=findViewById(R.id.btn_action);
        Button understandbtn= findViewById(R.id.undrastandbtn);
        TextView btn_action;

        String vpath="android.resource://"+getPackageName()+"/raw/android_11v";
        Uri videoURI= Uri.parse(vpath);
        video.setVideoURI(videoURI);
        video.start();
        MediaController mediaController=new MediaController(this);
        video.setMediaController(mediaController);
        mediaController.setAnchorView(video);

     /*   btnaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        understandbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in =new Intent(CGMbasetreatmentDecision4.this, safety.class);
                ActivityOptions options =
                        ActivityOptions.makeCustomAnimation(CGMbasetreatmentDecision4.this, R.anim.animationint, R.anim.anim);
                CGMbasetreatmentDecision4.this.startActivity(in, options.toBundle());
            }
        });
        btn_action = findViewById(R.id.btn_action);
        btn_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in =new Intent(CGMbasetreatmentDecision4.this,CGMbasetreatmentDecison3.class);
                startActivity(in);
                finish();
            }
        });
        ImageView txt = findViewById(R.id.txt);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in =new Intent(CGMbasetreatmentDecision4.this,CGMbasetreatmentDecison3.class);
                startActivity(in);
                finish();
            }
        });
    }

}