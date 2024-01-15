package com.polidea.rxandroidble2.sample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.polidea.rxandroidble2.sample.example4_characteristic.CharacteristicOperationExampleActivity;
import com.polidea.rxandroidble2.sample.example4_characteristic.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class settings extends AppCompatActivity {
    int x=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        EditText editTe=findViewById(R.id.calibration);
        Button calibration=findViewById(R.id.calibrations);


         final DBcalibrate helper = new DBcalibrate(settings.this);
        final ArrayList array_list = helper.getAllCotacts1();
        /*String str = helper.getcalibriation();
        str= editTe.toString();
        helper.insert(Integer.parseInt(editTe.getText().toString()));
*/
        Intent intent = getIntent();
         intent.getStringExtra("mac_add");
        //Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
      calibration.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              int x=Integer.parseInt(editTe.getText().toString());
              if(x!=0){
                  helper.insert(x);
                  System.out.println(helper.getAllCotacts1()+"=============================");
              }
          }
      });
        //Toast.makeText(settings.this, x, Toast.LENGTH_SHORT).show();

    }
}