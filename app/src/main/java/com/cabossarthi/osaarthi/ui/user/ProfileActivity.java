package com.cabossarthi.osaarthi.ui.user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.cabossarthi.osaarthi.R;
import com.cabossarthi.osaarthi.session.SessionManager;

public class ProfileActivity extends AppCompatActivity {
  TextView name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name=findViewById(R.id.tv_name);
        name.setText(SessionManager.getInstance(getApplicationContext()).getNAME());

    }
}