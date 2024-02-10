package com.polidea.rxandroidble2.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;

import java.util.ArrayList;

public class graph_Height extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_graph_height);
        RadioButton radioButton=findViewById(R.id.min_hight);
        RadioButton radioButton1= findViewById(R.id.max_hight);

        final DBChart helper = new DBChart(graph_Height.this);
        final ArrayList array_list = helper.getAllCotact1();
        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = Integer.parseInt("300");
                if (x != 0) {
                    helper.insert(x);
                    System.out.println(helper.getAllCotact1() + "=============================");
                }
            }
        });
        radioButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = Integer.parseInt("400");
                if (x != 0) {
                    helper.insert(x);
                    System.out.println(helper.getAllCotact1() + "=============================");
                }
            }
        });
    }
}