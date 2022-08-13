package com.cabossarthi.osaarthi.ui.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.cabossarthi.osaarthi.R;
import com.cabossarthi.osaarthi.adapter.UserBookingListAdapter;
import com.cabossarthi.osaarthi.adapter.VehicleDetailsAdapter;
import com.cabossarthi.osaarthi.model.CustomerBookingListModal;
import com.cabossarthi.osaarthi.model.RegistrationResponseModel;
import com.cabossarthi.osaarthi.services.ApiClient;
import com.cabossarthi.osaarthi.services.ServiceApi;
import com.cabossarthi.osaarthi.session.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserBookingListActivity extends AppCompatActivity {
    RecyclerView userBookingRecycler;
    String TAG;
    UserBookingListAdapter userBookingListAdapter;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_booking_list);
        TAG=this.getClass().getCanonicalName();
        userBookingRecycler = findViewById(R.id.rv_user_bookingList);
        findViewById(R.id.iv_back).setOnClickListener(v->{onBackPressed();});

    }
    private void getBookinglist(){
        ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
        Call<CustomerBookingListModal> call = api.getCustomerBookingList(SessionManager.getInstance(getApplicationContext()).getID());
        call.enqueue(new Callback<CustomerBookingListModal>() {
            @Override
            public void onResponse(@NonNull Call<CustomerBookingListModal> call, @NonNull Response<CustomerBookingListModal> response) {
             if (response.isSuccessful()){
                 userBookingListAdapter = new UserBookingListAdapter(getApplicationContext(), response.body().getCustomerRideLists()
                         , UserBookingListActivity.this, (vehicleCatogeryModal, pos, linearLayout, view, callFunction) -> {

                 });
                 userBookingRecycler.setLayoutManager(new LinearLayoutManager(UserBookingListActivity.this, LinearLayoutManager.VERTICAL, false));
                 userBookingRecycler.setAdapter(userBookingListAdapter);
             }
            }
            @Override
            public void onFailure(@NonNull Call<CustomerBookingListModal> call, @NonNull Throwable t) {
                 call.cancel();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getBookinglist();
    }
}