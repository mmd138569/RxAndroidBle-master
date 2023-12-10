package com.polidea.rxandroidble2.sample.example1_scanning;

import android.Manifest;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.ActivityOptions;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.polidea.rxandroidble2.RxBleClient;
import com.polidea.rxandroidble2.exceptions.BleScanException;
import com.polidea.rxandroidble2.sample.DeviceActivity;
import com.polidea.rxandroidble2.sample.EntertransmitterSN1;
import com.polidea.rxandroidble2.sample.R;
import com.polidea.rxandroidble2.sample.SampleApplication;
//import com.polidea.rxandroidble2.sample.example1a_background_scanning.BackgroundScanActivity;
import com.polidea.rxandroidble2.sample.example3_discovery.ServiceDiscoveryExampleActivity;
import com.polidea.rxandroidble2.sample.myservice;
import com.polidea.rxandroidble2.sample.util.ScanExceptionHandler;
import com.polidea.rxandroidble2.sample.util.ScanPermission;
import com.polidea.rxandroidble2.scan.ScanFilter;
import com.polidea.rxandroidble2.scan.ScanResult;
import com.polidea.rxandroidble2.scan.ScanSettings;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class ScanActivity extends AppCompatActivity {

    @BindView(R.id.scan_toggle_btn)
    Button scanToggleButton;
    @BindView(R.id.scan_results)
    RecyclerView recyclerView;
    private RxBleClient rxBleClient;
    BluetoothManager btManager;
    String songUrl1;
    String str1;

    private Disposable scanDisposable;
    private ScanResultsAdapter resultsAdapter;
    private boolean hasClickedScan;
    private AnimatorSet animatorSet;
    private ImageView imgloading;
    BluetoothLeScanner btScanner;
    String s;
    TextView scan1;
    TextView scan2;
    BluetoothAdapter bluetoothAdapter;
    BluetoothAdapter btAdapter;
    private final static int REQUEST_ENABLE_BT = 1;

    boolean a;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_example1);
        ButterKnife.bind(this);
        rxBleClient = SampleApplication.getRxBleClient(this);
        configureResultList();
//===================== progress bar ================================

        imgloading = findViewById(R.id.imgloading);

        animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(ScanActivity.this, R.animator.loadinganime);
        animatorSet.setTarget(imgloading);
        animatorSet.start();

//===================================================================

      /*  if (ContextCompat.checkSelfPermission(ScanActivity.this, android.Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_DENIED) {
            if (Build.VERSION.SDK_INT > 31) {
                ActivityCompat.requestPermissions(ScanActivity.this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 100);
                return;
            }
        }
        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        if (Build.VERSION.SDK_INT >= 31) {
            bluetoothAdapter = bluetoothManager.getAdapter();
        } else {
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        if (bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.disable();
        } else {
            bluetoothAdapter.isEnabled();
        }*/

//==========================================================
        if (ContextCompat.checkSelfPermission(ScanActivity.this, android.Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_DENIED) {
            if (Build.VERSION.SDK_INT >= 31) {
                ActivityCompat.requestPermissions(ScanActivity.this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 100);
                return;
            }
        }
        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        if (Build.VERSION.SDK_INT >= 31) {
            bluetoothAdapter = bluetoothManager.getAdapter();
        } else {
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }

//============= some thing went wrong i can feel it ============

        if (bluetoothAdapter.isEnabled()) {
        } else {
            bluetoothAdapter.isEnabled();
        }
//================================= turn the bluetooth on ===============================================

        btManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        btAdapter = btManager.getAdapter();

        if (btAdapter != null && !btAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }
//======================================================================================================
    }

   /* @OnClick(R.id.background_scan_btn)
    public void onBackgroundScanRequested() {
        startActivity(new Intent(this, BackgroundScanActivity.class));
    }
*/
    @OnClick(R.id.scan_toggle_btn)
    public void onScanToggleClick() {
         scan1 = findViewById(R.id.Scan1);
         scan2 = findViewById(R.id.Scan2);
        if (isScanning()) {
            scanDisposable.dispose();
            a=true;

        } else {
            if (rxBleClient.isScanRuntimePermissionGranted()) {
                scanBleDevices();
            } else {
                hasClickedScan = true;
                ScanPermission.requestScanPermission(this, rxBleClient);
            }
            a=false;
        }

        updateButtonUIState();
//==================== no big deal just change the text view ================

        if(a==false){
            scan1.setVisibility(View.INVISIBLE);
            scan2.setVisibility(View.VISIBLE);
        }
        else if(a==true){
            scan1.setVisibility(View.VISIBLE);
            scan2.setVisibility(View.INVISIBLE);
        }
    }

//===========================================================================

    private void scanBleDevices() {
        Intent intent=getIntent();

       if(EntertransmitterSN1.geta()==2) {
           String str = intent.getStringExtra("my_mac");
           s = "CD:CC:0E:" + str;
       }
       else if(EntertransmitterSN1.geta()==1){
           songUrl1 = intent.getStringExtra("kif");
           s = "CD:CC:0E:" + songUrl1;
       }
        //Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        scanDisposable = rxBleClient.scanBleDevices(
                new ScanSettings.Builder()
                        .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                        .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES)
                        .build(),
                //===================================================================
                new ScanFilter.Builder()
                            .setDeviceAddress(s)
                        // add custom filters if needed  94:E6:86 :05:12:76
                        .build()
        )
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(this::dispose)
                .subscribe(resultsAdapter::addScanResult, this::onScanFailure);
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions,
            @NonNull final int[] grantResults) {
        if (ScanPermission.isScanPermissionGranted(requestCode, permissions, grantResults, rxBleClient)
                && hasClickedScan) {
            hasClickedScan = false;
            scanBleDevices();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (isScanning()) {
            /*
             * Stop scanning in onPause callback.
             */
            scanDisposable.dispose();
        }
    }

    private void configureResultList() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(null);
        LinearLayoutManager recyclerLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyclerLayoutManager);
        resultsAdapter = new ScanResultsAdapter();
        recyclerView.setAdapter(resultsAdapter);
        resultsAdapter.setOnAdapterItemClickListener(view -> {
            final int childAdapterPosition = recyclerView.getChildAdapterPosition(view);
            final ScanResult itemAtPosition = resultsAdapter.getItemAtPosition(childAdapterPosition);
            onAdapterItemClick(itemAtPosition);
        });
    }

    private boolean isScanning() {
        return scanDisposable != null;
    }

//==================intnent it to service discovering============

    private void onAdapterItemClick(ScanResult scanResults) {
        final String macAddress = scanResults.getBleDevice().getMacAddress();
        final Intent intent = new Intent(this, ServiceDiscoveryExampleActivity.class);
        intent.putExtra(DeviceActivity.EXTRA_MAC_ADDRESS, macAddress);
        startActivity(intent);
        /*ActivityOptions options =
                ActivityOptions.makeCustomAnimation(ScanActivity.this, R.anim.animationint, R.anim.anim);
        ScanActivity.this.startActivity(intent, options.toBundle());*/
    }

//===============================================================

    private void onScanFailure(Throwable throwable) {
        if (throwable instanceof BleScanException) {
            ScanExceptionHandler.handleException(this, (BleScanException) throwable);
        } else {
            Log.w("ScanActivity", "Scan failed", throwable);
        }
    }

    private void dispose() {
        scanDisposable = null;
        resultsAdapter.clearScanResults();
        updateButtonUIState();
    }

    private void updateButtonUIState() {
        scanToggleButton.setText(isScanning() ? R.string.stop_scan : R.string.start_scan);
    }
}
