package com.cabossarthi.osaarthi.services;


import com.cabossarthi.osaarthi.model.BookRidePostModal;
import com.cabossarthi.osaarthi.model.BookRideResponseModal;
import com.cabossarthi.osaarthi.model.CustomerBookingListModal;
import com.cabossarthi.osaarthi.model.LoginModel;
import com.cabossarthi.osaarthi.model.LoginMainModel;
import com.cabossarthi.osaarthi.model.MobileRegistrationModel;
import com.cabossarthi.osaarthi.model.MobileVerifyModel;
import com.cabossarthi.osaarthi.model.MobileVerifyResponseModel;
import com.cabossarthi.osaarthi.model.RegistationModel;
import com.cabossarthi.osaarthi.model.RegistrationResponseModel;
import com.cabossarthi.osaarthi.model.SendDataToVehicleApi;
import com.cabossarthi.osaarthi.model.VehicleCatogeryModal;
import com.cabossarthi.osaarthi.model.VehicleCatogeryResponseModal;
import com.cabossarthi.osaarthi.model.VehicleDetailsModal;
import com.cabossarthi.osaarthi.model.VehicleDetailsResponseModal;
import com.cabossarthi.osaarthi.model.VehicleListModal;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServiceApi {
    @POST("Users/login")
    Call<LoginModel> login(@Body LoginMainModel model);

    @POST("Users/registration")
    Call<RegistrationResponseModel> registration(@Body RegistationModel model);

    @POST("Users/mobileverify")
    Call<MobileVerifyResponseModel> mobileverify(@Body MobileVerifyModel model);

    @POST("Users/mobileregistration")
    Call<MobileVerifyResponseModel> mobileregistration(@Body MobileRegistrationModel mobileRegistrationModel);


    @POST("Vehicle/getvehicledetail")
    Call<VehicleListModal>getvehicledetail(@Body MobileRegistrationModel mobileRegistrationModel);

    @GET("Customers/CustomerBookingList/{CustomerID}")
    Call<CustomerBookingListModal>getCustomerBookingList(@Path("CustomerID") String CustomerID);

    @POST("Customers/bookride")
    Call<BookRideResponseModal>bookRide(@Body BookRidePostModal bookRidePostModal);

    @POST("Vehicle/getvehicleCatagory")
    Call<VehicleCatogeryModal>getvehicleCatagory(@Body VehicleCatogeryResponseModal vehicleCatogeryModal);

    @POST("Vehicle/getvehicledetail")
    Call<VehicleDetailsResponseModal>getvehicleDetails(@Body VehicleDetailsModal vehicleDetailsModal);

//    @GET("api/directions/json?key=AIzaSyC22GfkHu9FdgT9SwdCWMwKX1a4aohGifM")
//    Call<Example> getDistanceDuration(@Query("units") String units, @Query("origin") String origin, @Query("destination") String destination, @Query("mode") String mode);
}