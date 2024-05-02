package com.polidea.rxandroidble2.sample.example4_characteristic;

import static android.graphics.Color.GRAY;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;
import static android.graphics.Color.TRANSPARENT;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.RectF;
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
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.PathInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rx.ReplayingShare;
import com.polidea.rxandroidble2.RxBleConnection;
import com.polidea.rxandroidble2.RxBleDevice;
import com.polidea.rxandroidble2.sample.DBChart;
import com.polidea.rxandroidble2.sample.DBcalibrate;
import com.polidea.rxandroidble2.sample.DeviceActivity;
import com.polidea.rxandroidble2.sample.R;
import com.polidea.rxandroidble2.sample.SampleApplication;
import com.polidea.rxandroidble2.sample.landscapechart;
import com.polidea.rxandroidble2.sample.myservice;
import com.polidea.rxandroidble2.sample.settings;
import com.polidea.rxandroidble2.sample.settingsview;
import com.polidea.rxandroidble2.sample.util.HexString;
import com.polidea.rxandroidble2.scan.ScanResult;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
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
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class CharacteristicOperationExampleActivity extends AppCompatActivity {

    ImageView top,butt,twotop,twobutt,left,x2,x1,signal_strength1,signal_strength2,signal_strength3;
    int ii=0;
    private Disposable connectionDisposable1,connectionDisposable;

    public static final String EXTRA_CHARACTERISTIC_UUID = "extra_uuid";
    @BindView(R.id.connect)
    TextView connectButton;
    @BindView(R.id.read_output)
    TextView readOutputView;
    static String macAddress;
    float yval[] = new float[21];
/*    @BindView(R.id.read_hex_output)
    TextView readHexOutputView;
 @BindView(R.id.write_input)
    TextView writeInput;*/
    int temp =0;
    float time, time1;
    @BindView(R.id.read)
    TextView readButton;
    public static int z=0;
    public static String z1;
    @BindView(R.id.rssi)
    TextView rssiView;
  /*  @BindView(R.id.write)
    Button writeButton;*/
    @BindView(R.id.notify)
    Button notifyButton;
    boolean shoutdown1=false,shoutdown2=false;
    private UUID characteristicUuid;
    String  str="0";
    int i=2,x=13,j=0;
    public static PieChart pieChart;
    int aa=0;
    boolean a=false;
    private PublishSubject<Boolean> disconnectTriggerSubject = PublishSubject.create();
    private Observable<RxBleConnection> connectionObservable;
    private RxBleDevice bleDevice;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    //TextView save, refresh;
    //TextView name;
    RectF oval=null;
    private ListView listView;
    public boolean data_oomad=false;
    public boolean data_oomad2=false;
    public boolean data_oomad3=false;
    float rangeHigh = 10.5f;
    float rangeLow = -1f;
    float rangeLow2 = 11f;
    float rangeHigh2 = 38f;
    float rangeLow3 = 38.5f;
    float rangeHigh3 = 61f;
    float rangeHigh1 = 100f;
    float rangeLow1 = -7f;
    float rangeLow21 = 103f;
    float rangeHigh21= 350f;
    float rangeLow31 = 353f;
    float rangeHigh31 = 400f;

    float rangeHigh41 = 300f;
    float rangeLow41 = -7f;
    float rangeLow42 = 303f;
    float rangeHigh42 = 650f;
    float rangeLow43 = 653f;
    float rangeHigh43 = 1200f;
    float rangeHigh00 = 100f;
    float rangeLow00 = -7f;
    float rangeLow02 = 103f;
    float rangeHigh02 = 250f;
    float rangeLow03 = 253f;
    float rangeHigh03 = 300f;
    public CustomLineChart.TargetZone target00= new CustomLineChart.TargetZone(Color.parseColor("#feebe5"), rangeLow00, rangeHigh00, "");
    public CustomLineChart.TargetZone target10=new CustomLineChart.TargetZone(Color.parseColor("#dfdfdf"), rangeLow02, rangeHigh02, "");
    public CustomLineChart.TargetZone target20=new CustomLineChart.TargetZone(Color.parseColor("#fef5e6"), rangeLow03, rangeHigh03, "");
    public CustomLineChart.TargetZone target0=new CustomLineChart.TargetZone(Color.parseColor("#feebe5"), rangeLow, rangeHigh, "");
    public CustomLineChart.TargetZone target1=new CustomLineChart.TargetZone(Color.parseColor("#dfdfdf"), rangeLow2, rangeHigh2, "");
    public CustomLineChart.TargetZone target2=new CustomLineChart.TargetZone(Color.parseColor("#fef5e6"), rangeLow3, rangeHigh3, "");

    public CustomLineChart.TargetZone target01=new CustomLineChart.TargetZone(Color.parseColor("#feebe5"), rangeLow1, rangeHigh1, "");
    public CustomLineChart.TargetZone target11=new CustomLineChart.TargetZone(Color.parseColor("#dfdfdf"), rangeLow21, rangeHigh21, "");
    public CustomLineChart.TargetZone target21=new CustomLineChart.TargetZone(Color.parseColor("#fef5e6"), rangeLow31, rangeHigh31, "");

    public CustomLineChart.TargetZone target03=new CustomLineChart.TargetZone(Color.parseColor("#feebe5"), rangeLow41, rangeHigh41, "");
    public CustomLineChart.TargetZone target13=new CustomLineChart.TargetZone(Color.parseColor("#dfdfdf"), rangeLow42, rangeHigh42, "");
    public CustomLineChart.TargetZone target23=new CustomLineChart.TargetZone(Color.parseColor("#fef5e6"), rangeLow43, rangeHigh43, "");
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
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


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
        TextView setting=findViewById(R.id.settings);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Intent in=new Intent(CharacteristicOperationExampleActivity.this, settings.class);
                startActivity(in);
                finish();*/
                Intent intent = new Intent(getApplicationContext(), settingsview.class);
                intent.putExtra("mac_add", macAddress);
                startActivity(intent);
            }
        });
        lineChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), landscapechart.class);
                intent.putExtra("mac_add", macAddress);
                startActivity(intent);
            }
        });
        characteristicUuid = (UUID) getIntent().getSerializableExtra(EXTRA_CHARACTERISTIC_UUID);
        macAddress = getIntent().getStringExtra(DeviceActivity.EXTRA_MAC_ADDRESS);
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


            lineChart.addTargetZone(target0);
            lineChart.addTargetZone(target1);
            lineChart.addTargetZone(target2);
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
        Handler nand1 =new Handler();
        Handler nand2 =new Handler();
        Handler nand3 =new Handler();
        Handler nand4 =new Handler();

        signal_strength1= findViewById(R.id.signal_strength1);
        signal_strength2= findViewById(R.id.signal_strength2);
        signal_strength3= findViewById(R.id.signal_strength3);

        /*nand1.postDelayed(new Runnable() {
            @Override
            public void run() {
                 if(isConnected()) {
                     connectionDisposable.dispose();
                     rssi_should_work();


                 }
                hand.postDelayed(this, 2500);

            }
        },2500);

        hand.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isConnected()) {
                    connectionDisposable1.dispose();
                    onConnectToggleClick();
                }
                hand.postDelayed(this, 1000);

            }
        },1000);*/
        nand1.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!isConnected()) {
                    onConnectToggleClick();
                }
                nand1.postDelayed(this, 300);
            }
        },300);

       /* nand2.postDelayed(new Runnable() {
            @Override
            public void run() {
                connectionDisposable.dispose();
                nand2.postDelayed(this, 3100);
            }
        },3100);*/
        /*nand3.postDelayed(new Runnable() {
            @Override
            public void run() {
                rssi_should_work();

                nand3.postDelayed(this, 3150);
            }
        },3150);*/

      /*  nand4.postDelayed(new Runnable() {
            @Override
            public void run() {
//                connectionDisposable1.dispose();

                nand4.postDelayed(this, 3750);
            }
        },3750);*/



        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //connect
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
//last change on git is the way to get all the data
                handler.postDelayed(this, 500);
                Runnable r=new Runnable() {
                    @Override
                    public void run() {
                            onNotifyClick();
                            // onReadClick();
                        //}
                    }
                };           nand.postDelayed(r, 400);

            }
        },500);
        hand.postDelayed(new Runnable() {
            @Override
            public void run() {
                shoutdown2=false;
                shoutdown1=false;
                refreshing();

                hand.postDelayed(this, 3000);

            }
        },3000);

       // thread();

