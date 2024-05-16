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

public class whatsNew extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_whats_new);
        Button warningsection =findViewById(R.id.warningsection);
        warningsection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(whatsNew.this, allertSound.class);
                ActivityOptions options =
                        ActivityOptions.makeCustomAnimation(whatsNew.this, R.anim.animationint, R.anim.anim);
                whatsNew.this.startActivity(in, options.toBundle());
                finish();
            }
        });
        TextView txt_action =findViewById(R.id.txt_action);
        ImageView txt=findViewById(R.id.txt);
        txt_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(whatsNew.this, importingSetting2.class);
                startActivity(in);
                finish();
            }
        });
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(whatsNew.this, importingSetting2.class);
                startActivity(in);
                finish();
            }
        });
    }
}