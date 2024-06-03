package com.census.data.local.db.tables;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.census.utils.MediaTags;
import com.census.utils.PostTags;


@Entity(tableName = MediaTags.TABLE_NAME_UPLOAD_MEDIA,
        foreignKeys =
        @ForeignKey(
                entity = PostUpload.class,
                parentColumns = PostTags.COLUMN_UPLOAD_POST_LOCAL_ID,
                childColumns = MediaTags.COLUMN_UPLOAD_MEDIA_POST_ID,
                onDelete = CASCADE),
        indices = {@Index(MediaTags.COLUMN_UPLOAD_MEDIA_POST_ID)})
public class MediaUpload {
    @Ignore
    public final static int UPLOAD_MEDIA_TYPE_IMAGE = 1;
    @Ignore
    public final static int UPLOAD_MEDIA_TYPE_VIDEO = 2;
    @Ignore
    public final static int UPLOAD_MEDIA_TYPE_MP3 = 3;
    @Ignore
    public final static int UPLOAD_MEDIA_TYPE_PDF = 4;

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = MediaTags.COLUMN_UPLOAD_MEDIA_KEY)
    public String mediaKey; // unique key
    @ColumnInfo(name = MediaTags.COLUMN_UPLOAD_MEDIA_HASH_MD5)
    public String hashMd5;   // hashMd5 of file, for now we are sending it empty, in future will be used
    @ColumnInfo(name = MediaTags.COLUMN_UPLOAD_MEDIA_TYPE)
    public int mediaType;  // picture or video
    @ColumnInfo(name = MediaTags.COLUMN_UPLOAD_MEDIA_FILE_PATH)
    public String filePath;
    @ColumnInfo(name = MediaTags.COLUMN_UPLOAD_MEDIA_POST_ID)
    public String postId;
    @ColumnInfo(name = MediaTags.COLUMN_UPLOAD_MEDIA_THUMBNAIL)
    public String thumbnail;  // thumbnail in case of videos
    @ColumnInfo(name = MediaTags.COLUMN_UPLOAD_MEDIA_OVERLAY_PATH)
    public String overlayPath;  // overlay image containing all the editing
    @ColumnInfo(name = MediaTags.COLUMN_UPLOAD_MEDIA_FILTER)
    public String filter;
    @Ignore
    public boolean isNewPosting;
}