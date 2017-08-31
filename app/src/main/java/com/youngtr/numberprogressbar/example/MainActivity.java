package com.youngtr.numberprogressbar.example;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.youngtr.numberprogressbar.NumberProgressBar;


public class MainActivity extends AppCompatActivity {

    private NumberProgressBar mHorizontalProgressBarRed;
    private NumberProgressBar mHorizontalProgressBarBlue;
    private NumberProgressBar mHorizontalProgressBarYellow;
    private NumberProgressBar mCircleProgressBarBlue;
    private NumberProgressBar mCircleProgressBarYellow;
    private NumberProgressBar mCircleProgressBarRed;

    private Handler mTimedHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (mHorizontalProgressBarYellow.getProgress() <= 100) {
                int progress = mHorizontalProgressBarYellow.getProgress() + 1;
                mHorizontalProgressBarRed.setProgress(progress);
                mHorizontalProgressBarBlue.setProgress(progress);
                mHorizontalProgressBarYellow.setProgress(progress);
                mCircleProgressBarBlue.setProgress(progress);
                mCircleProgressBarYellow.setProgress(progress);
                mCircleProgressBarRed.setProgress(progress);
                mTimedHandler.sendEmptyMessageDelayed(0, 90);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHorizontalProgressBarRed = (NumberProgressBar) findViewById(R.id.progress_horizontal_red);
        mHorizontalProgressBarBlue = (NumberProgressBar) findViewById(R.id.progress_horizontal_blue);
        mHorizontalProgressBarYellow = (NumberProgressBar) findViewById(R.id.progress_horizontal_yellow);
        mCircleProgressBarBlue = (NumberProgressBar) findViewById(R.id.progress_circle_blue);
        mCircleProgressBarYellow = (NumberProgressBar) findViewById(R.id.progress_circle_yellow);
        mCircleProgressBarRed = (NumberProgressBar) findViewById(R.id.progress_circle_red);

        mTimedHandler.sendEmptyMessageDelayed(0,15000);
    }
}
