package com.project.misterauto;

import android.app.Application;
import android.content.Context;

import com.project.misterauto.Util.Cache;
import com.project.misterauto.network.ApiInterface;
import com.project.misterauto.network.GsonHelper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppController extends Application {

    private static Retrofit retrofit;
    private static ApiInterface service;
    public static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private static Context context;
    public static OkHttpClient okHttpClient;

    public static ApiInterface getAPIService() {
        if(service == null)
            service = retrofit.create(ApiInterface.class);
        return service;
    }



    @Override
    public void onCreate() {
        super.onCreate();

        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        String url = request.url().toString();
                        Request finalRequest = request.newBuilder()
                                .url(url)
                                .tag(null)
                                .build();
                        return chain.proceed(finalRequest);
                    }
                })
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .cache(null)
                .build();


        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonHelper.getGson()))
                .build();

        service = getAPIService();

        context = this;

        Cache.initCache(context);

    }





}



