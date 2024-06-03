package com.census.data.local.db.doa;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.census.data.local.db.tables.MediaUpload;

import java.util.List;

@Dao
public abstract class MediaUploadDao implements BaseDao<MediaUpload> {

    @Query("SELECT * FROM upload_media WHERE post_id=:postId")
    public abstract List<MediaUpload> getMediaUploadListByPostId(String postId);


    @Query("SELECT * FROM upload_media WHERE post_id=:postId")
    public abstract LiveData<List<MediaUpload>> getLiveMediaUploadListByPostId(String postId);

    @Query("SELECT * FROM upload_media")
    public abstract List<MediaUpload> getAllMediaUploadList();
}