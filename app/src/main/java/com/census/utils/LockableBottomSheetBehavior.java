package com.census.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class LockableBottomSheetBehavior<V extends View> extends BottomSheetBehavior<V> {
    private boolean mAllowUserDragging = true;
    private float initY;

    public LockableBottomSheetBehavior() {
    }

    /**
     * Default constructor for inflating BottomSheetBehaviors from layout.
     *
     * @param context The {@link Context}.
     * @param attrs   The {@link AttributeSet}.
     */
    public LockableBottomSheetBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean isAllowUserDragging() {
        return mAllowUserDragging;
    }

    public void setAllowUserDragging(boolean allowUserDragging) {
        mAllowUserDragging = allowUserDragging;
    }

    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, V child, MotionEvent event) {
        Log.d("usm_sheet_lock_drag_1", "onInterceptTouchEvent: allowUserDragging= " + mAllowUserDragging + " ,action= " + event.getAction() + " ,isAbove= " + isAboveThreshold(event));

        if (!mAllowUserDragging && isAboveThreshold(event)) {
            /*
             * !mAllowUserDragging && isAboveThreshold(event) means that now dragging of BottomSheet
             * actually starts.
             */
            
            return false;
        }
        return super.onInterceptTouchEvent(parent, child, event);
    }

    private boolean isAboveThreshold(MotionEvent event) {
        boolean isAbove = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float diffY = Math.abs(event.getY() - initY);

                isAbove = diffY > ScreenUtils.dpToPx(5);

                Log.d("usm_sheet_lock_drag_2", "isAbove= " + isAbove + " ,diffY= " + diffY);
                break;
        }

        return isAbove;
    }
}