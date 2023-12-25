package com.polidea.rxandroidble2.sample.example4_characteristic;

import static android.graphics.Color.GRAY;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;
import static android.graphics.Color.TRANSPARENT;

import static java.util.concurrent.TimeUnit.SECONDS;

import android.app.ActivityManager;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.snackbar.Snackbar;

import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rx.ReplayingShare;
import com.polidea.rxandroidble2.RxBleConnection;
import com.polidea.rxandroidble2.RxBleDevice;
import com.polidea.rxandroidble2.sample.DeviceActivity;
import com.polidea.rxandroidble2.sample.R;
import com.polidea.rxandroidble2.sample.SampleApplication;
import com.polidea.rxandroidble2.sample.landscapechart;
import com.polidea.rxandroidble2.sample.myservice;
import com.polidea.rxandroidble2.sample.util.HexString;
import com.polidea.rxandroidble2.scan.ScanResult;

import java.sql.Timestamp;
import java.time.OffsetTime;
import java.util.ArrayList;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

public class CharacteristicOperationExampleActivity extends AppCompatActivity {

    ImageView top,butt,twotop,twobutt,left,x2,x1,signal_strength1,signal_strength2,signal_strength3;

    private Disposable connectionDisposable1;

    public static final String EXTRA_CHARACTERISTIC_UUID = "extra_uuid";
    @BindView(R.id.connect)
    TextView connectButton;
    @BindView(R.id.read_output)
    TextView readOutputView;
    float yval[] = new float[1000];
/*    @BindView(R.id.read_hex_output)
    TextView readHexOutputView;
 @BindView(R.id.write_input)
    TextView writeInput;*/
    int temp =0;
    @BindView(R.id.read)
    TextView readButton;
    public static int z=0;
    @BindView(R.id.rssi)
    TextView rssiView;
  /*  @BindView(R.id.write)
    Button writeButton;*/
    @BindView(R.id.notify)
    Button notifyButton;
    private UUID characteristicUuid;
    String  str="0";
    int i=2,x=13,j=0;
    public static PieChart pieChart;

    boolean a=false;
    private PublishSubject<Boolean> disconnectTriggerSubject = PublishSubject.create();
    private Observable<RxBleConnection> connectionObservable;
    private RxBleDevice bleDevice;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    //TextView save, refresh;
    //TextView name;
    private ListView listView;
    public static CustomLineChart lineChart;



