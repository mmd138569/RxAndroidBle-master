package com.polidea.rxandroidble2.sample;

import static android.graphics.Color.GRAY;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.opencsv.CSVWriter;
import com.polidea.rxandroidble2.sample.example4_characteristic.CharacteristicOperationExampleActivity;
import com.polidea.rxandroidble2.sample.example4_characteristic.CustomLineChart;
import com.polidea.rxandroidble2.sample.example4_characteristic.DatabaseHelper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.OffsetTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import io.reactivex.annotations.NonNull;

public class landscapechart extends AppCompatActivity {
    private CustomLineChart lineChart;
    float time,time1;
    int entrySize = 288;
    int count=0;
    Spinner spinner;
    ArrayList<String>folderlist=new ArrayList<>();
    int a;
    Button button1,button2,button3,button4,button5,button11,button21,button31,button41,button51;
    ImageView csv;
    String sfolder;
    Button btn;
    private static final int REQUEST_CODE_PICK_FILE = 1;
    private static final int PERMISSION_REQUEST_CODE = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /*Configuration config = getResources().getConfiguration();
        if(config.smallestScreenWidthDp>300){
            setContentView(R.layout.activity_landscapechart);
        }
        else {
            setContentView(R.layout.landscapehart_smallphone);
        }*/
        DisplayMetrics displayMetrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenhight=displayMetrics.heightPixels;
        if(screenhight>=1000){
            setContentView(R.layout.activity_landscapechart);
        }
        else if(screenhight<=1000){
            setContentView(R.layout.landscapehart_smallphone);
        }
        Intent intent = getIntent();
         intent.getStringExtra("mac_add");

        button1=  findViewById(R.id.btton1);
        button2=  findViewById(R.id.btton2);
        button3=  findViewById(R.id.btton3);
        button4=  findViewById(R.id.btton4);
        button5=  findViewById(R.id.btton5);

