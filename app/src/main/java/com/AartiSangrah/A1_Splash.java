package com.AartiSangrah;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class A1_Splash extends AppCompatActivity {
    private BroadcastReceiver ConnectionReceiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a1_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                Intent intent = new Intent(A1_Splash.this, A2_AartiList.class);
                startActivity(intent);
                finish();
//                Intent intent = new Intent(A1_Splash.this, A2_AartiList.class);
//                startActivity(intent);
            }
        }, 2000);

    }


}