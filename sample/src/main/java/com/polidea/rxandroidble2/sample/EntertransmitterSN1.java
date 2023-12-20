package com.polidea.rxandroidble2.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class EntertransmitterSN1 extends AppCompatActivity {


    public static int [] a={0};
    int x=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        /*Configuration config = getResources().getConfiguration();
        if(config.smallestScreenWidthDp>300){
            setContentView(R.layout.activity_entertransmitter_sn1);
        }
        else {
            setContentView(R.layout.entertransmitter_sn1_smallphone);
        }*/
        DisplayMetrics displayMetrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenhight=displayMetrics.heightPixels;
        if(screenhight>=1000){
            setContentView(R.layout.activity_enter_transmitter_sn);
        }
        else if(screenhight<=1000){
            setContentView(R.layout.entertransmitter_sn1_smallphone);
        }
        Button takephoto=findViewById(R.id.TAKEPHOTO);
        Button entermanually=findViewById(R.id.ENTERMANUALLY);
        takephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                x=1;
                seta(a,x);
                Intent in =new Intent(EntertransmitterSN1.this,qrcode.class);
                startActivity(in);
            }
        });
        entermanually.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                x=2;
            seta(a,x);
                Intent in1=new Intent(EntertransmitterSN1.this,EnterTransmitterSN.class);

                startActivity(in1);
            }
        });
    }
    public static int geta() { return a[0]; }

    public void seta(int []a,int x) { this.a[0] = x; }
}