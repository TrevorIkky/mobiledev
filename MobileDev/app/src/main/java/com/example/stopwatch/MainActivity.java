package com.example.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout start;
    RelativeLayout stop;
    RelativeLayout reset;

    TextView hour;
    TextView minute;
    TextView seconds;

    CoordinatorLayout coordinatorLayout;

    Timer timer;
    int s, m, h = 0;

    boolean isRunning = false;
    boolean run;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("MainActivity", "On create");

        if (savedInstanceState != null) {
            h = savedInstanceState.getInt("hours");
            m = savedInstanceState.getInt("minutes");
            s = savedInstanceState.getInt("seconds");
            initializeTimerTask();
        }

        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);
        reset = findViewById(R.id.reset);

        hour = findViewById(R.id.hours);
        minute = findViewById(R.id.minutes);
        seconds = findViewById(R.id.seconds);
        coordinatorLayout = findViewById(R.id.coordinator);

        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        reset.setOnClickListener(this);


    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.d("MainActivity", "Saved state");

        outState.putInt("hours", h);
        outState.putInt("minutes", m);
        outState.putInt("seconds", s);

    }

    private void initializeTimerTask() {
        isRunning = true;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (s < 59) {
                    s++;
                } else {
                    s = 0;
                    if (m < 59) {
                        m++;
                    } else {
                        m = 0;
                        h++;
                    }
                }
                updateTextViews();
            }
        }, 0, 1000);
    }

    private void updateTextViews() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                String sh = h < 10 ? "0".concat(String.valueOf(h)) : String.valueOf(h);
                String sm = m < 10 ? "0".concat(String.valueOf(m)) : String.valueOf(m);
                String ss = s < 10 ? "0".concat(String.valueOf(s)) : String.valueOf(s);
                hour.setText(sh);
                minute.setText(sm);
                seconds.setText(ss);
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                if (!isRunning) {
                    initializeTimerTask();
                    showMessage("Stopwatch started");
                } else {
                    showMessage("Timer is running");
                }
                break;
            case R.id.stop:
                isRunning = false;
                timer.cancel();
                showMessage("Stopwatch stopped");
                break;
            case R.id.reset:
                isRunning = false;
                s = 0;
                m = 0;
                h = 0;
                timer.cancel();
                initializeTimerTask();
                showMessage("Stopwatch reset");
                break;
            default:
        }
    }

    private void showMessage(String message) {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_SHORT).show();
    }
}