//=============== this is important ===========
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
                    connectionDisposable = connectionObservable
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
    }
    public void  rssi_should_work(){
        connectionDisposable1 = bleDevice.establishConnection(true)
                .doFinally(this::clearSubscription)
                .flatMap(RxBleConnection -> // Set desired interval.
                        Observable.interval(400, MILLISECONDS)
                                .flatMapSingle(sequence -> RxBleConnection.readRssi()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateRssi, this::onConnectionFailure);
    }
//==========================================================================
//literly i think the read method called after 4 or 5 second so we need theard for 4 or 5 second
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
                    .observeOn(Schedulers.io())
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
    public static boolean ternerry(Integer num) {
        return 0 == (num == null ? 0 : num);
    }
    @OnClick(R.id.refresh)
    public void refreshing() {
        float centerX = 438;
        float centerY = 320;
        float radius = 285;
        float startAngle = 360f;
        float sweepAngle = -180f;
        twotop=findViewById(R.id.twotop);
        left=findViewById(R.id.leFt);
        butt=findViewById(R.id.butt);
        twobutt=findViewById(R.id.twobutt);
        top=findViewById(R.id.top);
        x1=findViewById(R.id.topmid);
        x2=findViewById(R.id.buttmid);

        final DBcalibrate dBcalibrate = new DBcalibrate(CharacteristicOperationExampleActivity.this);
        final ArrayList z = dBcalibrate.getAllCotacts1();

        final DatabaseHelper helper = new DatabaseHelper(CharacteristicOperationExampleActivity.this);
        final ArrayList array_list = helper.getAllCotacts();
        //name = findViewById(R.id.name);
        readOutputView = findViewById(R.id.read_output);
        listView = findViewById(R.id.listView);
        final ArrayAdapter arrayAdapter = new ArrayAdapter(CharacteristicOperationExampleActivity.this,
                android.R.layout.simple_list_item_1, array_list);
        listView.setAdapter(arrayAdapter);
        //System.out.println(readOutputView+"=======================");
        if (!readOutputView.getText().toString().isEmpty()&&readOutputView.getText().toString()!="20000") {
//===========================================================================================================
            yval[20] = Float.parseFloat(String.valueOf(readOutputView.getText()));
            if(z.size()!=0) {
                int a = Integer.valueOf((String) z.get(z.size() - 1));
                ternerry(a);
                if (a != 0) {
                    yval[20] = yval[20] - a;
                }
            }
            System.out.println("===================="+yval[20]);
            Intent intent  = new Intent(this, myservice.class);

            int songUrl=Integer.parseInt(String.valueOf(readOutputView.getText()));
            intent.putExtra("YOUR_KEY_SONG_NAME", songUrl);
            startService(intent);
//===========================================================================================================
           data_oomad=true;
           data_oomad2=true;
           data_oomad3=true;
              str=String.valueOf((int)yval[20]);

              //str = readOutputView.getText().toString();

            if (helper.insert(/*name.getText()*/ yval[20])) {

                //Toast.makeText(CharacteristicOperationExampleActivity.this, "Inserted", Toast.LENGTH_LONG).show();
            } else {
                //Toast.makeText(CharacteristicOperationExampleActivity.this, "NOT Inserted", Toast.LENGTH_LONG).show();
            }
            readOutputView.setText("20000");
        } else {
            // name.setError("Enter NAME");
            //readOutputView.setError("Enter Salary");
        }


        float x=yval[20]-yval[19];
        float y=yval[19]-yval[20];
        //Toast.makeText(this, String.valueOf(x), Toast.LENGTH_SHORT).show();
        left.setVisibility(View.INVISIBLE);
        twobutt.setVisibility(View.INVISIBLE);
        twotop.setVisibility(View.INVISIBLE);
        top.setVisibility(View.INVISIBLE);
        butt.setVisibility(View.INVISIBLE);
        x1.setVisibility(View.INVISIBLE);
        x2.setVisibility(View.INVISIBLE);
 //====================================================================
        if(data_oomad==true){
        anim(   centerX , centerY , radius , x,y);
        data_oomad=false;}
//================================================
//========================= refresh ===============
        final DBChart dbChart = new DBChart(CharacteristicOperationExampleActivity.this);
        final ArrayList mychart = dbChart.getAllCotact1();
        if(mychart.size()!=0) {
            int my_Chart = Integer.valueOf((String) mychart.get(mychart.size() - 1));

            if (my_Chart == 300) {

                lineChart.setTouchEnabled(true);
                lineChart.setScaleEnabled(false);
                lineChart.addTargetZone(target00);
                lineChart.addTargetZone(target10);
                lineChart.addTargetZone(target20);
                array_list.clear();
            } else if (my_Chart == 400) {


                lineChart.setTouchEnabled(true);
                lineChart.setScaleEnabled(false);
                lineChart.addTargetZone(target03);
                lineChart.addTargetZone(target13);
                lineChart.addTargetZone(target23);
                array_list.clear();
            }
            if(my_Chart==300) {
                lineChart.getAxisLeft().setAxisMaximum(300f);
                lineChart.getAxisRight().setAxisMaximum(300f);
            }
            else if(my_Chart==400){
                lineChart.getAxisLeft().setAxisMaximum(1200f);
                lineChart.getAxisRight().setAxisMaximum(1200f);
            }
            else {
                lineChart.getAxisLeft().setAxisMaximum(400f);
                lineChart.getAxisRight().setAxisMaximum(400f);
            }
        }
        else {


        lineChart.setTouchEnabled(true);
        lineChart.setScaleEnabled(false);
        lineChart.addTargetZone(target01);
        lineChart.addTargetZone(target11);
        lineChart.addTargetZone(target21);
        array_list.clear(); }
        array_list.addAll(helper.getAllCotacts());
        arrayAdapter.notifyDataSetChanged();
        listView.invalidateViews();
        listView.refreshDrawableState();
        LineDataSet lineDataSet = new LineDataSet(linechart(yval, i,(int)yval[20]), "lable");
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
        if(yval[20]%12==0){
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
        OffsetTime offset1 = OffsetTime.now();
        time = offset1.getHour();
        time1 = offset1.getMinute();
        time=time+time1/100;
        XAxis xAxis=lineChart.getXAxis();
        xAxis.setLabelCount(3,true);
        lineChart.getXAxis().setAxisMaximum((float) (time+1));
        lineChart.getXAxis().setAxisMinimum(time);
//================================================================
        setupPieChart(str);
        if(data_oomad2==true) {
            loadPieChartData(str);
            data_oomad2=false;
        }
    }

public void anim( float centerX , float centerY, float radius,float x, float y){
    Handler animstart=new Handler();
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            while (!shoutdown1) {
                if(((x<50)&&(x>=0))||(y<50)&&(y>=0)){
                    ObjectAnimator rotationAnimator1 = ObjectAnimator.ofFloat(top, "rotation", 180f, 0f);  // Specify the start and end rotation angles
                    rotationAnimator1.setDuration(2000);  // Set the duration of the rotation animation in milliseconds
                    rotationAnimator1.setRepeatCount(0);
                    rotationAnimator1.start();
                }
                else if((x<150)&&(x>=100)) {
                    ObjectAnimator rotationAnimator1 = ObjectAnimator.ofFloat(top, "rotation", 180f, 90f);  // Specify the start and end rotation angles
                    rotationAnimator1.setDuration(2000);  // Set the duration of the rotation animation in milliseconds
                    rotationAnimator1.setRepeatCount(0);
                    rotationAnimator1.start();
                }
                else if ((x<100)&&(x>=50)) {
                    ObjectAnimator rotationAnimator1 = ObjectAnimator.ofFloat(top, "rotation", 180f, 45f);  // Specify the start and end rotation angles
                    rotationAnimator1.setDuration(2000);  // Set the duration of the rotation animation in milliseconds
                    rotationAnimator1.setRepeatCount(0);
                    rotationAnimator1.start();
                }
                else if ((y<100)&&(y>=50)) {
                    ObjectAnimator rotationAnimator1 = ObjectAnimator.ofFloat(top, "rotation", 180f, -45f);  // Specify the start and end rotation angles
                    rotationAnimator1.setDuration(2000);  // Set the duration of the rotation animation in milliseconds
                    rotationAnimator1.setRepeatCount(0);
                    rotationAnimator1.start();
        }
                else if((y<150)&&(y>=100)){
                    ObjectAnimator rotationAnimator1 = ObjectAnimator.ofFloat(top, "rotation", 180f, -90f);  // Specify the start and end rotation angles
                    rotationAnimator1.setDuration(2000);  // Set the duration of the rotation animation in milliseconds
                    rotationAnimator1.setRepeatCount(0);
                    rotationAnimator1.start();
        }
                else if(y>=150){
                    ObjectAnimator rotationAnimator1 = ObjectAnimator.ofFloat(twotop, "rotation", 180f, -90f);  // Specify the start and end rotation angles
                    rotationAnimator1.setDuration(2000);  // Set the duration of the rotation animation in milliseconds
                    rotationAnimator1.setRepeatCount(0);
                    rotationAnimator1.start();
        }
                else if(x>=150){
                    ObjectAnimator rotationAnimator1 = ObjectAnimator.ofFloat(twotop, "rotation", 180f, 90f);  // Specify the start and end rotation angles
                    rotationAnimator1.setDuration(2000);  // Set the duration of the rotation animation in milliseconds
                    rotationAnimator1.setRepeatCount(0);
                    rotationAnimator1.start();
        }
                    shoutdown1=true;
            }
        }
    }; animstart.postDelayed(runnable,1900);
    Handler animstart1=new Handler();
    Runnable r1=new Runnable() {
        @Override
        public void run() {
            while (!shoutdown2) {
                top.setVisibility(View.VISIBLE);
                if(((x<50)&&(x>=0))||(y<50)&&(y>=0)) {
                    Path path = new Path();
                    if(oval==null) {
                        oval = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
                    }
                    path.arcTo(oval, 360, -180, true);
                // Create a PathInterpolator with the circular path
                    PathInterpolator pathInterpolator = new PathInterpolator(0.25f, 0.1f, 0.25f, 1f);
                // Create an ObjectAnimator to rotate the image along the circular path
                    ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(top, "translationX", "translationY", path);
                    rotationAnimator.setDuration(2000); // Set the desired duration for the rotation
                    rotationAnimator.setInterpolator(pathInterpolator);
                    rotationAnimator.setRepeatCount(0); // Repeat the rotation indefinitely
                    rotationAnimator.start();
                }
                else if((x<150)&&(x>=100)) {//top

                    Path path = new Path();
                    if(oval==null) {
                        oval = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
                    }
                    path.arcTo(oval, 360, -90, true);
                    // Create a PathInterpolator with the circular path
                    PathInterpolator pathInterpolator = new PathInterpolator(0.25f, 0.1f, 0.25f, 1f);
                    // Create an ObjectAnimator to rotate the image along the circular path
                    ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(top, "translationX", "translationY", path);
                    rotationAnimator.setDuration(2000); // Set the desired duration for the rotation
                    rotationAnimator.setInterpolator(pathInterpolator);
                    rotationAnimator.setRepeatCount(0); // Repeat the rotation indefinitely
                    rotationAnimator.start();
                }
                else if ((x<100)&&(x>=50)) {//top mid
                    Path path = new Path();
                    if(oval==null) {
                        oval = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
                    }
                    path.arcTo(oval, 360, -135, true);
                    // Create a PathInterpolator with the circular path
                    PathInterpolator pathInterpolator = new PathInterpolator(0.25f, 0.1f, 0.25f, 1f);
                    // Create an ObjectAnimator to rotate the image along the circular path
                    ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(top, "translationX", "translationY", path);
                    rotationAnimator.setDuration(2000); // Set the desired duration for the rotation
                    rotationAnimator.setInterpolator(pathInterpolator);
                    rotationAnimator.setRepeatCount(0); // Repeat the rotation indefinitely
                    rotationAnimator.start();
        }
                else if ((y<100)&&(y>=50)) {//botmid
                    Path path = new Path();
                    if(oval==null) {
                        oval = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
                    }
                    path.arcTo(oval, 360, -225, true);
                    // Create a PathInterpolator with the circular path
                    PathInterpolator pathInterpolator = new PathInterpolator(0.25f, 0.1f, 0.25f, 1f);
                    // Create an ObjectAnimator to rotate the image along the circular path
                    ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(top, "translationX", "translationY", path);
                    rotationAnimator.setDuration(2000); // Set the desired duration for the rotation
                    rotationAnimator.setInterpolator(pathInterpolator);
                    rotationAnimator.setRepeatCount(0); // Repeat the rotation indefinitely
                    rotationAnimator.start();
        }
                else if((y<150)&&(y>=100)){//bot
                    Path path = new Path();
                    if(oval==null) {
                        oval = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
                    }
                    path.arcTo(oval, 360, -270, true);
                    // Create a PathInterpolator with the circular path
                    PathInterpolator pathInterpolator = new PathInterpolator(0.25f, 0.1f, 0.25f, 1f);
                    // Create an ObjectAnimator to rotate the image along the circular path
                    ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(top, "translationX", "translationY", path);
                    rotationAnimator.setDuration(2000); // Set the desired duration for the rotation
                    rotationAnimator.setInterpolator(pathInterpolator);
                    rotationAnimator.setRepeatCount(0); // Repeat the rotation indefinitely
                    rotationAnimator.start();
        }
                else if(y>=150){
                    Path path = new Path();
                    if(oval==null) {
                        oval = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
                    }
                    path.arcTo(oval, 360, -270, true);
                    // Create a PathInterpolator with the circular path
                    PathInterpolator pathInterpolator = new PathInterpolator(0.25f, 0.1f, 0.25f, 1f);
                    // Create an ObjectAnimator to rotate the image along the circular path
                    ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(twotop, "translationX", "translationY", path);
                    rotationAnimator.setDuration(2000); // Set the desired duration for the rotation
                    rotationAnimator.setInterpolator(pathInterpolator);
                    rotationAnimator.setRepeatCount(0); // Repeat the rotation indefinitely
                    rotationAnimator.start();
        }
                else if(x>=150){
                    Path path = new Path();
                    if(oval==null) {
                        oval = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
                    }
                    path.arcTo(oval, 360, -90, true);
                    // Create a PathInterpolator with the circular path
                    PathInterpolator pathInterpolator = new PathInterpolator(0.25f, 0.1f, 0.25f, 1f);
                    // Create an ObjectAnimator to rotate the image along the circular path
                    ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(twotop, "translationX", "translationY", path);
                    rotationAnimator.setDuration(2000); // Set the desired duration for the rotation
                    rotationAnimator.setInterpolator(pathInterpolator);
                    rotationAnimator.setRepeatCount(0); // Repeat the rotation indefinitely
                    rotationAnimator.start();
        }
                    shoutdown2=true;
            }
        }
    };animstart1.postDelayed(r1,1900);

}
        ArrayList<Entry>linechart(float yvals[],int i,int lastone){
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
                    if (yvals[j] != 0) {
                        //dataset.add(new Entry(temp, yval[temp]));
                        dataset.add(new Entry(time+(float)j/13, yvals[j]));
                    }
                }
            }
