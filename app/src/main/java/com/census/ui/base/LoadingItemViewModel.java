package com.census.ui.base;

public class LoadingItemViewModel {

    private LoadingItemViewModelListener mListener;

    public LoadingItemViewModel(LoadingItemViewModelListener listener) {
        this.mListener = listener;
    }

    public void onClick() {
        mListener.onClick();
    }

    public interface LoadingItemViewModelListener {

        void onClick();
    }
}
