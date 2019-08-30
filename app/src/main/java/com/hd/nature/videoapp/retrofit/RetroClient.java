package com.hd.nature.videoapp.retrofit;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class RetroClient {

    private static String url = "http://techvirtualgames.com/krsn_app/Video_api/";


    public static Retrofit getRetrofitInstance()
    {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        return new Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient)
                .addConverterFactory(new ToStringConverterFactory())
                .build();
    }

    public static ApiService getApiService()
    {
        return getRetrofitInstance().create(ApiService.class);
    }
}
