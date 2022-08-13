package com.cabossarthi.osaarthi.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.cabossarthi.osaarthi.R;
import com.cabossarthi.osaarthi.ui.user.LoginWithOtpActivity;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.skydoves.elasticviews.ElasticButton;

import java.util.List;

public class LoginMainActivity extends AppCompatActivity {
    ElasticButton login_with_otp, login_with_facebook, login_with_google;
    String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_main);
        type=getIntent().getStringExtra("type");
        login_with_otp = findViewById(R.id.btn_otp_login);
        login_with_facebook = findViewById(R.id.btn_facebook_login);
        login_with_google = findViewById(R.id.btn_google_login);
        login_with_otp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginMainActivity.this,LoginWithOtpActivity.class);
            intent.putExtra("type",type);
            startActivity(intent);
        });
        callAllPermissions();
    }

    private void callAllPermissions() {
        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.ANSWER_PHONE_CALLS
                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {

            }


        }).check();
    }
}