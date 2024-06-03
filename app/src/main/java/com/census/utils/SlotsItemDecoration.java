package com.census.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class SlotsItemDecoration extends RecyclerView.ItemDecoration {
    private int space;
    private int bottomMargin;

    public SlotsItemDecoration(int space, int bottomMargin) {
        this.space = space;
        this.bottomMargin = bottomMargin;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = bottomMargin;
        outRect.top = bottomMargin;
    }
}