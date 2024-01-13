package com.polidea.rxandroidble2.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
        calibration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(settings.this, CharacteristicOperationExampleActivity.class);
                startActivity(in);
                finish();
            }
        });
    }
}