    public static Intent startActivityIntent(Context context, String peripheralMacAddress, UUID characteristicUuid) {
        Intent intent = new Intent(context, CharacteristicOperationExampleActivity.class);
        intent.putExtra(DeviceActivity.EXTRA_MAC_ADDRESS, peripheralMacAddress);
        intent.putExtra(EXTRA_CHARACTERISTIC_UUID, characteristicUuid);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
*/
       /* Configuration config = getResources().getConfiguration();
        if(config.smallestScreenWidthDp>300){
            setContentView(R.layout.activity_example4);
        }
        else {
            setContentView(R.layout.example4_smallphone);
        }*/
        DisplayMetrics displayMetrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenhight=displayMetrics.heightPixels;
        if(screenhight>=1000){
            setContentView(R.layout.activity_example4);
        }
        else if(screenhight<=1000){
            setContentView(R.layout.example4_smallphone);
        }

        pieChart = findViewById(R.id.pichart);
      /*  if(Float.parseFloat(readOutputView.getText().toString())!=0){
            lineChart = findViewById(R.id.chart);
            lineChart.setVisibility(View.VISIBLE);
        }
        else if(Float.parseFloat(readOutputView.getText().toString())==0){
            lineChart = findViewById(R.id.chart);
            lineChart.setVisibility(View.INVISIBLE);
        }*/
        lineChart = findViewById(R.id.chart);

        ButterKnife.bind(this);
        String macAddress = getIntent().getStringExtra(DeviceActivity.EXTRA_MAC_ADDRESS);
        characteristicUuid = (UUID) getIntent().getSerializableExtra(EXTRA_CHARACTERISTIC_UUID);
        bleDevice = SampleApplication.getRxBleClient(this).getBleDevice(macAddress);
        connectionObservable = prepareConnectionObservable();
        if(a==false) {
            lineChart.invalidate();
            //XAxis xAxis=lineChart.getXAxis();
            LineDataSet lineDataSet = new LineDataSet(linechart1(), "data set");
            ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
            iLineDataSets.add(lineDataSet);
            LineData lineData = new LineData(iLineDataSets);
            lineChart.setData(lineData);
            lineChart.invalidate();
            //lineChart.setBackgroundColor(Color.G);
            float rangeHigh = 10.5f;
            float rangeLow = -1f;
            float rangeLow2 = 11f;
            float rangeHigh2 = 38f;
            float rangeLow3 = 38.5f;
            float rangeHigh3 = 61f;
            lineChart.addTargetZone(new CustomLineChart.TargetZone(Color.parseColor("#feebe5"), rangeLow, rangeHigh, ""));
            lineChart.addTargetZone(new CustomLineChart.TargetZone(Color.parseColor("#dfdfdf"), rangeLow2, rangeHigh2, ""));
            lineChart.addTargetZone(new CustomLineChart.TargetZone(Color.parseColor("#fef5e6"), rangeLow3, rangeHigh3, ""));
            lineChart.getLegend().setEnabled(false);
            lineChart.setNoDataText("No Data Insert");
            lineDataSet.setColor(GRAY);
            lineDataSet.setCircleColors(Color.BLACK);
            lineDataSet.setDrawCircles(true);
            lineDataSet.setDrawCircleHole(true);
            lineDataSet.setLineWidth((float) 0.3);
            lineDataSet.setCircleRadius(2);
            lineDataSet.setCircleHoleRadius(10);
            lineDataSet.setValueTextColor(Color.GRAY);
            lineDataSet.setDrawValues(false);
            lineChart.getDescription().setEnabled(false);
            //lineChart.setDrawGridBackground(true);
            //lineChart.setDrawBorders(true);
            //xAxis.isEnabled();
            YAxis left = lineChart.getAxisLeft();
            left.setDrawGridLines(false);
            left.setDrawAxisLine(false); // no axis line
            left.setDrawGridLines(false); // no grid lines
            left.setDrawZeroLine(true);
            YAxis leftAxis = lineChart.getAxisLeft();
            lineChart.getXAxis().setDrawGridLines(false);//disable vertical line
            lineChart.getAxisLeft().setDrawGridLines(false);//disiable horizental
            lineChart.getAxisRight().setDrawGridLines(false);//disable horizantal
            leftAxis.setTextSize(0f);//put it bottom
            leftAxis.setTextColor(Color.TRANSPARENT);
            leftAxis.setDrawAxisLine(false);
            leftAxis.setDrawGridLines(false);
            lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
            a=true;
        }
        //noinspection ConstantConditions
        //getSupportActionBar().setSubtitle(getString(R.string.mac_address, macAddress));
        Handler handler=new Handler();
        Handler hand=new Handler();
        Handler nand =new Handler();
        signal_strength1= findViewById(R.id.signal_strength1);
        signal_strength2= findViewById(R.id.signal_strength2);
        signal_strength3= findViewById(R.id.signal_strength3);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                //connect
                onConnectToggleClick();
                String a=String.valueOf(rssiView.getText()).replace("RSSI: ","");
                System.out.println("================= if its run it should run the RSSI ==================");
                if(a!="") {
                    if (Integer.parseInt(a) <= -70) {
                        temp=0;
                        // Toast.makeText(this,  rssiView.getText(), Toast.LENGTH_SHORT).show();
                        setrssi(temp);

                        signal_strength3.setVisibility(View.INVISIBLE);
                        signal_strength2.setVisibility(View.INVISIBLE);
                        signal_strength1.setVisibility(View.VISIBLE);


                        /*HERE WE NEED TO USE ALERT*/
                    } else if (Integer.parseInt(a) <= -40 && Integer.parseInt(a) >= -70) {
                        //  Toast.makeText(this, String.valueOf(rssiView.getText()), Toast.LENGTH_SHORT).show();
                        temp=1;
                        setrssi(temp);
                        signal_strength3.setVisibility(View.INVISIBLE);
                        signal_strength2.setVisibility(View.VISIBLE);
                        signal_strength1.setVisibility(View.INVISIBLE);

                    } else if (Integer.parseInt(a) <= 0 && Integer.parseInt(a) >= -40) {
                        // Toast.makeText(this, String.valueOf(rssiView.getText()), Toast.LENGTH_SHORT).show();
                        temp=2;
                        setrssi(temp);
                        signal_strength3.setVisibility(View.VISIBLE);
                        signal_strength2.setVisibility(View.INVISIBLE);
                        signal_strength1.setVisibility(View.INVISIBLE);

                    }
                }
                //refreshing();
                //test it before add the onReadClick();
                //  Toast.makeText(getApplicationContext(),"This is a Service running in Background", Toast.LENGTH_SHORT).show();

                handler.postDelayed(this, 10700);
                Runnable r=new Runnable() {
                    @Override
                    public void run() {
                            onNotifyClick();
                            // onReadClick();
                        //}
                    }
                };           nand.postDelayed(r, 3500);

            }
        },10700);
        hand.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshing();

                hand.postDelayed(this, 17000);

            }
        },17000);

       // thread();
        lineChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in= new Intent(CharacteristicOperationExampleActivity.this, landscapechart.class);
                startActivity(in);
                finish();
            }
        });
