package com.polidea.rxandroidble2.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.polidea.rxandroidble2.sample.example1_scanning.ScanActivity;

import java.util.Locale;

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

                String str1=editText.getText().toString();
                if(editText.getText()==null){
                    Toast.makeText(EnterTransmitterSN.this, "pls Enter something", Toast.LENGTH_SHORT).show();
                }


                    String sa = mystr(str1);
                    in.putExtra("my_mac", sa);
                    ActivityOptions options =
                            ActivityOptions.makeCustomAnimation(EnterTransmitterSN.this, R.anim.animationint, R.anim.anim);
                    EnterTransmitterSN.this.startActivity(in, options.toBundle());
                }

           /* String str= editText.getText().toString();
            lastdigits(str);*/

        });

    }
    public String mystr(String str1){
        String newString="";
        String insert=":";
        for(int i=0;i<6;i++){
           newString+= str1.charAt(i);
           if(i%2==1&&i!=5){
               newString+=insert;
           }
        }
        newString=newString.substring(0,8).toUpperCase() ;

        return newString;
    }
  /*  public static void lastdigits(String string){
        string
    }*/
}