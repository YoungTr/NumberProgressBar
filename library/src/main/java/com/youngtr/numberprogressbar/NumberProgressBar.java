package com.youngtr.numberprogressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by YoungTr on 2017/2/23.
 */

public class NumberProgressBar extends View {

    private static final int PROGRESS_TEXT_VISIBLE = 0;
    private static final int PROGRESS_SHAPE_HORIZONTAL = 0;

    float mMinWidth;
    float mMinHeight;

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
    private boolean mIfShapeHorizontal;

    private Paint mReachedBarPaint;
    private Paint mUnreachedBarPaint;
    private Paint mTextPaint;

    private RectF mReachedRecF = new RectF();
    private RectF mUnreachedRecF = new RectF();

    public NumberProgressBar(Context context) {
        this(context, null);
    }

    public NumberProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initProgressBar();
        final TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.NumberProgressBar);
        setMax(attributes.getInt(R.styleable.NumberProgressBar_maxValue, mMax));
        setProgress(attributes.getInt(R.styleable.NumberProgressBar_currentValue, mProgress));
        mMinWidth = attributes.getDimension(R.styleable.NumberProgressBar_minWidth, mMinWidth);
        mMinHeight = attributes.getDimension(R.styleable.NumberProgressBar_minHeight, mMinHeight);
        mReachedBarColor = attributes.getColor(R.styleable.NumberProgressBar_reachedBarColor, mReachedBarColor);
        mUnreachedBarColor = attributes.getColor(R.styleable.NumberProgressBar_unreachedBarColor, mUnreachedBarColor);
        mNumberTextColor = attributes.getColor(R.styleable.NumberProgressBar_numberTextColor, mNumberTextColor);
        mReachedBarHeight = attributes.getDimension(R.styleable.NumberProgressBar_reachedBarHeight, mReachedBarHeight);
        mUnreachedBarHeight = attributes.getDimension(R.styleable.NumberProgressBar_unreachedBarHeight, mUnreachedBarHeight);
        mNumberTextSize = attributes.getDimension(R.styleable.NumberProgressBar_numberTextSize, mNumberTextSize);
        mNumberTextColor = attributes.getColor(R.styleable.NumberProgressBar_numberTextColor, mNumberTextColor);
        mTextOffset = attributes.getDimension(R.styleable.NumberProgressBar_textOffset, mTextOffset);
        int textVisibility = attributes.getInt(R.styleable.NumberProgressBar_numberTextVisibility, PROGRESS_TEXT_VISIBLE);
        if (textVisibility != PROGRESS_TEXT_VISIBLE) {
            mIfDrawText = false;
        }
        int progressShape = attributes.getInt(R.styleable.NumberProgressBar_progressBarShape, PROGRESS_SHAPE_HORIZONTAL);
        if (progressShape != PROGRESS_SHAPE_HORIZONTAL) {
            mIfShapeHorizontal = false;
        }
        attributes.recycle();
        initPaints();
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
        mIfShapeHorizontal = true;
        mMinWidth = 24;
        mMinHeight = 24;
    }

    private void initPaints() {
        mReachedBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mReachedBarPaint.setColor(mReachedBarColor);

        mUnreachedBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mUnreachedBarPaint.setColor(mUnreachedBarColor);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(mNumberTextColor);
        mTextPaint.setTextSize(mNumberTextSize);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int dw;
        int dh;
        if (mIfShapeHorizontal) {
            dw = (int) Math.max(mMinWidth, mNumberTextSize);
            dh = (int) Math.max(mMinHeight, Math.max(mNumberTextSize, Math.max(mReachedBarHeight, mUnreachedBarHeight)));
        } else {
            dw = (int) Math.max(mMinWidth, mNumberTextSize);
            dh = (int) Math.max(mMinHeight, mNumberTextSize);
        }

        dw += getPaddingLeft() + getPaddingRight();
        dh += getPaddingTop() + getPaddingBottom();

        final int measureWidth = resolveSizeAndState(dw, widthMeasureSpec, 0);
        final int measureHeight = resolveSizeAndState(dh, heightMeasureSpec, 0);
        setMeasuredDimension(measureWidth, measureHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mIfShapeHorizontal) {
            calculateHorizontal();
        } else {
            calculateCircle();
        }

        if (mIfDrawUnreachedBar) {
            drawUnreachedBar(canvas);
        }

        if (mIfDrawReachedBar) {
            drawReachedBar(canvas);
        }

        if (mIfDrawText) {
            dealWithDrawText();
            drawText(canvas);
        }
    }


    private void drawReachedBar(Canvas canvas) {
        if (mIfShapeHorizontal) {
            canvas.drawRect(mReachedRecF, mReachedBarPaint);
        } else {
            mReachedBarPaint.setStrokeWidth(mReachedBarHeight);
            mReachedBarPaint.setStyle(Paint.Style.STROKE);
            float sweepAngle = getProgress() * 360 / getMax() * 1.0f;
            canvas.drawArc(mReachedRecF, -90, sweepAngle, false, mReachedBarPaint);
        }
    }


    private void calculateHorizontal() {
        if (mIfDrawText) {
            calculateHorizontalDrawTextRectF();
        } else {
            calculateHorizontalWithoutTextRectF();
        }
    }

    private void calculateHorizontalDrawTextRectF() {
        dealWithDrawText();
        if (getProgress() == 0) {
            mIfDrawReachedBar = false;
            mDrawTextStart = getPaddingLeft();
        } else {
            mIfDrawReachedBar = true;
            mReachedRecF.left = getPaddingLeft();
            mReachedRecF.top = getHeight() / 2 - mReachedBarHeight / 2;
            mReachedRecF.right = (getWidth() - getPaddingLeft() - getPaddingRight()) / (getMax() * 1.0f) * getProgress() - mTextOffset + getPaddingLeft();
            mReachedRecF.bottom = getHeight() / 2 + mReachedBarHeight / 2;
            mDrawTextStart = mReachedRecF.right + mTextOffset;
        }
        mDrawTextEnd = getHeight() / 2 - (mTextPaint.descent() + mTextPaint.ascent()) / 2;
        if ((mDrawTextStart + mDrawTextWidth) >= getWidth() - getPaddingRight()) {
            mDrawTextStart = getWidth() - getPaddingRight() - mDrawTextWidth;
            mReachedRecF.right = mDrawTextStart - mTextOffset;
        }
        float unreachedBarStart = mDrawTextStart + mDrawTextWidth + mTextOffset;
        if (unreachedBarStart >= getWidth() - getPaddingRight()) {
            mIfDrawUnreachedBar = false;
        } else {
            mIfDrawUnreachedBar = true;
            mUnreachedRecF.left = unreachedBarStart;
            mUnreachedRecF.top = getHeight() / 2.0f - mUnreachedBarHeight / 2.0f;
            mUnreachedRecF.right = getWidth() - getPaddingRight();
            mUnreachedRecF.bottom = getHeight() / 2.0f + mUnreachedBarHeight / 2.0f;
        }
    }

    private void dealWithDrawText() {
        mCurrentDrawText = mPrefix + String.format("%d", getProgress() * 100 / getMax()) + mSuffix;
        mDrawTextWidth = mTextPaint.measureText(mCurrentDrawText);
    }

    private void calculateHorizontalWithoutTextRectF() {
        mReachedRecF.left = getPaddingLeft();
        mReachedRecF.top = getHeight() / 2.0f - (mTextPaint.ascent() + mTextPaint.descent()) / 2.0f;
        mReachedRecF.right = (getWidth() - getPaddingLeft() - getPaddingRight()) / (getMax() * 1.0f) * getProgress() + getPaddingLeft();
        mReachedRecF.bottom = getHeight() / 2.0f + mReachedBarHeight / 2.0f;
    }

    private void calculateCircle() {
        mIfDrawUnreachedBar = true;
        if (getWidth() >= getHeight()) {
            mReachedRecF.left = getWidth() / 2 - getHeight() / 2 + mUnreachedBarHeight;
            mReachedRecF.top = getPaddingTop() + mUnreachedBarHeight;
            mReachedRecF.right = getWidth() / 2 + getHeight() / 2 - mUnreachedBarHeight;
            mReachedRecF.bottom = getHeight() - getPaddingBottom() - mUnreachedBarHeight;
        } else {
            mReachedRecF.left = getPaddingLeft() + mUnreachedBarHeight;
            mReachedRecF.top = getHeight() / 2 - getWidth() / 2 + mUnreachedBarHeight;
            mReachedRecF.right = getWidth() - getPaddingRight() - mUnreachedBarHeight;
            mReachedRecF.bottom = getHeight() / 2 + getWidth() / 2 - mUnreachedBarHeight;
        }
    }

    private void drawUnreachedBar(Canvas canvas) {
        if (mIfShapeHorizontal) {
            canvas.drawRect(mUnreachedRecF, mUnreachedBarPaint);
        } else {
            mUnreachedBarPaint.setStyle(Paint.Style.STROKE);
            mUnreachedBarPaint.setStrokeWidth(mUnreachedBarHeight);
            canvas.drawArc(mReachedRecF, -90, 360, false, mUnreachedBarPaint);
        }
    }

    private void drawText(Canvas canvas) {
        if (mIfShapeHorizontal) {
            canvas.drawText(mCurrentDrawText, mDrawTextStart, mDrawTextEnd, mTextPaint);
        } else {
            mTextPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(mCurrentDrawText, getWidth() / 2.0f, getHeight() / 2.0f - (mTextPaint.descent() + mTextPaint.ascent()) / 2.0f, mTextPaint);
        }
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

    public int getProgress() {
        return mProgress;
    }

    public void setProgress(int progress) {
        if (progress <= getMax() && progress >= 0) {
            this.mProgress = progress;
            invalidate();
        }
    }
}
