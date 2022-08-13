package com.cabossarthi.osaarthi.ui.driver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.cabossarthi.osaarthi.R;

public class DriverOtpVerificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_otp_verification);
        findViewById(R.id.btn_otp_login).setOnClickListener(v->{
            startActivity(new Intent(getApplicationContext(),DriverRegistrationActivity.class));
        });
    }
}