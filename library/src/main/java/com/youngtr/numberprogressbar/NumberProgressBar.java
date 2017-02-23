package com.youngtr.numberprogressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by YoungTr on 2017/2/23.
 */

public class NumberProgressBar extends View {

    private static final int PROGRESS_TEXT_VISIBLE = 0;
    private static final int PROGRESS_SHAP_HORIZONTAL = 0;
    private int mMax;
    private int mProgress;
    private int mReachedBarColor;
    private int mUnreachedBarColor;
    private int mNumberTextColor;
    private float mNumberTextSize;
    private float mReachedBarHeight;
    private float mUnreachedBarHeight;
    private float mTextOffset;
    private float mDrawTextWidth;
    private float mDrawTextStart;
    private float mDrawTextEnd;
    private String mCurrentDrawText;
    private String mSuffix = "%";
    private String mPrefix = "";
    private boolean mIfDrawReachedBar;
    private boolean mIfDrawUnreachedBar;
    private boolean mIfDrawText;
    private boolean mIfShapHorizontal;
    private Paint mReachedPaint;
    private Paint mUnreachedPaint;
    private Paint mTextPaint;


    public NumberProgressBar(Context context) {
        this(context, null);
    }

    public NumberProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initProgressBar();
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.NumberProgressBar);
        setMax(attributes.getInt(R.styleable.NumberProgressBar_maxValue, mMax));
        setProgress(attributes.getInt(R.styleable.NumberProgressBar_currentValue, mProgress));
        mReachedBarColor = attributes.getColor(R.styleable.NumberProgressBar_reachedBarColor, mReachedBarColor);
        mUnreachedBarColor = attributes.getColor(R.styleable.NumberProgressBar_unreachedBarColor, mUnreachedBarColor);
        mNumberTextColor = attributes.getColor(R.styleable.NumberProgressBar_numberTextColor, mNumberTextColor);
        mReachedBarHeight = attributes.getDimension(R.styleable.NumberProgressBar_reachedBarHeight, mReachedBarHeight);
    }

    private void initProgressBar() {
        mMax = 100;
        mProgress = 0;
        mReachedBarColor = Color.rgb(66, 145, 241);
        mNumberTextColor = Color.rgb(66, 145, 241);
        mUnreachedBarColor = Color.rgb(204, 204, 204);
        mReachedBarHeight = 1.5F;
        mUnreachedBarHeight = 1.0F;
        mNumberTextSize = 10.0F;
        mTextOffset = 3.0F;
        mIfDrawReachedBar = true;
        mIfDrawUnreachedBar = true;
        mIfDrawText = true;
        mIfShapHorizontal = true;
    }

    public int getMax() {
        return mMax;
    }

    public void setMax(int max) {
        if (max >= 0) {
            this.mMax = max;
            invalidate();
        }
    }

    public int getProgerss() {
        return mProgress;
    }

    public void setProgress(int progress) {
        if (progress <= getMax() && progress >= 0) {
            this.mProgress = progress;
        }
    }
}
