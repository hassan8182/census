package com.census.data.local.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;


import com.census.data.local.db.tables.PostUpload;

import java.util.List;

@Dao
public abstract class PostUploadDao implements BaseDao<PostUpload> {

    @Query("SELECT COUNT(*) FROM posts_upload")
    public abstract int getCount();

    @Query("SELECT * FROM posts_upload Order By post_local_id ASC LIMIT 1")
    public abstract PostUpload getTopPendingPost();

    @Query("SELECT post_local_id FROM posts_upload Order By post_local_id ASC LIMIT 1")
    public abstract String getTopPendingPostId();

    @Query("SELECT * FROM posts_upload WHERE post_local_id = :postId")
    public abstract PostUpload getPostById(String postId);

    @Query("DELETE FROM posts_upload WHERE post_local_id=:postId")
    public abstract void deleteByPostId(String postId);

    @Query("DELETE FROM posts_upload")
    public abstract void deleteAllUploadPosts();

    @Query("SELECT * FROM posts_upload Order By post_local_id ASC LIMIT 1")
    public abstract LiveData<PostUpload> getLiveTopUploadingPost();

    @Query("SELECT * FROM posts_upload Order By post_local_id")
    public abstract LiveData<List<PostUpload>> getLiveUploadingPostList();

    @Query("SELECT * FROM posts_upload Order By post_local_id")
    public abstract List<PostUpload> getUploadPostList();

    @Query("UPDATE posts_upload SET post_thumbnail = :thumbnail WHERE post_local_id =:postId")
    public abstract void updateUploadPost(String postId, String thumbnail);

    /*@Query("SELECT * FROM posts_upload WHERE post_local_id = :postId")
    public abstract PostUploadWrapper getPostWrapperById(String postId);*/


}
