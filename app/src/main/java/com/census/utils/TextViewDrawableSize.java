package com.census.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.core.graphics.drawable.DrawableCompat;
import androidx.databinding.BindingMethod;
import androidx.databinding.BindingMethods;

import com.census.R;


/**
 * This widget is useful if you want to adjust/resize the drawable icons of TextView and change icon color.
 * TODO: Vector drawables don't support setBounds yet, so, prefer to convert this class to a Compound view that will have a ImageView and TextView.
 */
@BindingMethods({
        @BindingMethod(type = TextViewDrawableSize.class, attribute = "drawableRight", method = "setDrawableRight"),
        @BindingMethod(type = TextViewDrawableSize.class, attribute = "android:drawableLeft", method = "setDrawableLeft")
})
public class TextViewDrawableSize extends androidx.appcompat.widget.AppCompatTextView {

    private int mDrawableWidth;
    private int mDrawableHeight;
    private int mDrawableColor;
    private boolean mBoundDrawable;

    public TextViewDrawableSize(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public TextViewDrawableSize(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public TextViewDrawableSize(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TextViewDrawableSize, defStyleAttr, defStyleRes);

        try {
            mDrawableWidth = array.getDimensionPixelSize(R.styleable.TextViewDrawableSize_compoundDrawableWidth, -1);
            mDrawableHeight = array.getDimensionPixelSize(R.styleable.TextViewDrawableSize_compoundDrawableHeight, -1);
            mDrawableColor = array.getColor(R.styleable.TextViewDrawableSize_compoundDrawableColor, Integer.MAX_VALUE);
            mBoundDrawable = array.getBoolean(R.styleable.TextViewDrawableSize_boundDrawable, true);
        } finally {
            array.recycle();
        }

        if (mDrawableWidth > 0 || mDrawableHeight > 0 || mDrawableColor != Integer.MAX_VALUE) {
            initCompoundDrawableSize();     // top,left,right,bottom
            //initCompoundRelativeDrawableSize(); // top,start,end,bottom
        }
    }

    public void setDrawableRight(Drawable drawableRight) {
        if (drawableRight != null) {
            drawableRight.setBounds(0, 0, mDrawableWidth, mDrawableHeight);
            setCompoundDrawables(null, null, drawableRight, null);
        }
    }

    public void setDrawableLeft(Drawable drawableLeft) {
        if (drawableLeft != null) {
            drawableLeft.setBounds(0, 0, mDrawableWidth, mDrawableHeight);
            setCompoundDrawables(drawableLeft, null, null, null);
        }
    }

    /**
     * This function is resizing the compound drawables.
     * It changes drawable color too if required.
     */
    private void initCompoundDrawableSize() {
        Drawable[] drawables = getCompoundDrawables();
        for (Drawable drawable : drawables) {
            if (drawable == null) {
                continue;
            }
            if (mDrawableColor != Integer.MAX_VALUE)
                drawable.mutate().setColorFilter(mDrawableColor, PorterDuff.Mode.SRC_ATOP);
            Rect realBounds = drawable.getBounds();
            checkPointF(mBoundDrawable, realBounds, drawable);
        }
        setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
    }

    private void initCompoundRelativeDrawableSize() {
        Drawable[] drawables = getCompoundDrawablesRelative();
        for (Drawable drawable : drawables) {
            if (drawable == null) {
                continue;
            }
            if (mDrawableColor != Integer.MAX_VALUE)
                DrawableCompat.setTint(drawable, mDrawableColor);
            // drawable.mutate().setColorFilter(mDrawableColor, PorterDuff.Mode.SRC_ATOP);
            Rect realBounds = drawable.getBounds();
            checkPointF(mBoundDrawable, realBounds, drawable);
        }
        setCompoundDrawablesRelative(drawables[0], drawables[1], drawables[2], drawables[3]);
    }

    private void checkPointF(boolean mBoundDrawable, Rect realBounds, Drawable drawable) {
        PointF pointF;
        float scaleFactor = realBounds.height() / (float) realBounds.width();
        float drawableWidth = realBounds.width();
        float drawableHeight = realBounds.height();
        if (mBoundDrawable) {
            pointF = calculateDimesToBoundDrawable(drawableWidth, drawableHeight, scaleFactor);
        } else {
            pointF = calculateDimensToNotBoundDrawable(drawableWidth, drawableHeight, scaleFactor);
        }
        drawableWidth = pointF.x;
        drawableHeight = pointF.y;
        realBounds.right = realBounds.left + Math.round(drawableWidth);
        realBounds.bottom = realBounds.top + Math.round(drawableHeight);
        drawable.setBounds(realBounds);
    }

    private PointF calculateDimesToBoundDrawable(float drawableWidth, float drawableHeight, float scaleFactor) {
        if (mDrawableWidth > 0 && drawableWidth > mDrawableWidth) {
            // save scale factor of image
            drawableWidth = mDrawableWidth;
            drawableHeight = drawableWidth * scaleFactor;
        }
        if (mDrawableHeight > 0 && drawableHeight > mDrawableHeight) {
            // save scale factor of image
            drawableHeight = mDrawableHeight;
            drawableWidth = drawableHeight / scaleFactor;
        }
        return new PointF(drawableWidth, drawableHeight);

    }

    private PointF calculateDimensToNotBoundDrawable(float drawableWidth, float drawableHeight, float scaleFactor) {
        if (mDrawableWidth > 0) {
            // save scale factor of image
            drawableWidth = mDrawableWidth;
            drawableHeight = drawableWidth * scaleFactor;
        }
        if (mDrawableHeight > 0) {
            // save scale factor of image

            drawableHeight = mDrawableHeight;
            drawableWidth = drawableHeight / scaleFactor;
        }
        return new PointF(drawableWidth, drawableHeight);
    }

}