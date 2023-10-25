package com.polidea.rxandroidble2.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class AllowAppAlwaysRun2 extends AppCompatActivity {

    Button NextBtn;
    TextView txt_action;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_allow_app_always_run2);
        NextBtn = findViewById(R.id.NextBtn);
        NextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in =new Intent(AllowAppAlwaysRun2.this,Transmitter.class);
                startActivity(in);
                finish();
            }
        });
        txt_action = findViewById(R.id.txt_action);
     /*   txt_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in =new Intent(AllowAppAlwaysRun2.this,allowAppAlways.class);
                startActivity(in);
                finish();
            }
        });*/
    }
}