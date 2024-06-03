package com.census.ui.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.census.databinding.ItemLoadingViewBinding;
import android.util.Log;


public abstract class BaseLoadMoreItemAdapter extends BaseRecyclerViewAdapter<BaseViewHolder> implements LoadMoreIndicatorListener {


    protected boolean showLoader = false;

    protected BaseLoadMoreItemAdapter() {
        hasLoadMore = true;
    }

    @Override
    public void showLoading(boolean status) {
        showLoader = status;
        Log.d("usm_adapter_show_loader", "showLoader= " + status + " ,itemCount= " + getItemCount());

        if (getItemCount() > 1)
            notifyItemChanged(getItemCount() - 1);
    }

    @Override
    public int getItemViewType(int position) {
        // loader can't be at position 0
        // loader can only be at the last position
        if (position != 0 && position == getItemCount() - 1) {
            return VIEW_TYPE_EMPTY;
        } else // if (peopleList != null && !peopleList.isEmpty())
        {
            return VIEW_TYPE_NORMAL;
        }
    }

    @Override
    protected BaseViewHolder createEmptyItemViewHolder(ViewGroup parent) {
        ItemLoadingViewBinding loadingViewBinding = ItemLoadingViewBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);

        // to set full width of this item
        if (loadingViewBinding.getRoot().getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) loadingViewBinding.getRoot().getLayoutParams();
            layoutParams.setFullSpan(true);
        }
        return new LoadingViewHolder(loadingViewBinding);
    }

    public boolean shouldShowLoader() {
        return hasLoadMore && showLoader;
    }

    public class LoadingViewHolder extends BaseViewHolder implements LoadingItemViewModel.LoadingItemViewModelListener {

        private ItemLoadingViewBinding itemLoadingViewBinding;

        public LoadingViewHolder(ItemLoadingViewBinding binding) {
            super(binding.getRoot());
            itemLoadingViewBinding = binding;
        }

        @Override
        public void onBind(int position) {
            LoadingItemViewModel emptyItemViewModel = new LoadingItemViewModel(this);
            itemLoadingViewBinding.setViewModel(emptyItemViewModel);
            itemLoadingViewBinding.getRoot().setVisibility(shouldShowLoader() ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick() {
            // mListener.onRetryClick();
        }
    }

}