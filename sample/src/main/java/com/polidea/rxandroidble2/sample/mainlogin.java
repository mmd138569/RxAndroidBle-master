package com.polidea.rxandroidble2.sample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ActivityOptions;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.polidea.rxandroidble2.sample.example1_scanning.ScanActivity;

import es.dmoral.toasty.Toasty;

//import es.dmoral.toasty.Toasty;

public class mainlogin extends AppCompatActivity {

    float x1,x2,y1,y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean isFirstTime1 = preferences.getBoolean("isFirstTime1", true);

        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_mainlogin);
        Button login=findViewById(R.id.login);
        EditText username =findViewById(R.id.username);
        EditText password =findViewById(R.id.password);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
                String packageName = getPackageName();
                if(username.getText().length()!=0 && password.getText().length()!=0 ) {
                    if(isFirstTime1) {

                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean("isFirstTime1", false);
                        editor.apply();

                        Intent in = new Intent(mainlogin.this, CGMbasetreatmentDecision.class);
                        ActivityOptions options =
                                ActivityOptions.makeCustomAnimation(mainlogin.this, R.anim.animationint, R.anim.anim);
                        mainlogin.this.startActivity(in, options.toBundle());
                    }
                    else if(!isFirstTime1){
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if(!notificationManager.isNotificationPolicyAccessGranted()){
                                Intent in = new Intent(mainlogin.this, doNotDisturb.class);
                                ActivityOptions options =
                                        ActivityOptions.makeCustomAnimation(mainlogin.this, R.anim.animationint, R.anim.anim);
                                mainlogin.this.startActivity(in, options.toBundle());

                            }
                            else if(!pm.isIgnoringBatteryOptimizations(packageName)){
                             Intent in = new Intent(mainlogin.this, allowAppAlways.class);
                             ActivityOptions options =
                                     ActivityOptions.makeCustomAnimation(mainlogin.this, R.anim.animationint, R.anim.anim);
                             mainlogin.this.startActivity(in, options.toBundle());
                         }
                            else if(ContextCompat.checkSelfPermission(mainlogin.this, Manifest.permission.BLUETOOTH_CONNECT)== PackageManager.PERMISSION_DENIED) {
                                Intent in = new Intent(mainlogin.this, bluetooth.class);
                                ActivityOptions options =
                                        ActivityOptions.makeCustomAnimation(mainlogin.this, R.anim.animationint, R.anim.anim);
                                mainlogin.this.startActivity(in, options.toBundle());
                            }
                         else {
                             Intent in = new Intent(mainlogin.this, EntertransmitterSN1.class);
                             ActivityOptions options =
                                     ActivityOptions.makeCustomAnimation(mainlogin.this, R.anim.animationint, R.anim.anim);
                             mainlogin.this.startActivity(in, options.toBundle());

                         }
                        }
                        // we remove it temperrerley we use it for the latest version
                        
                       /* Intent in = new Intent(mainlogin.this, EntertransmitterSN1.class);
                        ActivityOptions options =
                                ActivityOptions.makeCustomAnimation(mainlogin.this, R.anim.animationint, R.anim.anim);
                        mainlogin.this.startActivity(in, options.toBundle());*/

                        Intent in = new Intent(mainlogin.this, ScanActivity.class);
                        ActivityOptions options =
                                ActivityOptions.makeCustomAnimation(mainlogin.this, R.anim.animationint, R.anim.anim);
                        mainlogin.this.startActivity(in, options.toBundle());
                    }
                    // if(username.getText()== && password.getText()==) {
                    // Intent in = new Intent(mainLogin.this, );
                    //}
                  /*  else{
                        Toast.makeText(mainLogin.this, "username or password is wrong", Toast.LENGTH_SHORT).show();
                    }*/
                }
                else if(username.getText().length()!=0 && password.getText().length()==0){
                    Toasty.error(mainlogin.this, "please enter the password", Toast.LENGTH_SHORT).show();
                }
                else if(username.getText().length()==0 && password.getText().length()!=0){
                    Toasty.error(mainlogin.this, "please enter the username or Email", Toast.LENGTH_SHORT).show();
                }
                else if(username.getText().length()==0 && password.getText().length()==0){
                    Toasty.error(mainlogin.this, "please enter the username and password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public boolean onTouchEvent(MotionEvent Touchevent){
        switch(Touchevent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1=Touchevent.getX();
                y1=Touchevent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2=Touchevent.getX();
                y2=Touchevent.getY();
                if(x1<x2){
                    Intent in = new Intent(mainlogin.this,start.class);
                    ActivityOptions options =
                            ActivityOptions.makeCustomAnimation(mainlogin.this, R.anim.animationint, R.anim.anim);
                    mainlogin.this.startActivity(in, options.toBundle());
                }
                else if(x1>x2){

                }
                break;
        }
        return false;
    }
}