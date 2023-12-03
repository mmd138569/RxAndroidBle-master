package com.polidea.rxandroidble2.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class safety extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Configuration config = getResources().getConfiguration();
        if(config.smallestScreenWidthDp>700){
            setContentView(R.layout.activity_safety);
        }
        else {
            setContentView(R.layout.saftey_smallphone);
        }

        Button safetybtn =findViewById(R.id.safetybtn);
        safetybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(safety.this, imoprtSetting1.class);
                ActivityOptions options =
                        ActivityOptions.makeCustomAnimation(safety.this, R.anim.animationint, R.anim.anim);
                safety.this.startActivity(in, options.toBundle());
            }
        });
        TextView txt_action =findViewById(R.id.txt_action);
        ImageView txt =findViewById(R.id.txt);

        txt_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(safety.this, CGMbasetreatmentDecision4.class);
                startActivity(in);
                finish();
            }
        });
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(safety.this, CGMbasetreatmentDecision4.class);
                startActivity(in);
                finish();
            }
        });
    }
}