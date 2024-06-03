package com.census.ui.base;

import java.util.List;

public interface AdapterUpdateListener<T> {
    void clearItems();

    void addItems(List<T> items, boolean isLoadMore);

    default void addItem(T object) {
    }

    default void removeItem(int position) {
    }

}