//=============== this thread is life savier ===========
        Handler hand1=new Handler();
        Runnable run=new Runnable() {
            @Override
            public void run() {
                if (!foregroundServiceRunning()) {
                    Intent in= new Intent(CharacteristicOperationExampleActivity.this, myservice.class);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        startForegroundService(in);
                    }
                }

            }
        };
        hand1.postDelayed(run,18000);
//=================================================

//==================================================
    }
    /*public static void thread(){
        Handler handler=new Handler();
        Handler hand=new Handler();
        Handler nand =new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                //connect
                onConnectToggleClick();

                //refreshing();
                //test it before add the onReadClick();
                //  Toast.makeText(getApplicationContext(),"This is a Service running in Background", Toast.LENGTH_SHORT).show();

                handler.postDelayed(this, 10700);
                Runnable r=new Runnable() {
                    @Override
                    public void run() {
                        onReadClick();
                    }
                };            nand.postDelayed(r, 3500);

            }
        },10700);
        hand.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshing();

                hand.postDelayed(this, 17000);

            }
        },17000);
    }*/
    public static int getrssi() { return z; }

    public void setrssi(int x) { this.z = x; }
    private Observable<RxBleConnection> prepareConnectionObservable() {
        return bleDevice
                .establishConnection(false)
                .takeUntil(disconnectTriggerSubject)
                .compose(ReplayingShare.instance());
    }
