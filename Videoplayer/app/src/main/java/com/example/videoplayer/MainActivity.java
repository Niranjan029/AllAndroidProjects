package com.example.videoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VideoView myapp = findViewById(R.id.appvideo);
        myapp.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.bmiapp);
        MediaController mediaController=new MediaController(this);
        myapp.setMediaController(mediaController);
        mediaController.setAnchorView(myapp);
        myapp.start();
    }
}