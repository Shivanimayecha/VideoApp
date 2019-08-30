package com.hd.nature.videoapp.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.VideoView;

import androidx.annotation.NonNull;

import com.hd.nature.videoapp.R;
import com.hd.nature.videoapp.model.video_model;

import java.util.List;

/*public class adapterVideo extends ArrayAdapter<video_model> {

    private Context mContext;
    private List<video_model> mVideos;

    public adapterVideo(@NonNull Context context, @NonNull List<video_model> objects) {
        super(context, R.layout.video_layout, objects);

        mContext = context;
        mVideos = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;


        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.video_layout, null);
            holder = new ViewHolder();

            holder.videoView = (VideoView) convertView
                    .findViewById(R.id.video);

            convertView.setTag(holder);
        } else {

            holder = (ViewHolder) convertView.getTag();

        }

        *//***get clicked view and play video url at this position**//*
        try {
            video_model video = mVideos.get(position);
            //play video using android api, when video view is clicked.
            String url = video.getVideoUrl(); // your URL here
            Uri videoUri = Uri.parse(url);
            holder.videoView.setVideoURI(videoUri);
            holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setLooping(true);
                    holder.videoView.start();
                }
            });




        } catch (Exception e) {
            e.printStackTrace();
        }


        return convertView;
    }

    public static class ViewHolder {
        VideoView videoView;

    }
}*/
