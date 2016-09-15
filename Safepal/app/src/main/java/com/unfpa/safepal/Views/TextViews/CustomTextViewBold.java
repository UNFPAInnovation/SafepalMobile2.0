package com.unfpa.safepal.Views.TextViews;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Brian on 1/14/2016.
 */
@SuppressWarnings("HardCodedStringLiteral")
public class CustomTextViewBold extends TextView {
    Context context;
    boolean canChangeTypeFace = false;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomTextViewBold(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        this.isInEditMode();
    }

    public CustomTextViewBold(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.isInEditMode();
    }

    public CustomTextViewBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.isInEditMode();
    }

    public CustomTextViewBold(Context context) {
        super(context);
        this.context = context;
        this.isInEditMode();
    }

    @Override
    public void setTypeface(Typeface tf) {
        if( !canChangeTypeFace ){
            tf = Typeface.createFromAsset( getContext().getAssets(), "fonts/RobotoCondensed-Bold.ttf");
        }
        super.setTypeface( tf );
    }

    public void setNewTypeface(Typeface tf) {
        canChangeTypeFace = true;
        super.setTypeface( tf );
    }
}
