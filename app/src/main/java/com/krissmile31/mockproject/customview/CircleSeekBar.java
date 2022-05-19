package com.krissmile31.mockproject.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class CircleSeekBar extends View {
    private Paint mCirclePaint, mIndicatorPaint, mCircleProgressPaint;
    private Shader mGradientColor;
    private int[] mColorsShader;
    private float[] mPositionsShader;
    private Path mCirclePath, mCircleProgressPath, mIndicatorPath;
    private float mProgressDegrees;
    private RectF mCircleRectF;
    private float mCircleWidth, mCircleHeight;
    private float mCircleRadius;
    private float mOnProgress;
    private float mCxIndicator, mCyIndicator;

    private final int START_COLOR = Color.parseColor("#EC8472");
    private final int CENTER_COLOR = Color.parseColor("#DA549A");
    private final int END_COLOR = Color.parseColor("#D9519D");
    private final int CIRCLE_COLOR = Color.TRANSPARENT;
    private final int PADDING = 30;
    private final int INDICATOR_RADIUS = 20;
    private final int STROKE_WIDTH = 10;

    public CircleSeekBar(Context context) {
        super(context);
        initRectF();
        initPaints();
        initPaths();
    }

    public CircleSeekBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initRectF();
        initPaints();
        initPaths();
    }

    private void initPaints() {
        setGradientColor();

        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setDither(true);
        mCirclePaint.setColor(CIRCLE_COLOR);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(STROKE_WIDTH/2);

        mIndicatorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mIndicatorPaint.setShader(mGradientColor);
        mIndicatorPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mCircleProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCircleProgressPaint.setDither(true);
        mCircleProgressPaint.setShader(mGradientColor);
        mCircleProgressPaint.setStyle(Paint.Style.STROKE);
        mCircleProgressPaint.setStrokeWidth(STROKE_WIDTH);

    }

    private void initPaths() {
        initRectF();
        mCirclePath = new Path();
        mCirclePath.addArc(mCircleRectF, -90, 360);

        mCxIndicator = getWidth()/2;
        mCyIndicator = PADDING;

        mCircleProgressPath = new Path();
        mCircleProgressPath.addArc(mCircleRectF, -90, mOnProgress * 360/100f);
    }

    private void initRectF() {
        mCircleRectF = new RectF();
        mCircleWidth = getWidth()-PADDING;
        mCircleHeight = getHeight()-PADDING;

        mCircleRadius = mCircleWidth/2;

        mCircleRectF.set(PADDING, PADDING, mCircleWidth, mCircleHeight);

    }

    private void setGradientColor() {
        mColorsShader = new int[]{START_COLOR, CENTER_COLOR, END_COLOR};
        mPositionsShader = new float[]{0, 1, 0};

        mGradientColor = new LinearGradient(0, 0, 0, getHeight(),
                mColorsShader, mPositionsShader, Shader.TileMode.MIRROR);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int measuredWidth = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int measuredHeight = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        int size = Math.min(measuredWidth, measuredHeight);
        setMeasuredDimension(size, size);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        initPaths();
        changePathIndicator();
        canvas.drawPath(mCirclePath, mCirclePaint);
        canvas.drawPath(mCircleProgressPath, mCircleProgressPaint);
        canvas.drawCircle(mCxIndicator, mCyIndicator, INDICATOR_RADIUS, mIndicatorPaint);

        changePathIndicator();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // distance from center of circle to touch point
        float disX = mCircleRectF.centerX() - event.getX();
        float disY = mCircleRectF.centerY() - event.getY();

        mProgressDegrees = (float) Math.toDegrees(Math.atan(disY / disX));
        mProgressDegrees = (disX > 0 ? mProgressDegrees + 270 : mProgressDegrees + 90);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e("TAG", "onTouchEvent: down");
                mOnProgress = (100 * mProgressDegrees / 360);
                invalidate();
                break;

            case MotionEvent.ACTION_MOVE:
                Log.e("TAG", "onTouchEvent: move");
                mOnProgress = (100 * mProgressDegrees / 360);
                invalidate();
                break;

            case MotionEvent.ACTION_UP:
                Log.e("TAG", "onTouchEvent: up");
                break;
        }
        return true;
    }

    //percent
    public void setProgress(float progress) {
        mOnProgress = progress;
        Log.d("", "setProgress: " + progress);
        invalidate();
    }

    public void changePathIndicator(){
        mCxIndicator = (float) (mCircleRectF.centerX()
                + (mCircleRadius - INDICATOR_RADIUS + STROKE_WIDTH/2)
                * Math.cos(Math.toRadians(-90 + mOnProgress * 360 /100f)));

        mCyIndicator = (float) (mCircleRectF.centerY()
                + (mCircleRadius - INDICATOR_RADIUS + STROKE_WIDTH/2)
                * Math.sin(Math.toRadians(-90 + mOnProgress * 360/100f)));

    }

}
