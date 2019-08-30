package com.hd.nature.videoapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hd.nature.videoapp.R;
import com.hd.nature.videoapp.activity.ShowVideoActivity;
import com.hd.nature.videoapp.model.video_model;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<video_model> mVideos = new ArrayList<>();
    MediaController mediaController;

    public ImageAdapter(Context mContext, ArrayList<video_model> mVideos) {
        this.mContext = mContext;
        this.mVideos = mVideos;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        //public VideoView videoView;
        public ImageView imageView;
        public RelativeLayout relativeLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView =itemView.findViewById(R.id.img_videoImage);
            relativeLayout = itemView.findViewById(R.id.ll);
            //videoView = itemView.findViewById(R.id.video);
        }
    }

    @NonNull
    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.video_layout, parent, false);
        return new ImageAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {


        Glide.with(mContext)
                .load(mVideos.get(position).getImgUrl())
                .into(holder.imageView);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent(mContext, ShowVideoActivity.class);
                in.putExtra("video",mVideos.get(position).getVideoUrl());
               // in.putExtra("video",mVideos.get(position).getVideoUrl());
                mContext.startActivity(in);
            }
        });



      /*  try {
            video_model model = mVideos.get(position);*/

/*            String url = model.getImgUrl();
            Uri videoUri = Uri.parse(url);
            holder.videoView.setVideoURI(videoUri);
            mediaController = new MediaController(mContext);
            mediaController.setAnchorView(holder.videoView);
            holder.videoView.setMediaController(mediaController);
            holder.videoView.setVisibility(View.VISIBLE);*/

         /*   holder.videoView.pause();

            holder.videoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.videoView.isPlaying())
                       holder.videoView.pause();
                    else
                       holder.videoView.start();
                }

            });*/

       /*     holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.setLooping(true);
                    holder.videoView.pause();



                }
            });*/

       /* } catch (Exception e) {
            e.printStackTrace();
        }*/

    }

    @Override
    public int getItemCount() {
        return mVideos.size();
    }


}
