package com.census.ui.base;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseRecyclerViewAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {

    public final static int VIEW_TYPE_EMPTY = 0;
    public final static int VIEW_TYPE_NORMAL = 1;
    public final static int VIEW_TYPE_HEADER = 2;

    protected boolean hasLoadMore;
    protected boolean hasHeader;

    protected abstract T createNormalItemViewHolder(ViewGroup parent);

    protected abstract T createEmptyItemViewHolder(ViewGroup parent);

    @NonNull
    @Override
    public T onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return createNormalItemViewHolder(parent);
            case VIEW_TYPE_EMPTY:
            default:
                return createEmptyItemViewHolder(parent);
        }
    }
}