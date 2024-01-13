package com.polidea.rxandroidble2.sample;

import androidx.appcompat.app.AppCompatActivity;

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

public class settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        EditText editTe=findViewById(R.id.calibration);
        Button calibration=findViewById(R.id.calibrations);
        final DatabaseHelper helper = new DatabaseHelper(settings.this);
        /*String str = helper.getcalibriation();
        str= editTe.toString();
        helper.insert(Integer.parseInt(editTe.getText().toString()));
*/
        Intent intent = getIntent();
         intent.getStringExtra("mac_add");
        //Toast.makeText(this, str, Toast.LENGTH_SHORT).show();

    }
}