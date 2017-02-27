package com.youngtr.numberprogressbar.example;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.youngtr.numberprogressbar.NumberProgressBar;


public class MainActivity extends AppCompatActivity {

    private NumberProgressBar mHorizontalProgressBar;
    private NumberProgressBar mCircleProgressBar;

    private Handler mTimedHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (mCircleProgressBar.getProgress() <= 100) {
                mCircleProgressBar.setProgress(mCircleProgressBar.getProgress() + 1);
                mHorizontalProgressBar.setProgress(mHorizontalProgressBar.getProgress() + 1);
                mTimedHandler.sendEmptyMessageDelayed(0, 100);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHorizontalProgressBar = (NumberProgressBar) findViewById(R.id.progress_horizontal);
        mCircleProgressBar = (NumberProgressBar) findViewById(R.id.progress_circle);

        mTimedHandler.sendEmptyMessage(0);
    }
}