//=============================== connect buttom ==========================

   /* Handler h = new Handler();
    Runnable r = new Runnable() {
        @Override
        public void run() {
        }
    };
            h.postDelayed(r, 1500);*/
   public static void customchart() {
       //  lineChart.init();



       lineChart.invalidate();
       //XAxis xAxis=lineChart.getXAxis();
       LineDataSet lineDataSet = new LineDataSet(linechart1(), "data set");
       ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
       iLineDataSets.add(lineDataSet);
       LineData lineData = new LineData(iLineDataSets);
       lineChart.setData(lineData);
       lineChart.invalidate();
       //lineChart.setBackgroundColor(Color.G);
       float rangeHigh = 10.5f;
       float rangeLow = -1f;
       float rangeLow2 = 11f;
       float rangeHigh2 = 38f;
       float rangeLow3 = 38.5f;
       float rangeHigh3 = 61f;
       lineChart.addTargetZone(new CustomLineChart.TargetZone(Color.parseColor("#feebe5"), rangeLow, rangeHigh, ""));
       lineChart.addTargetZone(new CustomLineChart.TargetZone(Color.parseColor("#dfdfdf"), rangeLow2, rangeHigh2, ""));
       lineChart.addTargetZone(new CustomLineChart.TargetZone(Color.parseColor("#fef5e6"), rangeLow3, rangeHigh3, ""));
       lineChart.getLegend().setEnabled(false);


       lineChart.setNoDataText("No Data Insert");
       lineDataSet.setColor(GRAY);
       lineDataSet.setCircleColors(Color.BLACK);
       lineDataSet.setDrawCircles(true);
       lineDataSet.setDrawCircleHole(true);
       lineDataSet.setLineWidth((float) 0.3);
       lineDataSet.setCircleRadius(2);
       lineDataSet.setCircleHoleRadius(10);
       lineDataSet.setValueTextColor(Color.GRAY);
       lineDataSet.setDrawValues(false);
       lineChart.getDescription().setEnabled(false);
       //lineChart.setDrawGridBackground(true);
       //lineChart.setDrawBorders(true);
       //xAxis.isEnabled();

       YAxis left = lineChart.getAxisLeft();
       left.setDrawGridLines(false);
       left.setDrawAxisLine(false); // no axis line
       left.setDrawGridLines(false); // no grid lines
       left.setDrawZeroLine(true);

       YAxis leftAxis = lineChart.getAxisLeft();

       lineChart.getXAxis().setDrawGridLines(false);//disable vertical line
       lineChart.getAxisLeft().setDrawGridLines(false);//disiable horizental
       lineChart.getAxisRight().setDrawGridLines(false);//disable horizantal

       leftAxis.setTextSize(0f);//put it bottom
       leftAxis.setTextColor(Color.TRANSPARENT);
       leftAxis.setDrawAxisLine(false);
       leftAxis.setDrawGridLines(false);
       lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

       lineChart.animateX(4000);

   }
    @OnClick(R.id.connect)
    public void onConnectToggleClick() {

        if (isConnected()) {

            triggerDisconnect();
        } else {
            final Disposable connectionDisposable = connectionObservable
                    .flatMapSingle(RxBleConnection::discoverServices)
                    .flatMapSingle(rxBleDeviceServices -> rxBleDeviceServices.getCharacteristic(characteristicUuid))
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(disposable -> connectButton.setText(R.string.connecting))
                    .subscribe(
                            characteristic -> {
                                updateUI(characteristic);
                                Log.i(getClass().getSimpleName(), "Hey, connection has been established!");
                            },
                            this::onConnectionFailure,
                            this::onConnectionFinished
                    );

 }
        connectionDisposable1 = bleDevice.establishConnection(false)
                .doFinally(this::clearSubscription)
                .flatMap(rxBleConnection -> // Set desired interval.
                        Observable.interval(10, SECONDS).flatMapSingle(sequence -> rxBleConnection.readRssi()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateRssi, this::onConnectionFailure);

    }
//==========================================================================
//literly i think the read method called after 4 or 5 second so we need theard for 4 or 5 second tho
private void updateRssi(int rssiValue) {
    rssiView.setText(getString(R.string.read_rssi, rssiValue));

    /*else if(Integer.parseInt(String.valueOf(rssiView.getText()))<=0&&Integer.parseInt(String.valueOf(rssiView.getText()))>=-40) {
        Toast.makeText(this, String.valueOf(rssiView.getText()), Toast.LENGTH_SHORT).show();
        findViewById(R.id.signal_strength4);
    }*/
}
private void clearSubscription() {
    connectionDisposable1 = null;
    //updateUI();
}
    private void updateUI() {
        final boolean connected = isConnected();
        connectButton.setText(connected ? R.string.disconnect : R.string.connect);
    }
    @OnClick(R.id.read)
    public void onReadClick() {

        if (isConnected()) {
            final Disposable disposable = connectionObservable
                    .firstOrError()
                    .flatMap(rxBleConnection -> rxBleConnection.readCharacteristic(characteristicUuid))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(bytes -> {
                        readOutputView.setText(new String(bytes));
                      //  readHexOutputView.setText(HexString.bytesToHex(bytes));
                       // writeInput.setText(HexString.bytesToHex(bytes));
//=======================================

                    /*  try {
                            FileOutputStream fos = openFileOutput("myfile.txt", Context.MODE_PRIVATE);
                            fos.write(HexString.bytesToHex(bytes).getBytes());
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }*/
                    }, this::onReadFailure);

            compositeDisposable.add(disposable);

//=========================================
        }
    }
    @OnClick(R.id.notify)
    public void onNotifyClick() {

        if (isConnected()) {
            connectionObservable
                    .flatMap(rxBleConnection -> rxBleConnection.setupNotification(characteristicUuid))
                    .doOnNext(notificationObservable -> runOnUiThread(this::notificationHasBeenSetUp))
                    .flatMap(notificationObservable -> notificationObservable)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(bytes -> {
                        readOutputView.setText(new String(bytes));
                        //  readHexOutputView.setText(HexString.bytesToHex(bytes));
                        // writeInput.setText(HexString.bytesToHex(bytes));
//=======================================

                    /*  try {
                            FileOutputStream fos = openFileOutput("myfile.txt", Context.MODE_PRIVATE);
                            fos.write(HexString.bytesToHex(bytes).getBytes());
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }*/
                    }, this::onReadFailure);
        }
    }
    @OnClick(R.id.refresh)
    public void refreshing() {

        twotop=findViewById(R.id.twotop);
        left=findViewById(R.id.leFt);
        butt=findViewById(R.id.butt);
        twobutt=findViewById(R.id.twobutt);
        top=findViewById(R.id.top);
        x1=findViewById(R.id.topmid);
        x2=findViewById(R.id.buttmid);

        final DatabaseHelper helper = new DatabaseHelper(CharacteristicOperationExampleActivity.this);
        final ArrayList array_list = helper.getAllCotacts();
        //name = findViewById(R.id.name);
        readOutputView = findViewById(R.id.read_output);
        listView = findViewById(R.id.listView);
        final ArrayAdapter arrayAdapter = new ArrayAdapter(CharacteristicOperationExampleActivity.this,
                android.R.layout.simple_list_item_1, array_list);
        listView.setAdapter(arrayAdapter);
        //System.out.println(readOutputView+"=======================");
        if (!readOutputView.getText().toString().isEmpty()) {
//===========================================================================================================
            yval[i - 1] = Float.parseFloat(String.valueOf(readOutputView.getText()));
            System.out.println("===================="+yval[i-1]);
            Intent intent  = new Intent(this, myservice.class);

            int songUrl=Integer.parseInt(String.valueOf(readOutputView.getText()));
            intent.putExtra("YOUR_KEY_SONG_NAME", songUrl);
            startService(intent);
//===========================================================================================================
              str = readOutputView.getText().toString();

            if (helper.insert(/*name.getText()*/ yval[i - 1])) {

                Toast.makeText(CharacteristicOperationExampleActivity.this, "Inserted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(CharacteristicOperationExampleActivity.this, "NOT Inserted", Toast.LENGTH_LONG).show();
            }
        } else {
            // name.setError("Enter NAME");
            readOutputView.setError("Enter Salary");
        }


        float x=yval[i-1]-yval[i-2];
        float y=yval[i-2]-yval[i-1];
        Toast.makeText(this, String.valueOf(x), Toast.LENGTH_SHORT).show();
        left.setVisibility(View.INVISIBLE);
        twobutt.setVisibility(View.INVISIBLE);
        twotop.setVisibility(View.INVISIBLE);
        top.setVisibility(View.INVISIBLE);
        butt.setVisibility(View.INVISIBLE);
        x1.setVisibility(View.INVISIBLE);
        x2.setVisibility(View.INVISIBLE);
        if(((x<50)&&(x>=0))||(y<50)&&(y>=0)){
            left.setVisibility(View.VISIBLE);
            twobutt.setVisibility(View.INVISIBLE);
            twotop.setVisibility(View.INVISIBLE);
            top.setVisibility(View.INVISIBLE);
            butt.setVisibility(View.INVISIBLE);
            x1.setVisibility(View.INVISIBLE);
            x2.setVisibility(View.INVISIBLE);

        }
        else if ((x<100)&&(x>=50)) {
            left.setVisibility(View.INVISIBLE);
            twobutt.setVisibility(View.INVISIBLE);
            twotop.setVisibility(View.INVISIBLE);
            top.setVisibility(View.INVISIBLE);
            butt.setVisibility(View.INVISIBLE);
            x1.setVisibility(View.VISIBLE);
            x2.setVisibility(View.INVISIBLE);

        }
        else if ((y<100)&&(y>=50)) {
            left.setVisibility(View.INVISIBLE);
            twobutt.setVisibility(View.INVISIBLE);
            twotop.setVisibility(View.INVISIBLE);
            top.setVisibility(View.INVISIBLE);
            butt.setVisibility(View.INVISIBLE);
            x1.setVisibility(View.INVISIBLE);
            x2.setVisibility(View.VISIBLE);
        }
        else if((x<150)&&(x>=100)){
            left.setVisibility(View.INVISIBLE);
            twobutt.setVisibility(View.INVISIBLE);
            twotop.setVisibility(View.INVISIBLE);
            top.setVisibility(View.VISIBLE);
            butt.setVisibility(View.INVISIBLE);
            x1.setVisibility(View.INVISIBLE);
            x2.setVisibility(View.INVISIBLE);
        }
        else if((y<150)&&(y>=100)){
            left.setVisibility(View.INVISIBLE);
            twobutt.setVisibility(View.INVISIBLE);
            twotop.setVisibility(View.INVISIBLE);
            top.setVisibility(View.INVISIBLE);
            butt.setVisibility(View.VISIBLE);
            x1.setVisibility(View.INVISIBLE);
            x2.setVisibility(View.INVISIBLE);
        }
        else if(y>=150){
            left.setVisibility(View.INVISIBLE);
            twobutt.setVisibility(View.VISIBLE);
            twotop.setVisibility(View.INVISIBLE);
            top.setVisibility(View.INVISIBLE);
            butt.setVisibility(View.INVISIBLE);
            x1.setVisibility(View.INVISIBLE);
            x2.setVisibility(View.INVISIBLE);
        }
        else if(x>=150){
            left.setVisibility(View.INVISIBLE);
            twobutt.setVisibility(View.INVISIBLE);
            twotop.setVisibility(View.VISIBLE);
            top.setVisibility(View.INVISIBLE);
            butt.setVisibility(View.INVISIBLE);
            x1.setVisibility(View.INVISIBLE);
            x2.setVisibility(View.INVISIBLE);
        }
//================================================
//========================= refresh ===============
        float rangeHigh = 100f;
        float rangeLow = -7f;
        float rangeLow2 = 103f;
        float rangeHigh2 = 350f;
        float rangeLow3 = 353f;
        float rangeHigh3 = 400f;
        lineChart.setTouchEnabled(true);
        lineChart.setScaleEnabled(false);
        lineChart.addTargetZone(new CustomLineChart.TargetZone(Color.parseColor("#feebe5"), rangeLow, rangeHigh, ""));
        lineChart.addTargetZone(new CustomLineChart.TargetZone(Color.parseColor("#dfdfdf"), rangeLow2, rangeHigh2, ""));
        lineChart.addTargetZone(new CustomLineChart.TargetZone(Color.parseColor("#fef5e6"), rangeLow3, rangeHigh3, ""));
        array_list.clear();
        array_list.addAll(helper.getAllCotacts());
        arrayAdapter.notifyDataSetChanged();
        listView.invalidateViews();
        listView.refreshDrawableState();
        LineDataSet lineDataSet = new LineDataSet(linechart(yval, i), "lable");
        i++;
        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
        iLineDataSets.add(lineDataSet);
        LineData lineData = new LineData(iLineDataSets);
        lineChart.setData(lineData);
        lineChart.invalidate();
//need to fix the scale and the color of the chart
        lineChart.setScaleEnabled(false);
        //lineChart.getXAxis().setAxisMaximum(24f);
        //lineChart.getXAxis().setAxisMinimum(0f);
        lineChart.getAxisLeft().setAxisMaximum(400f);
        lineChart.getAxisRight().setAxisMaximum(400f);
        //==================
        lineChart.getXAxis().setDrawGridLines(false);//disable vertical line
        lineChart.getAxisLeft().setDrawGridLines(false);//disiable horizental
        lineChart.getAxisRight().setDrawGridLines(false);//disable horizantal'
        YAxis RightAxis = lineChart.getAxisRight();
        //here we add the right axis with number

        RightAxis.setTextSize(0f);//put it bottom
        RightAxis.setTextColor(Color.BLACK);
        RightAxis.setDrawAxisLine(true);
        RightAxis.setDrawGridLines(false);

//==============================================================================
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        if(yval[i-1]%12==0){
            lineDataSet.setDrawCircleHole(false);
        }
        else{
            lineDataSet.setDrawCircles(true);
        }
//==============================================================================
        lineDataSet.setColor(TRANSPARENT);
        lineDataSet.setCircleColor(Color.BLACK);
        lineChart.getLegend().setEnabled(false);
        //---------
        //  lineChart.highlightValue(30,20);
       // lineChart.invalidate();
        lineDataSet.setLineWidth((float) 0.3);
        lineDataSet.setCircleRadius(2);
        lineDataSet.setCircleHoleRadius(10);
        lineDataSet.setValueTextColor(Color.GRAY);
        lineChart.setData(lineData);
        lineChart.getAxisLeft().setEnabled(true);
        lineChart.getAxisRight().setEnabled(true);
        lineChart.invalidate();
        lineData.setDrawValues(false);
        lineChart.getDescription().setEnabled(false);
//================================================================
      /*  OffsetTime offset = OffsetTime.now();
        q1=offset.getHour();
        q=offset.getMinute();
        q1=q1*(q/100);*/
        lineChart.getXAxis().setAxisMaximum(12f);
//================================================================
        setupPieChart(str);
        loadPieChartData(str);
    }

        ArrayList<Entry>linechart(float yval[],int i){
        ArrayList<Entry> dataset=new ArrayList<Entry>();

        int temp =0;
       /* =(int)System.currentTimeMillis();
        Timestamp time =new Timestamp(j);
        String str=time.toString();
        j= Integer.parseInt(str);*/

           /* OffsetTime offset = OffsetTime.now();
            offset.getHour();*/

            dataset.add(new Entry(0,0));
            if(i<=13) {
                for (j = 0; j < i; j++) {
                    if (yval[j] != 0) {
                        //dataset.add(new Entry(temp, yval[temp]));
                        dataset.add(new Entry(j, yval[j]));
                    }
                }
            }
//================== need for loop ===============
            else if(i>13){
                yval[0]=yval[i-(i-1)];
                dataset.add(new Entry(0, yval[0]));

                yval[1]=yval[i-(i-2)];
                dataset.add(new Entry(1, yval[1]));

                yval[2]=yval[i-(i-3)];
                dataset.add(new Entry(2, yval[2]));

                yval[3]=yval[i-(i-4)];
                dataset.add(new Entry(3, yval[3]));

                yval[4]=yval[i-(i-5)];
                dataset.add(new Entry(4, yval[4]));

                yval[5]=yval[i-(i-6)];
                dataset.add(new Entry(5, yval[5]));

                yval[6]=yval[i-(i-7)];
                dataset.add(new Entry(6, yval[6]));

                yval[7]=yval[i-(i-8)];
                dataset.add(new Entry(7, yval[7]));

                
                yval[8]=yval[i-(i-9)];
                dataset.add(new Entry(8, yval[8]));

                yval[9]=yval[i-(i-10)];
                dataset.add(new Entry(9, yval[9]));

                yval[10]=yval[i-(i-11)];
                dataset.add(new Entry(10, yval[10]));

                yval[11]=yval[i-(i-12)];
                dataset.add(new Entry(11, yval[11]));

               yval[12]=yval[i-(i-x)];
                dataset.add(new Entry(12, yval[12]));
                x++;
            }
        return dataset;
    }
   /* @OnClick(R.id.write)
    public void onWriteClick() {

        if (isConnected()) {
            final Disposable disposable = connectionObservable
                    .firstOrError()
                    .flatMap(rxBleConnection -> rxBleConnection.writeCharacteristic(characteristicUuid, getInputBytes()))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            bytes -> onWriteSuccess(),
                            this::onWriteFailure
                    );

            compositeDisposable.add(disposable);
        }
    }*/
  /*  @OnClick(R.id.notify)
    public void onNotifyClick() {

        if (isConnected()) {
            final Disposable disposable = connectionObservable
                    .flatMap(rxBleConnection -> rxBleConnection.setupNotification(characteristicUuid))
                    .doOnNext(notificationObservable -> runOnUiThread(this::notificationHasBeenSetUp))
                    .flatMap(notificationObservable -> notificationObservable)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onNotificationReceived, this::onNotificationSetupFailure);

            compositeDisposable.add(disposable);
        }
    }*/
  public void setupPieChart(String s) {
      //pieChart.setBackgroundColor(getResources().getColor(android.R.color.transparent));
      pieChart.setDrawHoleEnabled(true);
      pieChart.setUsePercentValues(true);
      pieChart.setEntryLabelTextSize(40);
      pieChart.setEntryLabelColor(Color.BLACK);
      pieChart.setCenterText(s);
      pieChart.setCenterTextSize(40);
      pieChart.getDescription().setEnabled(false);

      Legend l = pieChart.getLegend();
      l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
      l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
      l.setOrientation(Legend.LegendOrientation.VERTICAL);
      l.setDrawInside(false);
      l.setEnabled(true);
  }
    static private ArrayList<Entry> linechart1() {
        ArrayList<Entry> dataset = new ArrayList<Entry>();
        dataset.add(new Entry(1, 4));
        dataset.add(new Entry(13, 24));
        dataset.add(new Entry(21, 41));
        dataset.add(new Entry(17, 56));
        dataset.add(new Entry(22, 7));
        dataset.add(new Entry(12, 4));
        dataset.add(new Entry(53, 44));
        return dataset;
    }

    public void loadPieChartData(String s) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(0.2f, ""));


    /*    ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for (int color: ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }*/


        if(Integer.valueOf(s)<=120) {
            PieDataSet dataSet = new PieDataSet(entries, "");
            dataSet.setColors(GREEN);
            int a=Integer.valueOf(s);
            PieData data = new PieData(dataSet);
            data.setDrawValues(false);
            data.setValueFormatter(new PercentFormatter(pieChart));
            pieChart.setData(data);
            pieChart.invalidate();
            pieChart.setHoleColor(Color.TRANSPARENT);//---
            pieChart.setTouchEnabled(false);
            pieChart.setMaxAngle(360f);
            // pieChart.setRotation(-135);
            pieChart.setHoleRadius(75f);
            pieChart.setTransparentCircleRadius(60f);
            pieChart.getLegend().setEnabled(false);
            pieChart.setDrawRoundedSlices(true);
            pieChart.animateY(1400, Easing.EaseInOutQuad);
        }
        else if ((Integer.valueOf(s)>120) &&(Integer.valueOf(s)<180) ){
            PieDataSet dataSet = new PieDataSet(entries, "");
            int a=Integer.valueOf(s);
            dataSet.setColors(Color.rgb(255, 165, 0));
            PieData data = new PieData(dataSet);
            data.setDrawValues(false);
            data.setValueFormatter(new PercentFormatter(pieChart));
            pieChart.setData(data);
            pieChart.invalidate();
            pieChart.setHoleColor(Color.TRANSPARENT);//---
            pieChart.setTouchEnabled(false);
            pieChart.setMaxAngle(360f);
            // pieChart.setRotation(-135);
            pieChart.setHoleRadius(75f);
            pieChart.setTransparentCircleRadius(60f);
            pieChart.getLegend().setEnabled(false);
            pieChart.setDrawRoundedSlices(true);
            pieChart.animateY(1400, Easing.EaseInOutQuad);
        }
        else if(Integer.valueOf(s)>180){
            PieDataSet dataSet = new PieDataSet(entries, "");
            int a=Integer.valueOf(s);
            dataSet.setColors(RED);
            PieData data = new PieData(dataSet);
            data.setDrawValues(false);
            data.setValueFormatter(new PercentFormatter(pieChart));
            pieChart.setData(data);
            pieChart.invalidate();
            pieChart.setHoleColor(Color.TRANSPARENT);//---
            pieChart.setTouchEnabled(false);
            pieChart.setMaxAngle(360f);
            // pieChart.setRotation(-135);
            pieChart.setHoleRadius(75f);
            pieChart.setTransparentCircleRadius(60f);
            pieChart.getLegend().setEnabled(false);
            pieChart.setDrawRoundedSlices(true);
            pieChart.animateY(1400, Easing.EaseInOutQuad);
        }
    }
    private boolean isConnected() {
        return bleDevice.getConnectionState() == RxBleConnection.RxBleConnectionState.CONNECTED;
    }

    private void onConnectionFailure(Throwable throwable) {
        //noinspection ConstantConditions
        Snackbar.make(findViewById(R.id.main), "Connection error: " + throwable, Snackbar.LENGTH_SHORT).show();
        updateUI(null);
    }

    private void onConnectionFinished() {
        updateUI(null);
    }
    public boolean foregroundServiceRunning(){
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo service: activityManager.getRunningServices(Integer.MAX_VALUE)) {
            if(myservice.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
    private void onReadFailure(Throwable throwable) {
        //noinspection ConstantConditions
        Snackbar.make(findViewById(R.id.main), "Read error: " + throwable, Snackbar.LENGTH_SHORT).show();
    }

    private void onWriteSuccess() {
        //noinspection ConstantConditions
        Snackbar.make(findViewById(R.id.main), "Write success", Snackbar.LENGTH_SHORT).show();
    }

    private void onWriteFailure(Throwable throwable) {
        //noinspection ConstantConditions
        Snackbar.make(findViewById(R.id.main), "Write error: " + throwable, Snackbar.LENGTH_SHORT).show();
    }

    private void onNotificationReceived(byte[] bytes) {
        //noinspection ConstantConditions
        Snackbar.make(findViewById(R.id.main), "Change: " + HexString.bytesToHex(bytes), Snackbar.LENGTH_SHORT).show();
    }

    private void onNotificationSetupFailure(Throwable throwable) {
        //noinspection ConstantConditions
        Snackbar.make(findViewById(R.id.main), "Notifications error: " + throwable, Snackbar.LENGTH_SHORT).show();
    }

    private void notificationHasBeenSetUp() {
        //noinspection ConstantConditions
        Snackbar.make(findViewById(R.id.main), "Notifications has been set up", Snackbar.LENGTH_SHORT).show();
    }

    private void triggerDisconnect() {
        disconnectTriggerSubject.onNext(true);
    }

    /**
     * This method updates the UI to a proper state.
     *
     * @param characteristic a nullable {@link BluetoothGattCharacteristic}. If it is null then UI is assuming a disconnected state.
     */
    private void updateUI(BluetoothGattCharacteristic characteristic) {
        connectButton.setText(characteristic != null ? R.string.disconnect : R.string.connect);
        readButton.setEnabled(hasProperty(characteristic, BluetoothGattCharacteristic.PROPERTY_READ));
        //writeButton.setEnabled(hasProperty(characteristic, BluetoothGattCharacteristic.PROPERTY_WRITE));
        notifyButton.setEnabled(hasProperty(characteristic, BluetoothGattCharacteristic.PROPERTY_NOTIFY));
    }

    private boolean hasProperty(BluetoothGattCharacteristic characteristic, int property) {
        return characteristic != null && (characteristic.getProperties() & property) > 0;
    }
    /*private byte[] getInputBytes() {
        return HexString.hexToBytes(writeInput.getText().toString());
    }*/
    @Override
    protected void onPause() {
        super.onPause();
        compositeDisposable.clear();
    }
}
