package com.polidea.rxandroidble2.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CGMbasetreatmentDecison3 extends AppCompatActivity {
    Button CgmBtn;
    float x1,x2,y1,y2;

    TextView txt_action;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Configuration config = getResources().getConfiguration();
        if(config.smallestScreenWidthDp>700){
            setContentView(R.layout.activity_cgmbasetreatment_decison3);
        }
        else {
            setContentView(R.layout.cgmbasetreatmentdecison3_smallphone);
        }
        CgmBtn = findViewById(R.id.iundrestandBtn);
        CgmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in =new Intent(CGMbasetreatmentDecison3.this,CGMbasetreatmentDecision4.class);
                ActivityOptions options =
                        ActivityOptions.makeCustomAnimation(CGMbasetreatmentDecison3.this, R.anim.animationint, R.anim.anim);
                CGMbasetreatmentDecison3.this.startActivity(in, options.toBundle());
            }
        });
        txt_action = findViewById(R.id.txt_action);
        txt_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in =new Intent(CGMbasetreatmentDecison3.this,CGMbasetreatmentDecsion2.class);
                startActivity(in);
                finish();
            }
        });
       ImageView txt = findViewById(R.id.txt);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in =new Intent(CGMbasetreatmentDecison3.this,CGMbasetreatmentDecsion2.class);
                startActivity(in);
                finish();
            }
        });
    }
    public boolean onTouchEvent(MotionEvent Touchevent){
        switch(Touchevent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1=Touchevent.getX();
                y1=Touchevent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2=Touchevent.getX();
                y2=Touchevent.getY();
                if(x1<x2){
                    Intent in = new Intent(CGMbasetreatmentDecison3.this,CGMbasetreatmentDecsion2.class);
                    startActivity(in);
                    finish();
                }
                else if(x1>x2){

                }
                break;
        }
        return false;
    }
}