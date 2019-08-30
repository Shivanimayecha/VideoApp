package com.hd.nature.videoapp.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService  {

    @GET("videos?getvideos=display")
    Call<String> getAllVideos();
}


