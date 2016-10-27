package com.unfpa.safepal.home;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
/**
 * Created by Kisa on 10/4/2016.
 */

public class RotateLayout extends ViewGroup {
    final Random mRandom = new Random();
    private float mRotateAngle;

    private Handler mHandler = new Handler();
    private float rotateAngleDegree;

    /** Constructors for the class**/
    public RotateLayout(Context context) {
        super(context);
    }
    public RotateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public RotateLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 1. Setup initial constraints.
        final float radius = Math.min(getMeasuredWidth(), getMeasuredHeight()) / 2F;
        float halfWidth = getMeasuredWidth() / 2F;
        float halfHeight = getMeasuredHeight() / 2.2F;
        final int count = getChildCount();


        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();

            float sinTheta = (float) Math.sin(lp.theta);

            float x = (int) (radius * Math.cos(lp.fi + mRotateAngle)
                    * sinTheta);

            if (child instanceof TextView) {
                ((TextView) child)
                        .setTextSize(7 * ((radius + x) / radius) + 6);
            }
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            //  http://en.wikipedia.org/wiki/Spherical_coordinates
            lp.x = (int) ((halfWidth + radius * Math.sin(lp.fi + mRotateAngle)
                    * sinTheta) - /* for balancing on x-axis */(child
                    .getMeasuredWidth() / 2F));
            lp.y = (int) (halfHeight + radius * Math.cos(lp.theta)-/* for balancing on y-axis */(child
                    .getMeasuredHeight() / 2F));
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                rotateAngleDegree += 0.1;
                mRotateAngle = (float) Math.toRadians(rotateAngleDegree);
                requestLayout();
                mHandler.postDelayed(this, 0);
            }
        }, 0);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeCallbacksAndMessages(null);
    }




    @Override
    public void addView(View child, int index,
                        android.view.ViewGroup.LayoutParams params) {
        super.addView(child, index, params);

        LayoutParams lp = (LayoutParams) child.getLayoutParams();

        lp.fi = (float) Math.toRadians(mRandom.nextInt(360));
        lp.theta = (float) Math.toRadians(mRandom.nextInt(360));
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            child.layout(lp.x, lp.y, lp.x + child.getMeasuredWidth(), lp.y
                    + child.getMeasuredHeight());
        }
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p.width, p.height);
    }

    public static class LayoutParams extends ViewGroup.LayoutParams {
        int x;
        int y;
        float fi, theta;

        public LayoutParams(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public LayoutParams(int w, int h) {
            super(w, h);
        }
    }




}
