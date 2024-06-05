package com.census.data.local.db.dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Delete;
import androidx.room.Insert;

public interface BaseDao<T> {

    @Insert(onConflict = REPLACE)
    long insert(T obj);

    @Delete
    void delete(T obj);
}
