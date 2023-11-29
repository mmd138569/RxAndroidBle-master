package com.polidea.rxandroidble2.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EntertransmitterSN1 extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration config = getResources().getConfiguration();
        if(config.smallestScreenWidthDp>700){
            setContentView(R.layout.activity_entertransmitter_sn1);
        }
        else {
            setContentView(R.layout.entertransmitter_sn1_smallphone);
        }
        Button takephoto=findViewById(R.id.TAKEPHOTO);
        Button entermanually=findViewById(R.id.ENTERMANUALLY);
        takephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in =new Intent(EntertransmitterSN1.this,qrcode.class);
                startActivity(in);
                finish();
            }
        });
        entermanually.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in1=new Intent(EntertransmitterSN1.this,EnterTransmitterSN.class);
                startActivity(in1);
                finish();
            }
        });
    }
}