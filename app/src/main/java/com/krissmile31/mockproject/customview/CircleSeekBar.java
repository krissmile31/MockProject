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

import com.krissmile31.mockproject.interfaces.OnCircleSeekBarListener;

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
    private float mIndicatorPosition;
    private OnCircleSeekBarListener mListener;
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
//        mCirclePaint.setColor(Color.GRAY);

//        mCirclePaint.setShader(mGradientColor);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(STROKE_WIDTH/2);
//        mCirclePaint.setStrokeJoin(Paint.Join.ROUND);
//        mCirclePaint.setStrokeCap(Paint.Cap.ROUND);

        mIndicatorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mIndicatorPaint.setShader(mGradientColor);
        mIndicatorPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mCircleProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCircleProgressPaint.setDither(true);
        mCircleProgressPaint.setShader(mGradientColor);
        mCircleProgressPaint.setStyle(Paint.Style.STROKE);
        mCircleProgressPaint.setStrokeWidth(STROKE_WIDTH);
//        mCircleProgressPaint.setStrokeJoin(Paint.Join.ROUND);
//        mCircleProgressPaint.setStrokeCap(Paint.Cap.ROUND);


    }

    private void initPaths() {
        initRectF();
        mCirclePath = new Path();
        mCirclePath.addArc(mCircleRectF, -90, 360);


        mCxIndicator = getWidth()/2;
        mCyIndicator = PADDING;
//        canvas.drawCircle(x, y, INDICATOR_RADIUS, mIndicatorPaint);
//        canvas.drawArc(mCircleRectF, -90, 360, false, mCirclePaint);
//
//        canvas.drawArc(mCircleRectF, -90, mOnProgress * 360/100f, false, mCircleProgressPaint);

        mCircleProgressPath = new Path();
        mCircleProgressPath.addArc(mCircleRectF, -90, mOnProgress * 360/100f);

//        mIndicatorPath = new Path();
//        mIndicatorPath.addCircle(x, y, (float)INDICATOR_RADIUS, Path.Direction.CW);
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

        mGradientColor = new LinearGradient(0, 0, 0, getHeight(), mColorsShader, mPositionsShader, Shader.TileMode.MIRROR);

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
//        changePathIndicator(canvas);
//        changePathIndicator();

//        x = getWidth()/2;
//        y = PADDING;
//        canvas.drawCircle(x, y, INDICATOR_RADIUS, mIndicatorPaint);
//        canvas.drawArc(mCircleRectF, -90, 360, false, mCirclePaint);
//
//        canvas.drawArc(mCircleRectF, -90, mOnProgress * 360/100f, false, mCircleProgressPaint);


//        canvas.drawPath(mIndicatorPath, mIndicatorPaint);
        canvas.drawCircle(mCxIndicator, mCyIndicator, INDICATOR_RADIUS, mIndicatorPaint);
//        canvas.drawCircle(mPointerPositionXY[0], mPointerPositionXY[1], INDICATOR_RADIUS, mIndicatorPaint);

        changePathIndicator();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

//        // center of circle
//        float distanceX = mCircleRectF.centerX() - (event.getX() - getWidth()/2);
//        float distanceY = mCircleRectF.centerY() - (event.getY() - getHeight()/2);
//
//        float distanceSquare = (float) (Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
//        // distance -> radius
//        float touchEventRadius = (float) Math.sqrt(distanceSquare);
//
////        float size = Math.max(mCircleWidth, mCircleHeight);
//        float outerRadius = Math.max(mCircleWidth, mCircleHeight) + 5;
//        float innerRadius = Math.min(mCircleWidth, mCircleHeight) - 5;

        // distance from center of circle to touch point
        float disX = mCircleRectF.centerX() - event.getX();
        float disY = mCircleRectF.centerY() - event.getY();

        mProgressDegrees = (float) Math.toDegrees(Math.atan(disY / disX));
        mProgressDegrees = (disX > 0 ? mProgressDegrees + 270 : mProgressDegrees + 90);


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e("TAG", "onTouchEvent: down");


                mOnProgress = (100 * mProgressDegrees/360);

                invalidate();
//                mCircleProgressPath.moveTo(event.getX(), event.getY());
//                if (touchEventRadius >= outerRadius && touchEventRadius <= innerRadius) {
////                    getProgressDegrees();
//                    setProgress(90);
//                    changePathIndicator();
//                    invalidate();
////                    invalidate();
//                    mListener.onProgressChange(this);
//
//                }



                break;

            case MotionEvent.ACTION_MOVE:
//                mCircleProgressPath.lineTo(event.getX(), event.getY());

                Log.e("TAG", "onTouchEvent: move");

                mOnProgress = (100 * mProgressDegrees/360);

                invalidate();


//                mListener.onProgressChange(this);



                break;

            case MotionEvent.ACTION_UP:
                Log.e("TAG", "onTouchEvent: up");


                break;
        }
        return true;

    }

//    //percent
//    public void setProgress(float progress) {
//        mOnProgress = progress;
//        Log.d("", "setProgress: " + progress);
//
////        changePathIndicator();
//        invalidate();
//    }

    public void changePathIndicator(){

        mCxIndicator = (float) (mCircleRectF.centerX() + (mCircleRadius - INDICATOR_RADIUS + STROKE_WIDTH/2) * Math.cos(Math.toRadians(-90 + mOnProgress * 360 /100f)));
        mCyIndicator = (float) (mCircleRectF.centerY() + (mCircleRadius - INDICATOR_RADIUS + STROKE_WIDTH/2) * Math.sin(Math.toRadians(-90 + mOnProgress * 360/100f)));

    }

}
