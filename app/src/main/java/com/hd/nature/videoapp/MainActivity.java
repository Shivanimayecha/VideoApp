package com.hd.nature.videoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.hd.nature.videoapp.adapter.ImageAdapter;

import com.hd.nature.videoapp.comman.NetworkConnection;
import com.hd.nature.videoapp.model.video_model;
import com.hd.nature.videoapp.retrofit.ApiService;
import com.hd.nature.videoapp.retrofit.RetroClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    public ArrayList<video_model> video_modelsList = new ArrayList<>();
    ImageAdapter adapter;
    Activity activity;
    String TAG = "TAG";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = MainActivity.this;
        findviews();
        initviews();
    }


    private void findviews() {
        recyclerView = findViewById(R.id.rv);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initviews() {

        if (NetworkConnection.isNetworkAvailable(activity))

        {
            try
            {
                ApiService api = RetroClient.getApiService();
                Call<String> call = api.getAllVideos();
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        Log.e(TAG, "onResponse: "+response.body() );
                        if (response.body()!=null)
                        {
                            parseResponse(response.body(),"");
                        }else
                        {
                            //writesomethingwrong
                        }
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }else {
            Toast.makeText(activity, "Please check your network connection", Toast.LENGTH_SHORT).show();
        }

    }

    private void parseResponse(String body, String s) {

        try
        {
            JSONArray jsonArray = new JSONArray(body);
            for(int i = 0;i<5;i++)
            {
                video_model videoModel = new video_model();
                JSONObject jsonObject = (JSONObject)jsonArray.get(i);
                videoModel.setV_id(jsonObject.optString("V_Id"));
                videoModel.setVideoName(jsonObject.optString("video_name"));
                videoModel.setVideoUrl(jsonObject.optString("videourl"));
                videoModel.setCat_id(jsonObject.optString("category_tag"));
                videoModel.setImgUrl(jsonObject.optString("imageurl"));
                video_modelsList.add(videoModel);

            }
            adapter=new ImageAdapter(MainActivity.this,video_modelsList);
            recyclerView.setAdapter(adapter);

        }catch (JSONException e)
        {
            e.printStackTrace();
        }


    }

}
