package com.androidsample.roomdatabase.tables;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.androidsample.roomdatabase.TableConstants;
import com.google.gson.annotations.SerializedName;

@Entity(foreignKeys = @ForeignKey(entity = MediaEntity.class, parentColumns = TableConstants.MEDIA_ID, childColumns = TableConstants.META_MEDIA_ID, onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE), indices = {@Index(value = {TableConstants.META_MEDIA_ID})},
        inheritSuperIndices = true)
public class MediaMetadataEntity {


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = TableConstants.META_ID)
    private long metaId;

    @SerializedName(TableConstants.META_MEDIA_ID)
    private long mediaId;

    @SerializedName(TableConstants.META_URL)
    private String url;
    @SerializedName(TableConstants.META_FORMAT)
    private String format;
    @SerializedName(TableConstants.META_HEIGHT)
    private int height;
    @SerializedName(TableConstants.META_WIDTH)
    private int width;

    public long getMetaId() {
        return metaId;
    }

    public void setMetaId(long metaId) {
        this.metaId = metaId;
    }


    public long getMediaId() {
        return mediaId;
    }

    public void setMediaId(long mediaId) {
        this.mediaId = mediaId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
