package com.cabossarthi.osaarthi.ui.driver;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.cabossarthi.osaarthi.R;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.skydoves.elasticviews.ElasticButton;

public class DriverRegistrationActivity extends AppCompatActivity {
    ElasticButton choose1, choose2, choose3, choose4, choose5, choose6, choose7, choose8;
    EditText name, email, password, confirm_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_registration);
        choose1 = findViewById(R.id.btn_choose_aadhaar_front);
        choose2 = findViewById(R.id.btn_choose_aadhaar_back);
        choose3 = findViewById(R.id.btn_choose_driving_licence_image);
        choose4 = findViewById(R.id.btn_choose_your_imamge);
        choose5 = findViewById(R.id.btn_choose_you_insurance_image);
        choose6 = findViewById(R.id.btn_choose_your_pan_card_image);
        choose7 = findViewById(R.id.btn_choose_your_rC_image);
        choose8 = findViewById(R.id.btn_choose_sefie_with_aadhar);
        name=findViewById(R.id.et_name);
        email=findViewById(R.id.et_email);
        password=findViewById(R.id.et_password);
        confirm_password=findViewById(R.id.et_confirm_password);
        choose1.setOnClickListener(v -> {
            ImagePicker.Companion.with(this)
                    .crop()                    //Crop image(Optional), Check Customization for more option
                    .compress(200)            //Final image size will be less than 1 MB(Optional)
                    .maxResultSize(300, 450)    //Final image resolution will be less than 1080 x 1080(Optional)
                    .start();
        });
        choose2.setOnClickListener(v -> {
            ImagePicker.Companion.with(this)
                    .crop()                    //Crop image(Optional), Check Customization for more option
                    .compress(200)            //Final image size will be less than 1 MB(Optional)
                    .maxResultSize(300, 450)    //Final image resolution will be less than 1080 x 1080(Optional)
                    .start();
        });
        choose3.setOnClickListener(v -> {
            ImagePicker.Companion.with(this)
                    .crop()                    //Crop image(Optional), Check Customization for more option
                    .compress(200)            //Final image size will be less than 1 MB(Optional)
                    .maxResultSize(300, 450)    //Final image resolution will be less than 1080 x 1080(Optional)
                    .start();
        });
        choose4.setOnClickListener(v -> {
            ImagePicker.Companion.with(this)
                    .crop()                    //Crop image(Optional), Check Customization for more option
                    .compress(200)            //Final image size will be less than 1 MB(Optional)
                    .maxResultSize(300, 450)    //Final image resolution will be less than 1080 x 1080(Optional)
                    .start();
        });
        choose5.setOnClickListener(v -> {
            ImagePicker.Companion.with(this)
                    .crop()                    //Crop image(Optional), Check Customization for more option
                    .compress(200)            //Final image size will be less than 1 MB(Optional)
                    .maxResultSize(300, 450)    //Final image resolution will be less than 1080 x 1080(Optional)
                    .start();
        });
        choose6.setOnClickListener(v -> {
            ImagePicker.Companion.with(this)
                    .crop()                    //Crop image(Optional), Check Customization for more option
                    .compress(200)            //Final image size will be less than 1 MB(Optional)
                    .maxResultSize(300, 450)    //Final image resolution will be less than 1080 x 1080(Optional)
                    .start();
        });
        choose7.setOnClickListener(v -> {
            ImagePicker.Companion.with(this)
                    .crop()                    //Crop image(Optional), Check Customization for more option
                    .compress(200)            //Final image size will be less than 1 MB(Optional)
                    .maxResultSize(300, 450)    //Final image resolution will be less than 1080 x 1080(Optional)
                    .start();
        });
        choose8.setOnClickListener(v -> {
            ImagePicker.Companion.with(this)
                    .crop()                    //Crop image(Optional), Check Customization for more option
                    .compress(200)            //Final image size will be less than 1 MB(Optional)
                    .maxResultSize(300, 450)    //Final image resolution will be less than 1080 x 1080(Optional)
                    .start();
        });
    }
}