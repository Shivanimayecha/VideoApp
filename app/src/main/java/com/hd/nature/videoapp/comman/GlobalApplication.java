package com.hd.nature.videoapp.comman;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

public class GlobalApplication extends MultiDexApplication {

    /*public PublisherInterstitialAd mInterstitialAd;
    public PublisherAdRequest ins_adRequest;*/


   // public static SharePrefs sharePrefs;
    private static final String TAG = "Application";

    private static GlobalApplication appInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        appInstance = this;
        MultiDex.install(this);
        //TODO to solve camera issue

      /*  sharePrefs = new SharePrefs(getApplicationContext());

        LoadAds();*/

    }

    public static synchronized GlobalApplication getInstance() {
        return appInstance;
    }
}