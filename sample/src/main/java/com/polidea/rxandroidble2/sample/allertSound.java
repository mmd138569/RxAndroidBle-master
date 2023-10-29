package com.polidea.rxandroidble2.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class allertSound extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_allert_sound);
        Button NxtBtn =findViewById(R.id.NxtBtn);
        NxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(allertSound.this, doNotDisturb.class);
                ActivityOptions options =
                        ActivityOptions.makeCustomAnimation(allertSound.this, R.anim.animationint, R.anim.anim);
                allertSound.this.startActivity(in, options.toBundle());
            }
        });
        TextView txt_action=findViewById(R.id.txt_action);
        txt_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(allertSound.this, whatsNew.class);
                startActivity(in);
                finish();
            }
        });
    }
}