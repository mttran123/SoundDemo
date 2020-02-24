package com.example.gohasu.sounddemo;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;

    AudioManager audioManager;

    public void play(View view) {

        mediaPlayer.start();

    }

    public void pause(View view) {
        mediaPlayer.pause();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE); // set up value for audioManager from null

        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC); // find max volume of the device

        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC); // find current volume of the device

        mediaPlayer = MediaPlayer.create(this,R.raw.heavy);

        SeekBar volumeControl = findViewById(R.id.volumeSeekBar);

        volumeControl.setMax(maxVolume);   // set max volume for seek bar like the device
        volumeControl.setProgress(currentVolume);   // set current volume for seek bar like device

        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.i("Seekbar changed", Integer.toString(i));  // i : volume level(default:1-100)

                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, i, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        final SeekBar scrubControl = findViewById(R.id.scrubSeekBar);

        scrubControl.setMax(mediaPlayer.getDuration());  //set maximum length of the audio for scrub seekbar

        scrubControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.i("Scrub Seekbar", Integer.toString(i)); // i: media length

                mediaPlayer.seekTo(i);  //to control playing audio with seek bar
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mediaPlayer.pause();

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                mediaPlayer.start();
            }
        });

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                scrubControl.setProgress(mediaPlayer.getCurrentPosition());  // get current position of audio for seekbar
            }
        }, 0, 300);  //define when to start audio which is now: 0; we want audio to run every second: 300 miliseconds

    }
}