        button11=  findViewById(R.id.btton11);
        button21=  findViewById(R.id.btton21);
        button31=  findViewById(R.id.btton31);
        button41=  findViewById(R.id.btton41);
        button51=  findViewById(R.id.btton51);
        csv=findViewById(R.id.EXL);
        lineChart = findViewById(R.id.landchart);
        spinner=findViewById(R.id.SP_folder);
        //folderlist.add("android");
        folderlist.add("Download");
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,folderlist));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                sfolder=folderlist.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        csv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Permission already granted, call the exportDataToExcel() function

                // Set the MIME type(s) of the files you want to access
                String[] mimeTypes = {"application/csv"};
                intent.setType("*/*"); // Allow all file types
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);

                startActivityForResult(intent, REQUEST_CODE_PICK_FILE);
                exportDataToExcel(landscapechart.this);
                String spath= Environment.getExternalStorageDirectory()+"/"+sfolder+"/";
                Uri uri=Uri.parse(spath);
                Intent in=new Intent(Intent.ACTION_PICK);
                in.setDataAndType(uri,"*/*");
                startActivity(in);
                System.out.println("==========================================================");
            }
        });
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
                        float rangeHigh = 200f;
                        float rangeLow = -7f;
                        float rangeLow2=203f;
                        float rangeHigh2=450;
                        float rangeLow3=453;
                        float rangeHigh3=600;
                        lineChart.setTouchEnabled(true);
                        lineChart.setScaleEnabled(false);
                        lineChart.addTargetZone(new CustomLineChart.TargetZone( Color.parseColor("#feebe5"),rangeLow,rangeHigh,""));
                        lineChart.addTargetZone(new CustomLineChart.TargetZone( Color.parseColor("#dfdfdf"),rangeLow2,rangeHigh2,""));
                        lineChart.addTargetZone(new CustomLineChart.TargetZone( Color.parseColor("#fef5e6"),rangeLow3,rangeHigh3,""));

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            OffsetTime offset1 = OffsetTime.now();
                             time = offset1.getHour();
                             time1 = offset1.getMinute();
                             time=time+time1/100;

                        }
                        else {
                            Calendar calendar = Calendar.getInstance();
                            time = calendar.get(Calendar.HOUR);
                             time1=calendar.get(Calendar.MINUTE);
                             a = calendar.get(Calendar.AM_PM);
                             time=time+time1/100;
                             if(a==calendar.AM){

                             }
                             else if(a==calendar.PM) {
                                 time=time+12;
                             }
                        }
                        initLineChart(time);
                        button1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                button1.setVisibility(View.VISIBLE);
                                button2.setVisibility(View.VISIBLE);
                                button3.setVisibility(View.VISIBLE);
                                button4.setVisibility(View.VISIBLE);
                                button5.setVisibility(View.VISIBLE);

                                button11.setVisibility(View.INVISIBLE);
                                button21.setVisibility(View.INVISIBLE);
                                button31.setVisibility(View.INVISIBLE);
                                button41.setVisibility(View.INVISIBLE);
                                button51.setVisibility(View.INVISIBLE);

                                initLineChrt(24,time);
                            }
                        });
                        button2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                button1.setVisibility(View.INVISIBLE);
                                button2.setVisibility(View.INVISIBLE);
                                button3.setVisibility(View.VISIBLE);
                                button4.setVisibility(View.VISIBLE);
                                button5.setVisibility(View.VISIBLE);

                                button21.setVisibility(View.VISIBLE);
                                button11.setVisibility(View.VISIBLE);

                                button31.setVisibility(View.INVISIBLE);
                                button41.setVisibility(View.INVISIBLE);
                                button51.setVisibility(View.INVISIBLE);

                                initLineChrt(12,time);
                            }
                        });
                        button3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                button1.setVisibility(View.INVISIBLE);
                                button2.setVisibility(View.VISIBLE);
                                button3.setVisibility(View.INVISIBLE);
                                button4.setVisibility(View.VISIBLE);
                                button5.setVisibility(View.VISIBLE);

                                button21.setVisibility(View.INVISIBLE);
                                button11.setVisibility(View.VISIBLE);

                                button31.setVisibility(View.VISIBLE);
                                button41.setVisibility(View.INVISIBLE);
                                button51.setVisibility(View.INVISIBLE);
                                initLineChrt(8,time);
                            }
                        });
                        button4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                button1.setVisibility(View.INVISIBLE);
                                button2.setVisibility(View.VISIBLE);
                                button3.setVisibility(View.VISIBLE);
                                button4.setVisibility(View.INVISIBLE);
                                button5.setVisibility(View.VISIBLE);

                                button21.setVisibility(View.INVISIBLE);
                                button11.setVisibility(View.VISIBLE);

                                button31.setVisibility(View.INVISIBLE);
                                button41.setVisibility(View.VISIBLE);
                                button51.setVisibility(View.INVISIBLE);
                                initLineChrt(3,time);
                            }
                        });
                        button5.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                button1.setVisibility(View.INVISIBLE);
                                button2.setVisibility(View.VISIBLE);
                                button3.setVisibility(View.VISIBLE);
                                button4.setVisibility(View.VISIBLE);
                                button5.setVisibility(View.INVISIBLE);

                                button21.setVisibility(View.INVISIBLE);
                                button11.setVisibility(View.VISIBLE);

                                button31.setVisibility(View.INVISIBLE);
                                button41.setVisibility(View.INVISIBLE);
                                button51.setVisibility(View.VISIBLE);
                                initLineChrt(1,time);
                            }
                        });
                    }
                });
    }
  /*  @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, call the exportDataToExcel() function
                exportDataToExcel(this);
            } else {
                // Permission denied, handle accordingly (e.g., show a message)
                Toast.makeText(this, "Write external storage permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }*/
    public void exportDataToExcel(Context context) {
        // Obtain a reference to the SQLite database

        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(
                context.getDatabasePath("database.db"), null);

        // Query the data from the database
        Cursor cursor = database.rawQuery("SELECT * FROM SalaryDetails", null);

        // Create CSV file
        String csvFileName = "exported_data.csv";
        //String userPath="android/data";
        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File csvFile = new File(folder, csvFileName);
         //File  = new File(context.getExternalStoragePublicDirectory, csvFileName);
        Log.d("FilePath", "=============================================CSV file saved at: " + csvFile.getAbsolutePath());
        try {
        // Initialize CSVWriter
            CSVWriter writer = new CSVWriter(new FileWriter(csvFile));

            // Write column names
            String[] columnNames = cursor.getColumnNames();
            writer.writeNext(columnNames);

            // Write data rows
            while (cursor.moveToNext()) {
                String[] rowData = new String[columnNames.length];
                for (int i = 0; i < columnNames.length; i++) {
                    rowData[i] = cursor.getString(i);
                }
                writer.writeNext(rowData);
            }

            // Close CSVWriter
            writer.close();

        // Convert CSV to Excel format using LightXLSReader library
           /* String excelFileName = "exported_data.xls";
            File excelFile = new File(context.getExternalFilesDir(null), excelFileName);
            LightXLSReader.convertCsvToXls(csvFile.getAbsolutePath(), excelFile.getAbsolutePath());*/
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Close cursor and database
        cursor.close();
        database.close();
    }
    private void initLineChart(float time){
        //    lineChart.setTouchEnabled(true);
        lineChart.getXAxis().setAxisMaximum(24f);
        lineChart.getAxisLeft().setAxisMaximum(600f);
        lineChart.getAxisRight().setAxisMaximum(600f);
        lineChart.getXAxis().setDrawGridLines(false);//disable vertical line
        lineChart.getAxisLeft().setDrawGridLines(false);//disiable horizental
        lineChart.getAxisRight().setDrawGridLines(false);//disable horizantal'
        YAxis RightAxis = lineChart.getAxisRight();
        RightAxis.setTextSize(0f);//put it bottom
        RightAxis.setTextColor(Color.TRANSPARENT);
        RightAxis.setDrawAxisLine(false);
        RightAxis.setDrawGridLines(false);
        lineChart.setDrawGridBackground(false);
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
//================================================================================


        //lineChart.getXAxis().setLabelCount(3,true);

//================================================================================
        count=1;
        lineChart.animateX(4000);
        entrySize = 288;
        LineDataSet line3 = new LineDataSet(getRandomEntries(entrySize,time,count), "");
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
            //lineChart.getXAxis().setAxisMaximum(24f);
            lineChart.getXAxis().setAxisMaximum(time);
            lineChart.getXAxis().setAxisMinimum(time-24f);
            entrySize = 288;
        count=1;}
        else if(num==12){
            //lineChart.getXAxis().setAxisMaximum(12f);
              //lineChart.getXAxis().setAxisMinimum(time);
            lineChart.getXAxis().setAxisMaximum(time);
            lineChart.getXAxis().setAxisMinimum(time-12f);
            entrySize = 144;
        count=2;}
        else if(num==8){
            //lineChart.getXAxis().setAxisMaximum(8f);
            lineChart.getXAxis().setAxisMaximum(time);
            lineChart.getXAxis().setAxisMinimum(time-8f);
            entrySize = 96;
        count=3;}
        else if(num==3){
            lineChart.getXAxis().setAxisMaximum(time);
            lineChart.getXAxis().setAxisMinimum(time-3f);
            entrySize = 36;
        count=4;}
        else if(num==1){
            lineChart.getXAxis().setAxisMaximum(time);
            lineChart.getXAxis().setAxisMinimum(time-1f);
            entrySize=12;
        count=5;}
        //lineChart.setTouchEnabled(true);
        lineChart.getAxisLeft().setAxisMaximum(600f);
        lineChart.getAxisRight().setAxisMaximum(600f);
        lineChart.getXAxis().setDrawGridLines(false);//disable vertical line
        lineChart.getAxisLeft().setDrawGridLines(false);//disiable horizental
        lineChart.getAxisRight().setDrawGridLines(false);//disable horizantal'
        CustomMarkerview mv = new CustomMarkerview (this, R.layout.custommarkerview);
        lineChart.setMarkerView(mv);
        YAxis RightAxis = lineChart.getAxisRight();
        RightAxis.setTextSize(0f);//put it bottom
       // lineChart.setDrawGridBackground(false);

        RightAxis.setTextColor(Color.TRANSPARENT);
        RightAxis.setDrawAxisLine(false);
        RightAxis.setDrawGridLines(false);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.animateX(4000);
        LineDataSet line3 = new LineDataSet(getRandomEntries(entrySize,time,count), "");
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
    private List<Entry> getRandomEntries(int entrySize, float time, int count) {
        final DatabaseHelper helper1 = new DatabaseHelper(landscapechart.this);
        final ArrayList array_list1 = helper1.getAllCotacts1();
        List<Entry> entries = new ArrayList<>();
        Random random = new Random();
        array_list1.clear();
 //================================================================







        array_list1.addAll(helper1.getAllCotacts1());








        //=======================================================
        ArrayList<Float> arr = new ArrayList<Float>();
        float x=0;
       /* if(array_list1.size()>entrySize){
            for (int i = 0; i < entrySize; i++) {
                entries.add(new Entry((float) (time-24+i*0.083),Float.parseFloat((String) array_list1.get(i))));
            }
        }
        else if ((array_list1.size()>144)&&(array_list1.size()<=288)) {
            for (int i = 0; i < 144; i++) {
                entries.add(new Entry((float) (i*0.083),Float.parseFloat((String) array_list1.get(i))));
            }
        }
        else if((array_list1.size()>96)&&(array_list1.size()<=144)){
            for (int i = 0; i < 96; i++) {
                entries.add(new Entry((float) (i*0.083),Float.parseFloat((String) array_list1.get(i))));
            }
        }

        else if((array_list1.size()>36)&&(array_list1.size()<=96)){
            for (int i = 0; i < 36; i++) {
                entries.add(new Entry((float) (time-8+i*0.083),Float.parseFloat((String) array_list1.get(i))));
            }
        }

        else if((array_list1.size()>12)&&(array_list1.size()<=36)){
            for (int i = 0; i < 12; i++) {
                entries.add(new Entry((float) (time-3+i*0.083),Float.parseFloat((String) array_list1.get(i))));
            }
        }
        else {*/
        switch (count) {
            case 1:
                if(array_list1.size()>=288) {
                    int j=0;
                    for (int i = array_list1.size() - 288; i < array_list1.size(); i++) {
                       /* System.out.println("=======================================");
                        System.out.println(Float.parseFloat((String) array_list1.get(i)));
                        System.out.println(i);*/
                        x = Float.parseFloat((String) array_list1.get(i));
                        arr.add(j,x);
                        j++;
                    }
                    for (int i = 0; i < arr.size(); i++) {
                        entries.add(new Entry((float) (time - 24 + i * 0.083), Float.parseFloat((String) array_list1.get(i))));
                    }
                }
                break;
            case 2:
                if(array_list1.size()>=144) {
                    int j=0;
                    for (int i = array_list1.size() - 144; i < array_list1.size(); i++) {
                       /* System.out.println("=======================================");
                        System.out.println(Float.parseFloat((String) array_list1.get(i)));
                        System.out.println(i);*/
                        x = Float.parseFloat((String) array_list1.get(i));
                        arr.add(j,x);
                        j++;
                    }
                    for (int i = 0; i < arr.size(); i++) {

                            entries.add(new Entry((float) (time - 12 + i * 0.083), arr.get(i)));
                    }
                }
                break;
            case 3:
                if(array_list1.size()>=96) {
                    int j=0;
                    for (int i = array_list1.size() - 96; i < array_list1.size(); i++) {
                        System.out.println("=======================================");
                        System.out.println(Float.parseFloat((String) array_list1.get(i)));
                        System.out.println(i);
                        x = Float.parseFloat((String) array_list1.get(i));
                        arr.add(j,x);
                        j++;
                    }
                    for (int i = 0; i < arr.size(); i++) {
                        entries.add(new Entry((float) (time - 8 + i * 0.083), arr.get(i)));
                    }
                }
                break;
            case 4:
                if(array_list1.size()>=36) {
                    int j=0;
                    for (int i = array_list1.size() - 36; i < array_list1.size(); i++) {
                        System.out.println("=======================================");
                        System.out.println(Float.parseFloat((String) array_list1.get(i)));
                        System.out.println(i);
                        x = Float.parseFloat((String) array_list1.get(i));
                        arr.add(j,x);
                        j++;
                    }
                    for (int i = 0; i < arr.size(); i++) {
                        entries.add(new Entry((float) (time - 3 + i * 0.083), arr.get(i)));
                    }
                }
                break;
            case 5:
                if(array_list1.size()>=12) {
                    int j = 0;
                    for (int i = array_list1.size() - 12; i < array_list1.size(); i++) {
                        System.out.println("=======================================");
                        System.out.println(Float.parseFloat((String) array_list1.get(i)));
                        System.out.println(i);
                        x = Float.parseFloat((String) array_list1.get(i));
                        arr.add(j,x);
                        j++;
                    }
                    for (int i = 0; i < arr.size(); i++) {
                        entries.add(new Entry((float) (time - 1 + i * 0.083), arr.get(i)));
                    }
                }
                break;

               /* if(array_list1.size()<=8) {
                    for (int i = 0; i < array_list1.size(); i++) {
                        entries.add(new Entry((float) (time - 1 + i * 0.083), Float.parseFloat((String) array_list1.get(i))));
                    }
                }
                else if(array_list1.size()>=8){
                    for(int i=array_list1.size()-9;i<array_list1.size();i++){
                        entries.add(new Entry((float) (time - 1 + i * 0.083), Float.parseFloat((String) array_list1.get(i))));
                    }
                }*/
        }
       // }
        entries.add(new Entry((float) 12, 0));
        return entries;
    }
  /*  @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, CharacteristicOperationExampleActivity.class);
        startActivity(intent);
    }*/
}