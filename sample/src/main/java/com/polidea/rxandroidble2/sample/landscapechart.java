package com.polidea.rxandroidble2.sample;

import static android.graphics.Color.GRAY;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.polidea.rxandroidble2.sample.example4_characteristic.CharacteristicOperationExampleActivity;
import com.polidea.rxandroidble2.sample.example4_characteristic.CustomLineChart;
import com.polidea.rxandroidble2.sample.example4_characteristic.DatabaseHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class landscapechart extends AppCompatActivity {
    private CustomLineChart lineChart;

    int entrySize = 288;
    Button button1;
    Button button2;
    Button button3 ;
    Button button4;
    Button button5 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_landscapechart);

        button1=  findViewById(R.id.btton1);
        button2=  findViewById(R.id.btton2);
        button3=  findViewById(R.id.btton3);
        button4=  findViewById(R.id.btton4);
        button5=  findViewById(R.id.btton5);
        lineChart = findViewById(R.id.landchart);
        lineChart.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {

                        lineChart.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        int offset = (lineChart.getHeight() - lineChart.getWidth()) / 2;

                        LinearLayout.LayoutParams layoutParams = ( LinearLayout.LayoutParams) lineChart.getLayoutParams();
                        layoutParams.width = lineChart.getHeight();
                        layoutParams.height = lineChart.getWidth();
                        lineChart.setLayoutParams(layoutParams);

                        lineChart.setTranslationX(-offset);
                        lineChart.setTranslationY(offset);
                        float rangeHigh = 100f;
                        float rangeLow = -7f;
                        float rangeLow2=103f;
                        float rangeHigh2=250f;
                        float rangeLow3=253f;
                        float rangeHigh3=270f;
                        lineChart.setTouchEnabled(true);
                        lineChart.setScaleEnabled(false);
                        lineChart.addTargetZone(new CustomLineChart.TargetZone( Color.parseColor("#feebe5"),rangeLow,rangeHigh,""));
                        lineChart.addTargetZone(new CustomLineChart.TargetZone( Color.parseColor("#dfdfdf"),rangeLow2,rangeHigh2,""));
                        lineChart.addTargetZone(new CustomLineChart.TargetZone( Color.parseColor("#fef5e6"),rangeLow3,rangeHigh3,""));

                        float time=(float)new Date().getTime();
                        initLineChart();
                        button1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                initLineChrt(24,time);
                            }
                        });
                        button2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                initLineChrt(12,time);
                            }
                        });
                        button3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                initLineChrt(8,time);
                            }
                        });
                        button4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                initLineChrt(3,time);
                            }
                        });
                        button5.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                initLineChrt(1,time);
                            }
                        });
                    }
                });

    }
    private void initLineChart(){
        //    lineChart.setTouchEnabled(true);
        lineChart.getXAxis().setAxisMaximum(24f);
        lineChart.getAxisLeft().setAxisMaximum(270f);
        lineChart.getAxisRight().setAxisMaximum(270f);
        lineChart.getXAxis().setDrawGridLines(false);//disable vertical line
        lineChart.getAxisLeft().setDrawGridLines(false);//disiable horizental
        lineChart.getAxisRight().setDrawGridLines(false);//disable horizantal'
        YAxis RightAxis = lineChart.getAxisRight();
        RightAxis.setTextSize(0f);//put it bottom
        RightAxis.setTextColor(Color.TRANSPARENT);
        RightAxis.setDrawAxisLine(false);
        RightAxis.setDrawGridLines(false);

        CustomMarkerview mv = new CustomMarkerview (this, R.layout.custommarkerview);
        lineChart.setMarkerView(mv);
       /* Legend legend=lineChart.getLegend();
        legend.setTextSize(10f);
        legend.setEnabled(true);
        legend.setFormSize(10f); // set the size of the legend forms/shapes
        legend.setForm(Legend.LegendForm.CIRCLE); // set what type of form/shape should be used
        legend.setTextSize(12f);
        legend.setTextColor(Color.BLACK);
        YAxis RightAxis = lineChart.getAxisRight();
        RightAxis.setTextSize(0f);//put it bottom
       RightAxis.setTextColor(Color.TRANSPARENT);
        RightAxis.setDrawAxisLine(false);
        RightAxis.setDrawGridLines(false);*/

        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);


        lineChart.animateX(4000);
        entrySize = 288;

        LineDataSet line3 = new LineDataSet(getRandomEntries(entrySize), "");
        line3.setColor(GRAY);
        line3.setCircleColor(Color.BLACK);
        line3.setDrawCircles(true);
        lineChart.getLegend().setEnabled(false);
        //---------
        //  lineChart.highlightValue(30,20);
        lineChart.invalidate();

        line3.setDrawCircleHole(true);
        line3.setLineWidth((float) 0.3);
        line3.setCircleRadius(2);
        line3.setCircleHoleRadius(10);
        line3.setValueTextColor(Color.GRAY);
        LineData lineData = new LineData(line3);
        lineChart.setData(lineData);
        lineChart.invalidate();

        lineData.setDrawValues(false);

        lineChart.getDescription().setEnabled(false);

    }
    private void initLineChrt(int num, float time){
        if(num==24){
            lineChart.getXAxis().setAxisMaximum(24f);
            entrySize = 288;}
        else if(num==12){
            lineChart.getXAxis().setAxisMaximum(12f);
            //  lineChart.getXAxis().setAxisMinimum(time);
            entrySize = 144;}
        else if(num==8){
            lineChart.getXAxis().setAxisMaximum(8f);
            entrySize = 96;}
        else if(num==3){
            lineChart.getXAxis().setAxisMaximum(3f);
            entrySize = 36;}
        else if(num==1){
            lineChart.getXAxis().setAxisMaximum(1f);
            entrySize=12;}
        //lineChart.setTouchEnabled(true);
        lineChart.getAxisLeft().setAxisMaximum(270f);
        lineChart.getAxisRight().setAxisMaximum(270f);
        lineChart.getXAxis().setDrawGridLines(false);//disable vertical line
        lineChart.getAxisLeft().setDrawGridLines(false);//disiable horizental
        lineChart.getAxisRight().setDrawGridLines(false);//disable horizantal'
        CustomMarkerview mv = new CustomMarkerview (this, R.layout.custommarkerview);
        lineChart.setMarkerView(mv);
        YAxis RightAxis = lineChart.getAxisRight();
        RightAxis.setTextSize(0f);//put it bottom
        RightAxis.setTextColor(Color.TRANSPARENT);
        RightAxis.setDrawAxisLine(false);
        RightAxis.setDrawGridLines(false);

        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        lineChart.animateX(4000);

        LineDataSet line3 = new LineDataSet(getRandomEntries(entrySize), "");

        line3.setColor(Color.TRANSPARENT);

        //line3.setColor(GRAY);
        line3.setCircleColor(Color.BLACK);
        line3.setDrawCircles(true);
        //--------


        lineChart.invalidate();


        line3.setDrawCircleHole(true);
        line3.setLineWidth((float) 0.3);
        line3.setCircleRadius(2);
        line3.setCircleHoleRadius(10);
        line3.setValueTextColor(Color.GRAY);
        LineData lineData = new LineData(line3);
        lineData.setDrawValues(false);

        lineChart.setData(lineData);
        lineChart.invalidate();
        lineChart.getDescription().setEnabled(false);
    }
    private List<Entry> getRandomEntries(int entrySize) {
        final DatabaseHelper helper1 = new DatabaseHelper(landscapechart.this);

        final ArrayList array_list1 = helper1.getAllCotacts1();

        List<Entry> entries = new ArrayList<>();
        Random random = new Random();
        array_list1.clear();
        array_list1.addAll(helper1.getAllCotacts1());
        if(array_list1.size()>entrySize){
            for (int i = 0; i < entrySize; i++) {
                entries.add(new Entry((float) (i*0.1),Float.parseFloat((String) array_list1.get(i))));
            }
        }
        else if ((array_list1.size()>144)&&(array_list1.size()<288)) {
            for (int i = 0; i < 144; i++) {
                entries.add(new Entry((float) (i*0.1),Float.parseFloat((String) array_list1.get(i))));
            }
        }
        else if((array_list1.size()>96)&&(array_list1.size()<144)){
            for (int i = 0; i < 96; i++) {
                entries.add(new Entry((float) (i*0.1),Float.parseFloat((String) array_list1.get(i))));
            }
        }

        else if((array_list1.size()>36)&&(array_list1.size()<96)){
            for (int i = 0; i < 36; i++) {
                entries.add(new Entry((float) (i*0.1),Float.parseFloat((String) array_list1.get(i))));
            }
        }

        else if((array_list1.size()>12)&&(array_list1.size()<36)){
            for (int i = 0; i < 12; i++) {
                entries.add(new Entry((float) (i*0.1),Float.parseFloat((String) array_list1.get(i))));
            }
        }
        else {
            for (int i = 0; i < array_list1.size(); i++) {
                entries.add(new Entry((float) (i*0.1),Float.parseFloat((String) array_list1.get(i))));
            }
        }
        entries.add(new Entry((float) 12, 273));
        return entries;
    }
}