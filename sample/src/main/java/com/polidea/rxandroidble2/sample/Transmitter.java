package com.polidea.rxandroidble2.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class Transmitter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_transmitter);
        Button NextBtn =findViewById(R.id.NextBtn);
        NextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Transmitter.this, bluetoothTransmitter.class);
                ActivityOptions options =
                        ActivityOptions.makeCustomAnimation(Transmitter.this, R.anim.animationint, R.anim.anim);
                Transmitter.this.startActivity(in, options.toBundle());
            }
        });
        TextView txt_action =findViewById(R.id.txt_action);
        ImageView txt =findViewById(R.id.txt);

        txt_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Transmitter.this, AllowAppAlwaysRun2.class);
                startActivity(in);
                finish();
            }
        });
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Transmitter.this, AllowAppAlwaysRun2.class);
                startActivity(in);
                finish();
            }
        });
    }
}