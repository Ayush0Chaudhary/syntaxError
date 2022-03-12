package com.example.planit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Time extends AppCompatActivity {


    private TextView countDownText;
    private ImageView pause;
    private ImageView play;
    private long timeLeftInMilliecond= 600000; //10 min

    private boolean timerRunning ;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        countDownText = findViewById(R.id.textView2);

        play = findViewById(R.id.imageView3);



        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startStopTime();
            }
        });
    }

    public void startStopTime(){
        if (timerRunning){
            stopTimer();
        }
        else startTimer();

    }

    private void startTimer() {
        timer = new CountDownTimer(timeLeftInMilliecond, 1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMilliecond = l;
                updateTimer();
            }

            @Override
            public void onFinish() {

            }
        }.start();
        play.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24);
        timerRunning =true;
    }

    private void updateTimer() {
    int minute =(int) timeLeftInMilliecond/60000;
    int second = (int) timeLeftInMilliecond%60000 /1000;

    String timeLeftText;

    timeLeftText = "" + minute;
    timeLeftText+=":";

    if(second<10) timeLeftText+="0";
    timeLeftText += second;

    countDownText.setText(timeLeftText);

    }

    private void stopTimer() {

        timer.cancel();
        play.setImageResource(R.drawable.ic_baseline_play_circle_outline_24);
        timerRunning =false;
    }


}