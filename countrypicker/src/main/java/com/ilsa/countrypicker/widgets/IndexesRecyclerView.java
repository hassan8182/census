package com.ilsa.countrypicker.widgets;

import android.content.Context;
import android.graphics.Canvas;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import com.ilsa.countrypicker.interfaces.FastScrollRecyclerViewInterface;

public class IndexesRecyclerView extends RecyclerView {
    private Context ctx;
    private RecyclerView mListingRecyclerView;

    public IndexesRecyclerView(Context context) {
        super(context);
        ctx = context;
    }

    public IndexesRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ctx = context;
    }

    public IndexesRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        ctx = context;
    }

    @Override
    public void onDraw(Canvas c) {
        super.onDraw(c);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {

        if (mListingRecyclerView != null) {
            // Log.d("usm_index_rv", "touch: x= " + e.getX() + " ,y= " + e.getY() + " ,event= " + e.getAction());
            View childView = this.findChildViewUnder(e.getX(), e.getY());
            if (childView != null) {
                int position = this.getChildAdapterPosition(childView);
                onTouchedPositionDetected(position, childView);
            }
        }
        return super.onTouchEvent(e);
    }

    private void onTouchedPositionDetected(int position, View childView) {
        String item = ((FastScrollRecyclerViewInterface) getAdapter()).getItemAtPosition(position);
        //   Log.d("usm_index_touch", "position= " + position + " ,item= " + item);
        if (((FastScrollRecyclerViewInterface) getAdapter()).getMapIndex().containsKey(item.toUpperCase())) {
            // mListingRecyclerView.scrollToPosition(((FastScrollRecyclerViewInterface) getAdapter()).getMapIndex().get(item.toUpperCase()));
            ((LinearLayoutManager) mListingRecyclerView.getLayoutManager()).scrollToPositionWithOffset(((FastScrollRecyclerViewInterface) getAdapter()).getMapIndex().get(item.toUpperCase()), 0);
            scaleView(childView, 0.8f, 2f);
        }
    }

    public void scaleView(View v, float startScale, float endScale) {
        Animation anim = new ScaleAnimation(
                startScale, endScale, // Start and end values for the X axis scaling
                startScale, endScale, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 0.5f); // Pivot point of Y scaling
        anim.setFillAfter(false); // Needed to keep the result of the animation
        anim.setDuration(400);
        v.startAnimation(anim);
    }

    public void setListingRecyclerView(RecyclerView recyclerView) {
        this.mListingRecyclerView = recyclerView;
    }

}