package com.polidea.rxandroidble2.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class CGMbasetreatmentDecision extends AppCompatActivity {
    Button Iundrstand;
    float x1,x2,y2,y1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_cgmbasetreatment_decision);
        Iundrstand = findViewById(R.id.Iundrstand);
        Iundrstand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in =new Intent(CGMbasetreatmentDecision.this,CGMbasetreatmentDecsion2.class);
                ActivityOptions options =
                        ActivityOptions.makeCustomAnimation(CGMbasetreatmentDecision.this, R.anim.animationint, R.anim.anim);
                CGMbasetreatmentDecision.this.startActivity(in, options.toBundle());
            }
        });
        TextView txt_action =findViewById(R.id.txt_action);
        txt_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(CGMbasetreatmentDecision.this, mainlogin.class);
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
                    Intent in = new Intent(CGMbasetreatmentDecision.this,mainlogin.class);
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