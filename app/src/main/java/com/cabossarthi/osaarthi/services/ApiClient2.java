package com.cabossarthi.osaarthi.services;



import static com.cabossarthi.osaarthi.services.Urls.BASE_URL;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.cabossarthi.osaarthi.session.SessionManager;
import com.google.android.datatransport.runtime.dagger.Provides;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ApiClient2 {
    private static Context context;

//save the context recievied via constructor in a local variable

    public ApiClient2(Context context){
        ApiClient2.context =context;
    }
    public static Retrofit getClient() {

        TokenInterceptor interceptor = new TokenInterceptor(context);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        return new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }
}
