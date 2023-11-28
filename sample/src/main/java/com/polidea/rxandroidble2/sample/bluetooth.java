package com.polidea.rxandroidble2.sample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ActivityOptions;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.polidea.rxandroidble2.sample.example1_scanning.ScanActivity;

public class bluetooth extends AppCompatActivity {
    BluetoothAdapter bluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_bluetooth);
        Button NextBtn =findViewById(R.id.NextBtn);

        NextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  Intent in = new Intent(bluetooth.this, EntertransmitterSN1.class);
                ActivityOptions options =
                        ActivityOptions.makeCustomAnimation(bluetooth.this, R.anim.animationint, R.anim.anim);
                bluetooth.this.startActivity(in, options.toBundle());
                if(ContextCompat.checkSelfPermission(bluetooth.this, android.Manifest.permission.BLUETOOTH_CONNECT)== PackageManager.PERMISSION_DENIED) {
                    if(Build.VERSION.SDK_INT>31){
                        ActivityCompat.requestPermissions(bluetooth.this,new String[]{Manifest.permission.BLUETOOTH_CONNECT},100);
                        return;
                    }
                }
                BluetoothManager bluetoothManager=(BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
                if(Build.VERSION.SDK_INT>=31){
                    bluetoothAdapter=bluetoothManager.getAdapter();
                }
                else{
                    bluetoothAdapter= BluetoothAdapter.getDefaultAdapter();
                }
                if(bluetoothAdapter.isEnabled()){
//                    bluetoothAdapter.disable();
                }
                else {
                    bluetoothAdapter.isEnabled();
                }
            }
        });
    }
}