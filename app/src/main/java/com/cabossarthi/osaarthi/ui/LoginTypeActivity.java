package com.cabossarthi.osaarthi.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.cabossarthi.osaarthi.R;
import com.cabossarthi.osaarthi.ui.driver.DriverLoginActivity;
import com.skydoves.elasticviews.ElasticButton;
//2131233298
//2131233297
public class LoginTypeActivity extends AppCompatActivity {
    ElasticButton login_as_driver, login_as_user, register;
    String selected_person_type;
    RadioGroup radioGroup;
    RadioButton rbCustomer,rbDriver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_type);
        radioGroup=findViewById(R.id.radio_group);
        register = findViewById(R.id.btn_register);
        login_as_driver = findViewById(R.id.btn_login_as_driver);
        login_as_user = findViewById(R.id.btn_login_as_user);

        login_as_driver.setOnClickListener(v -> {
            selected_person_type = "Driver";
            Intent intent = new Intent(LoginTypeActivity.this, DriverLoginActivity.class);
            intent.putExtra("type", selected_person_type);
            startActivity(intent);
        });
        login_as_user.setOnClickListener(v -> {
            selected_person_type = "Customer";
            Intent intent = new Intent(LoginTypeActivity.this, LoginMainActivity.class);
            intent.putExtra("type", selected_person_type);
            startActivity(intent);
        });
        register.setOnClickListener(v->{
            int selectedId = radioGroup.getCheckedRadioButtonId();
            rbCustomer = findViewById(selectedId);
            rbDriver = findViewById(selectedId);
            Log.e("TAG", "onRadioButtonClicked: "+selectedId);
            if(selectedId==R.id.rb_customer){
                selected_person_type = "Customer";
                Intent intent = new Intent(LoginTypeActivity.this, LoginMainActivity.class);
                intent.putExtra("type", selected_person_type);
                startActivity(intent);
            }
            else if (selectedId==R.id.rb_driver){
                selected_person_type = "Driver";
                Intent intent = new Intent(LoginTypeActivity.this, DriverLoginActivity.class);
                intent.putExtra("type", selected_person_type);
                startActivity(intent);
            }
        });
    }
}
