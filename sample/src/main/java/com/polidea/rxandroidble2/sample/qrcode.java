package com.polidea.rxandroidble2.sample;

import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ExperimentalGetImage;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Size;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;

import java.util.List;
import java.util.concurrent.ExecutionException;

import io.reactivex.annotations.NonNull;

public class qrcode extends AppCompatActivity {
    private PreviewView previewView;
    ListenableFuture<ProcessCameraProvider> cameraProviderListenableFuture;
    Button next;
    String str1;
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        editText=findViewById(R.id.edittxt);
        next=findViewById(R.id.next1);
        if(str1!=null) {
         //   Handler h1=new Handler();
          //  Runnable r=new Runnable() {
             //   @Override
             //   public void run() {
                    Intent intent2 = new Intent(qrcode.this, EnterTransmitterSN.class);
                    startActivity(intent2);
                    finish();

        }
           // };
          //  h1.postDelayed(r,1000);

        //}
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in =new Intent(qrcode.this,EnterTransmitterSN.class);
                startActivity(in);
                finish();
            }
        });
      /*   nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Intent in = new Intent(bluetooth.this, .class);
                // startActivity(in);
                //finish();
               if(ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.BLUETOOTH_CONNECT)== PackageManager.PERMISSION_DENIED) {
                    if(Build.VERSION.SDK_INT>31){
                        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.BLUETOOTH_CONNECT},100);
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
                    bluetoothAdapter.disable();
                }
                else {
                    bluetoothAdapter.isEnabled();
                }
            }
        });*/
        previewView =findViewById(R.id.cameraprew);
        if(ContextCompat.checkSelfPermission(qrcode.this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
            init();
        }
        else {
            ActivityCompat.requestPermissions(qrcode.this,new String[]{Manifest.permission.CAMERA},101);
        }

    }
    public void init(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cameraProviderListenableFuture= ProcessCameraProvider.getInstance(qrcode.this);
        }
        cameraProviderListenableFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    ProcessCameraProvider cameraProvider=cameraProviderListenableFuture.get();
                    bindImageAnalysis(cameraProvider);
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        },ContextCompat.getMainExecutor(qrcode.this));
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            init();
        }
        else{
            Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
        }
    }
    private void bindImageAnalysis(ProcessCameraProvider processCameraProvider){

          ImageAnalysis  imageAnalysis = new ImageAnalysis.Builder().setTargetResolution(new Size(1200,720))
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST).build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(qrcode.this), new ImageAnalysis.Analyzer() {
                //@OptIn(markerClass = ExperimentalGetImage.class) @Override
                public void analyze(@NonNull ImageProxy image) {
                    Image mediaimage= image.getImage();
                    if(mediaimage!=null){
                        InputImage image2=InputImage.fromMediaImage(mediaimage,image.getImageInfo().getRotationDegrees());
                        BarcodeScanner scanner= BarcodeScanning.getClient();
                        Task<List<Barcode>> result=scanner.process(image2);
                        result.addOnSuccessListener(new OnSuccessListener<List<Barcode>>() {
                            @Override
                            public void onSuccess(List<Barcode> barcodes) {
                                for(Barcode barcode:barcodes){
                                    final String getvalue=barcode.getRawValue();
                                    editText.setText(getvalue);
//========================================================================================================================
                                   /* if(getvalue!=null) {
                                        Intent intent1 = new Intent(getApplicationContext(), EnterTransmitterSN.class);
                                        intent1.putExtra("mykey", getvalue);
                                        startActivity(intent1);
                                    }*/
//==========================================================================================================================
                                } image.close();
                                mediaimage.close();

                            }
                        });
                    }
                }
            });
        }

        Preview preview=new Preview.Builder().build();
        CameraSelector cameraSelector = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            cameraSelector = new CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            preview.setSurfaceProvider(previewView.getSurfaceProvider());
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            processCameraProvider.bindToLifecycle(this,cameraSelector,imageAnalysis,preview);
        }
    }
}