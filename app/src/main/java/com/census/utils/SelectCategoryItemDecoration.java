package com.census.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class SelectCategoryItemDecoration extends RecyclerView.ItemDecoration {
    private int space;
    private int bottomMargin;

    public SelectCategoryItemDecoration(int space, int bottomMargin) {
        this.space = space;
        this.bottomMargin = bottomMargin;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = bottomMargin;
        outRect.top = 0;

//        // Add top margin only for the first item to avoid double space between items
//        if (parent.getChildLayoutPosition(view) == 0) {
//            outRect.top = space;
//        } else {
//            outRect.top = 0;
//        }
    }
}