package com.census.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Used this to resolved Jira#1372 problem similar as: https://stackoverflow.com/q/46452465/5110536
 * This will consume event if recyclerView is in settling state and will make list idle
 */
public class BottomSheetRecyclerView extends RecyclerView {
    public BottomSheetRecyclerView(@NonNull Context context) {
        super(context);
    }

    public BottomSheetRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomSheetRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean requestCancelDisallowInterceptTouchEvent = getScrollState() == SCROLL_STATE_SETTLING;
        boolean consumed = super.onInterceptTouchEvent(event);
        final int action = event.getActionMasked();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if( requestCancelDisallowInterceptTouchEvent ){
                    getParent().requestDisallowInterceptTouchEvent(false);

                    // only if it touched the top or the bottom. Thanks to @Sergey's answer.
                    if (!canScrollVertically(-1) || !canScrollVertically(1)) {
                        // stop scroll to enable child view to get the touch event
                        stopScroll();
                        // do not consume the event
                        return false;
                    }
                }
                break;
        }

        return consumed;
    }
}
