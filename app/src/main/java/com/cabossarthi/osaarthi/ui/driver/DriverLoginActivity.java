package com.cabossarthi.osaarthi.ui.driver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.cabossarthi.osaarthi.R;

public class DriverLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login);
        findViewById(R.id.btn_otp_login).setOnClickListener(v->{
            startActivity(new Intent(getApplicationContext(),DriverOtpVerificationActivity.class));

        });
    }
}