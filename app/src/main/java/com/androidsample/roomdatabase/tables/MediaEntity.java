package com.androidsample.roomdatabase.tables;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.androidsample.roomdatabase.TableConstants;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(foreignKeys = @ForeignKey(entity = ResultsEntity.class, parentColumns = TableConstants.RESULT_ID, childColumns = TableConstants.MEDIA_RESULT_ID, onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE), indices = {@Index(value = {TableConstants.MEDIA_RESULT_ID})},
        inheritSuperIndices = true)
public class MediaEntity {

    @PrimaryKey(autoGenerate = true)
    @SerializedName(TableConstants.MEDIA_ID)
    private long mediaId;
    @ColumnInfo(name = TableConstants.MEDIA_RESULT_ID)
    @SerializedName("resultId")
    private long resultId;

    @SerializedName(TableConstants.MEDIA_TYPE)
    private String type;
    @SerializedName(TableConstants.MEDIA_SUBTYPE)
    private String subtype;
    @SerializedName(TableConstants.MEDIA_CAPTION)
    private String caption;
    @SerializedName(TableConstants.MEDIA_COPYRIGHT)
    private String copyright;
    @SerializedName(value = TableConstants.MEDIA_APPROVED, alternate = "approvedForSyndication")
    private int approvedForSyndication;

    @Ignore
    @SerializedName("media-metadata")
    private List<MediaMetadataEntity> mediametadata;

    public long getMediaId() {
        return mediaId;
    }

    public void setMediaId(long mediaId) {
        this.mediaId = mediaId;
    }

    public long getResultId() {
        return resultId;
    }

    public void setResultId(long resultId) {
        this.resultId = resultId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public int getApprovedForSyndication() {
        return approvedForSyndication;
    }

    public void setApprovedForSyndication(int approvedForSyndication) {
        this.approvedForSyndication = approvedForSyndication;
    }

    public List<MediaMetadataEntity> getMediametadata() {
        return mediametadata;
    }

    public void setMediametadata(List<MediaMetadataEntity> mediametadata) {
        this.mediametadata = mediametadata;
    }
}
