package com.AartiSangrah;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;



public class InternetError extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet_error);
        Button retry = findViewById(R.id.btn_retry);

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectionDetector cd = new ConnectionDetector(getApplicationContext());

                if (!cd.isConnectingToInternet()) {
                    Toast.makeText(InternetError.this, "Internet NOT Connected", Toast.LENGTH_LONG).show();

                }
                else
                    startActivity(new Intent(InternetError.this, A2_AartiList.class));
            }
        });
    }
}