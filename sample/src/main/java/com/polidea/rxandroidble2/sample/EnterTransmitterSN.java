package com.polidea.rxandroidble2.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.polidea.rxandroidble2.sample.example1_scanning.ScanActivity;

public class EnterTransmitterSN extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_enter_transmitter_sn);
        Button button=findViewById(R.id.NextBtn);
        EditText editText =findViewById(R.id.lastdigits);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(EnterTransmitterSN.this, ScanActivity.class);
                ActivityOptions options =
                        ActivityOptions.makeCustomAnimation(EnterTransmitterSN.this, R.anim.animationint, R.anim.anim);
                EnterTransmitterSN.this.startActivity(in, options.toBundle());

            }
           /* String str= editText.getText().toString();
            lastdigits(str);*/

        });
    }
  /*  public static void lastdigits(String string){
        string
    }*/
}