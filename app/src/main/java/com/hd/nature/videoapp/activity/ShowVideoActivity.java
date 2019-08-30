package com.hd.nature.videoapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.hd.nature.videoapp.R;
import com.hd.nature.videoapp.model.video_model;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static com.hd.nature.videoapp.R.drawable.pausebutton;
import static com.hd.nature.videoapp.R.drawable.playbutton;

public class ShowVideoActivity extends AppCompatActivity {

    String TAG = "tag";
    String video;
    int idd;
    public ArrayList<video_model> arrayList = new ArrayList<>();
    public VideoView videoView;
    ImageView imageView,share,download;
    SeekBar seekBar;
    boolean isPlay = false;
    Handler handler;
    TextView textView;
    Bitmap bitmap;
    // MediaController mediaController;
    video_model model;
    int duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_video);

        findviews();
        initView();
        updateSeekbar();
        imageClick();
    }

    private void findviews() {

        videoView = findViewById(R.id.video);
        imageView = findViewById(R.id.img_play);
        seekBar = findViewById(R.id.seekbar);
        textView = findViewById(R.id.time);
        share = findViewById(R.id.img_share);
        download = findViewById(R.id.img_download);
    }

    private void initView() {

        // mediaController = new MediaController(ShowVideoActivity.this);
        videoView.setBackgroundColor(Color.TRANSPARENT);
        //video = ;
        String url = getIntent().getStringExtra("video");
        Uri videoUri = Uri.parse(url);

        videoView.setVideoURI(videoUri);
        handler = new Handler();
       // videoView.setVisibility(View.VISIBLE);
        videoView.start();
        isPlay = true;
        imageView.setImageResource(pausebutton);

        MediaPlayer mp = MediaPlayer.create(this, Uri.parse(url));
        duration = mp.getDuration();
        Log.e(TAG, "initView: "+duration );
        mp.release();
        /*convert millis to appropriate time*/

       textView.setText("0:" + String.format("%d",
               TimeUnit.MILLISECONDS.toSeconds(duration) -
                       TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration))
       ));

        shareVideo();




     /*   videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                //mediaPlayer.setLooping(true);
                videoView.requestFocus();
                videoView.start();

                mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i1) {
                        //videoView.setMediaController(mediaController);
                       // mediaController.setAnchorView(videoView);
                    }
                });
            }
        });*/
    }

    private void shareVideo() {

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new ImageShare().execute();
            }
        });
    }

    private class ImageShare extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //  onProgressBar();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            getVideos();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

           // hideProgressBar();
            String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "title", null);
            Uri bitmapUri = Uri.parse(getIntent().getStringExtra("video"));
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("video/*");
            intent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
            startActivity(Intent.createChooser(intent, "Share"));

        }
    }

    private void getVideos() {
        String url = getIntent().getStringExtra("video");
        bitmap = getBitmapFromURL(url);

    }

    private Bitmap getBitmapFromURL(String src) {
        try {
            URL url1 = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    private void updateSeekbar() {

        handler.postDelayed(updateTimeTask,100);


    }
    private void imageClick() {

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isPlay)
                {
                    videoView.pause();
                    isPlay = false;
                    imageView.setImageResource(playbutton);
                }
                else if(isPlay == false)
                {
                    videoView.start();
                    updateSeekbar();
                    isPlay=true;
                    imageView.setImageResource(pausebutton);
                }
            }
        });
    }
    public Runnable updateTimeTask = new Runnable() {
        @Override
        public void run() {

            seekBar.setProgress(videoView.getCurrentPosition());
            seekBar.setMax(videoView.getDuration());
            handler.postDelayed(this,100);

            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                    int x = (int) Math.ceil(i / 1000f);

                    if (x < seekBar.getMax())
                        textView.setText("0:0" + String.format("%d",
                                TimeUnit.MILLISECONDS.toSeconds(duration-i)));
                    else
                        textView.setText("0:00" + String.format("%d",
                                TimeUnit.MILLISECONDS.toMinutes(duration-i)));



                  /*  textView.setText("0:" + String.format("%d",
                            TimeUnit.MILLISECONDS.toSeconds(duration-i))
                    );*/
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                        handler.removeCallbacks(updateTimeTask);
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    handler.removeCallbacks(updateTimeTask);
                    videoView.seekTo(seekBar.getProgress());
                    updateSeekbar();
                   /* textView.setText(String.format("%d sec",
                            TimeUnit.MILLISECONDS.toSeconds(duration)));*/
                }
            });
        }
    };


}
