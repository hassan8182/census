package com.census.data.local.db.tables;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;


import com.census.data.local.db.PlaceUpload;
import com.census.data.local.db.converter.PlaceUploadTypeConverter;
import com.census.data.local.db.converter.StringListTypeConverter;
import com.census.utils.PostTags;

import java.util.List;

@Entity(tableName = PostTags.TABLE_NAME_UPLOAD_POST)
@TypeConverters({PlaceUploadTypeConverter.class, StringListTypeConverter.class
})
public class PostUpload {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = PostTags.COLUMN_UPLOAD_POST_LOCAL_ID)
    public String postLocalId;
    @ColumnInfo(name = PostTags.COLUMN_UPLOAD_POST_LINK)
    public String link;
    @ColumnInfo(name = PostTags.COLUMN_UPLOAD_POST_LOCATION)
    public String location;
    @ColumnInfo(name = PostTags.COLUMN_UPLOAD_POST_CAPTION)
    public String captionText;
    @ColumnInfo(name = PostTags.COLUMN_UPLOAD_POST_THUMBNAIL)
    public String thumbnail;
    @ColumnInfo(name = PostTags.COLUMN_UPLOAD_POST_PDF_DESC)
    public String pdf_description;
    @ColumnInfo(name = PostTags.COLUMN_UPLOAD_POST_SUBSCRIPTION_UID)
    public String subscription_uid;
    @ColumnInfo(name = PostTags.COLUMN_UPLOAD_POST_PLAYLIST_UID)
    public String playlist_uid;
    @ColumnInfo(name = PostTags.COLUMN_UPLOAD_POST_PLACE)
    public PlaceUpload placeUpload;
    @Ignore
    public List<MediaUpload> mediaUploadList;


    public void setMediaUploadList(List<MediaUpload> mediaList) {
        mediaUploadList = mediaList;
    }
}