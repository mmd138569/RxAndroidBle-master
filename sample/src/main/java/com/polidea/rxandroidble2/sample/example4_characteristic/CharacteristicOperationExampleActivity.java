package com.polidea.rxandroidble2.sample.example4_characteristic;

import static android.graphics.Color.GRAY;

import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.snackbar.Snackbar;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rx.ReplayingShare;
import com.polidea.rxandroidble2.RxBleConnection;
import com.polidea.rxandroidble2.RxBleDevice;
import com.polidea.rxandroidble2.sample.DeviceActivity;
import com.polidea.rxandroidble2.sample.R;
import com.polidea.rxandroidble2.sample.SampleApplication;
import com.polidea.rxandroidble2.sample.util.HexString;

import java.io.FileOutputStream;
import java.io.IOException;
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

    public static final String EXTRA_CHARACTERISTIC_UUID = "extra_uuid";
    @BindView(R.id.connect)
    TextView connectButton;
    @BindView(R.id.read_output)
    TextView readOutputView;
/*    @BindView(R.id.read_hex_output)
    TextView readHexOutputView;*/
   /* @BindView(R.id.write_input)
    TextView writeInput;*/
    @BindView(R.id.read)
    TextView readButton;
  /*  @BindView(R.id.write)
    Button writeButton;
    @BindView(R.id.notify)
    Button notifyButton;*/
    private UUID characteristicUuid;
    private PublishSubject<Boolean> disconnectTriggerSubject = PublishSubject.create();
    private Observable<RxBleConnection> connectionObservable;
    private RxBleDevice bleDevice;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    //TextView save, refresh;
    //TextView name;
    private ListView listView;
    CustomLineChart lineChart;
    public static Intent startActivityIntent(Context context, String peripheralMacAddress, UUID characteristicUuid) {
        Intent intent = new Intent(context, CharacteristicOperationExampleActivity.class);
        intent.putExtra(DeviceActivity.EXTRA_MAC_ADDRESS, peripheralMacAddress);
        intent.putExtra(EXTRA_CHARACTERISTIC_UUID, characteristicUuid);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example4);
        lineChart = findViewById(R.id.chart);

        ButterKnife.bind(this);
        String macAddress = getIntent().getStringExtra(DeviceActivity.EXTRA_MAC_ADDRESS);
        characteristicUuid = (UUID) getIntent().getSerializableExtra(EXTRA_CHARACTERISTIC_UUID);
        bleDevice = SampleApplication.getRxBleClient(this).getBleDevice(macAddress);
        connectionObservable = prepareConnectionObservable();
        //noinspection ConstantConditions
        getSupportActionBar().setSubtitle(getString(R.string.mac_address, macAddress));
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





//==================================================
    }

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

            compositeDisposable.add(connectionDisposable);
        }
    }
//==========================================================================
//literly i think the read method called after 4 or 5 second so we need theard for 4 or 5 second tho

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
    @OnClick(R.id.refresh)
    public void refreshing() {
        float yval = 0;
        final DatabaseHelper helper = new DatabaseHelper(CharacteristicOperationExampleActivity.this);
        final ArrayList array_list = helper.getAllCotacts();
        //name = findViewById(R.id.name);
        readOutputView = findViewById(R.id.read_output);
        listView = findViewById(R.id.listView);
        final ArrayAdapter arrayAdapter = new ArrayAdapter(CharacteristicOperationExampleActivity.this,
                android.R.layout.simple_list_item_1, array_list);
        listView.setAdapter(arrayAdapter);
        if ( !readOutputView.getText().toString().isEmpty()) {
            yval =Float.parseFloat(String.valueOf(readOutputView.getText()));
            
            if (helper.insert(/*name.getText()*/ yval)) {
              
                Toast.makeText(CharacteristicOperationExampleActivity.this, "Inserted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(CharacteristicOperationExampleActivity.this, "NOT Inserted", Toast.LENGTH_LONG).show();
            }
        } else {
            // name.setError("Enter NAME");
            readOutputView.setError("Enter Salary");
        }
//================================================

//========================= refresh ===============

        array_list.clear();
        array_list.addAll(helper.getAllCotacts());
        arrayAdapter.notifyDataSetChanged();
        listView.invalidateViews();
        listView.refreshDrawableState();

        LineDataSet lineDataSet = new LineDataSet(linechart(yval), "lable");
        ArrayList<ILineDataSet>iLineDataSets=new ArrayList<>();
        iLineDataSets.add(lineDataSet);
        LineData lineData=new LineData(iLineDataSets);
        lineChart.setData(lineData);
        lineChart.invalidate();
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


        // LineData lineData = new LineData(line3);
       // lineChart.setData(lineData);
        // lineChart.invalidate();
       // lineData.setDrawValues(false);
        //lineChart.getDescription().setEnabled(false);


    }
    ArrayList<Entry>linechart(float yval){
        ArrayList<Entry> dataset=new ArrayList<Entry>();
        for(int j=0;j<12;j++) {
            dataset.add(new Entry(yval, j));
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
        //notifyButton.setEnabled(hasProperty(characteristic, BluetoothGattCharacteristic.PROPERTY_NOTIFY));
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