//================== need for loop ===============
            else if(i>13){
                yvals[0]=yvals[1];
                dataset.add(new Entry((float)time+0, yvals[0]));

                yvals[1]=yvals[2];
                dataset.add(new Entry( (float) (time+(1.0/12.0)), yvals[1]));

                yvals[2]=yvals[3];
                dataset.add(new Entry( (float) (time+(2.0/12.0)), yvals[2]));

                yvals[3]=yvals[4];
                dataset.add(new Entry( (float) (time+(3.0/12.0)), yvals[3]));

                yvals[4]=yvals[5];
                dataset.add(new Entry( (float) (time+(4.0/12.0)), yvals[4]));

                yvals[5]=yvals[6];
                dataset.add(new Entry( (float) (time+(5.0/12.0)), yvals[5]));

                yvals[6]=yvals[7];
                dataset.add(new Entry((float) (time+(6.0/12.0)), yvals[6]));

                yvals[7]=yvals[8];
                dataset.add(new Entry( (float) (time+(7.0/12.0)), yvals[7]));


                yvals[8]=yvals[9];
                dataset.add(new Entry( (float) (time+(8.0/12.0)), yvals[8]));

                yvals[9]=yvals[10];
                dataset.add(new Entry( (float) (time+(9.0/12.0)), yvals[9]));

                yvals[10]=yvals[11];
                dataset.add(new Entry( (float) (time+(10.0/12.0)), yvals[10]));

                yvals[11]=yvals[12];
                dataset.add(new Entry((float) (time+(11.0/12.0)), yvals[11]));

               yvals[12]=lastone;
                dataset.add(new Entry( (time+(float)(1)), yvals[12]));
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
            //int a=Integer.valueOf(s);
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
            //int a=Integer.valueOf(s);
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
            //int a=Integer.valueOf(s);
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
