package com.polidea.rxandroidble2.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.polidea.rxandroidble2.sample.example1_scanning.ScanActivity;

public class importingSetting2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_importing_setting2);
        Button bluetoothbtn =findViewById(R.id.bluetoothbtn);
        bluetoothbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(importingSetting2.this, whatsNew.class);
                ActivityOptions options =
                        ActivityOptions.makeCustomAnimation(importingSetting2.this, R.anim.animationint, R.anim.anim);
                importingSetting2.this.startActivity(in, options.toBundle());
                finish();
            }
        });
        TextView txt_action =findViewById(R.id.txt_action);
        txt_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(importingSetting2.this, imoprtSetting1.class);
                startActivity(in);
                finish();
            }
        });
        ImageView txt =findViewById(R.id.txt);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(importingSetting2.this, imoprtSetting1.class);
                startActivity(in);
                finish();
            }
        });
    }
}