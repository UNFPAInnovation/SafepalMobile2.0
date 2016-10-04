package com.unfpa.safepal.home;

/**
 * Created by Kisa on 10/4/2016.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.unfpa.safepal.R;

public class RotateAnotherWay extends View {

    private List<String> mItems = new ArrayList<String>();
    private List<Angles> mAngles = new ArrayList<Angles>();
    private Camera mCamera = new Camera();
    private TextPaint mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    private Handler mHandler = new Handler();
    private float mRotateAngle;
    private float rotateAngleDegree;

    public static class Angles {
        float fi, theta;
    }

    public RotateAnotherWay(Context context) {
        super(context);
        init();
    }

    public RotateAnotherWay(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RotateAnotherWay(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        List<String> items = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            items.add("item:" + i);
        }
        setItems(items);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(canvas.getWidth() / 2F, canvas.getHeight() / 2F);
        mTextPaint.setColor(getResources().getColor(R.color.colorOrange));
        final float radius = 100;

        mCamera.setLocation(0, 0, -100);
        for (int i = 0; i < mItems.size(); i++) {
            String item = mItems.get(i);
            Angles xyz = mAngles.get(i);
            mCamera.save();
            canvas.save();
            float sinTheta = (float) Math.sin(xyz.theta);
            float x = (float) (radius * Math.cos(xyz.fi + mRotateAngle) * sinTheta);
            float y = (float) (radius * Math.sin(xyz.fi + mRotateAngle) * sinTheta);
            float z = (float) (radius * Math.cos(xyz.theta));
            // mapping coordinates with Android's coordinates
            // http://en.wikipedia.org/wiki/Spherical_coordinates
            mCamera.translate(y, z, x);
            mCamera.applyToCanvas(canvas);

            // http://en.wikipedia.org/wiki/Spherical_coordinates
            // set size based on x-Axis that is coming towards us
            mTextPaint.setTextSize(20 * ((100 - x) / 100) + 10);
            canvas.drawText(item, 0, 0, mTextPaint);
            mCamera.restore();
            canvas.restore();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                rotateAngleDegree += 5;
                mRotateAngle = (float) Math.toRadians(rotateAngleDegree);
                invalidate();
                mHandler.postDelayed(this, 40);
            }
        }, 40);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setItems(List<String> items) {
        mItems = items;
        final Random ran = new Random();
        final List<Angles> xyzList = mAngles;
        xyzList.clear();

        for (int i = 0; i < items.size(); i++) {
            Angles xyz = new Angles();
            float fi = (float) Math.toRadians(ran.nextInt(360));
            xyz.fi = fi;
            float theta = (float) Math.toRadians(ran.nextInt(360));
            xyz.theta = theta;
            xyzList.add(xyz);
        }
    }
}