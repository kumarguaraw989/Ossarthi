package com.cabossarthi.osaarthi.services;

import static com.cabossarthi.osaarthi.OssarthiApplication.JWTTOKEN;
import static com.cabossarthi.osaarthi.OssarthiApplication.getAppContext;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.cabossarthi.osaarthi.OssarthiApplication;
import com.cabossarthi.osaarthi.session.SessionManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {
    // variable to hold context
    private Context context;

//save the context recievied via constructor in a local variable

    public TokenInterceptor(Context context){
        this.context=context;
    }
     @NonNull
     @Override
    public Response intercept(Chain chain) throws IOException {
        //rewrite the request to add bearer token
         Log.e("TAG", "intercept: "+SessionManager.getInstance(getAppContext()).getJWTTOKEN());
         Request newRequest=chain.request().newBuilder()
                .header("Authorization","Bearer"+SessionManager.getInstance(context).getJWTTOKEN())
                .build();
        return chain.proceed(newRequest);
    }
}