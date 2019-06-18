package com.androidsample.beans;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Relation;
import android.os.Parcel;
import android.os.Parcelable;

import com.androidsample.roomdatabase.TableConstants;
import com.androidsample.roomdatabase.tables.MediaMetadataEntity;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MediaLiveBean implements Parcelable {

    public static final Parcelable.Creator<MediaLiveBean> CREATOR = new Parcelable.Creator<MediaLiveBean>() {
        @Override
        public MediaLiveBean createFromParcel(Parcel source) {
            return new MediaLiveBean(source);
        }

        @Override
        public MediaLiveBean[] newArray(int size) {
            return new MediaLiveBean[size];
        }
    };
    @Relation(parentColumn = TableConstants.MEDIA_ID,
            entity = MediaMetadataEntity.class,
            entityColumn = TableConstants.META_MEDIA_ID)
    @SerializedName(value = "mediaMeta", alternate = "media-metadata")
    public List<MediaMetadataEntity> mediaMetadataEntities;
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

    public MediaLiveBean() {
    }

    protected MediaLiveBean(Parcel in) {
        this.mediaId = in.readLong();
        this.resultId = in.readLong();
        this.type = in.readString();
        this.subtype = in.readString();
        this.caption = in.readString();
        this.copyright = in.readString();
        this.approvedForSyndication = in.readInt();
        this.mediaMetadataEntities = new ArrayList<MediaMetadataEntity>();
        in.readList(this.mediaMetadataEntities, MediaMetadataEntity.class.getClassLoader());
    }

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

    public List<MediaMetadataEntity> getMediaMetadataEntities() {
        return mediaMetadataEntities;
    }

    public void setMediaMetadataEntities(List<MediaMetadataEntity> mediaMetadataEntities) {
        this.mediaMetadataEntities = mediaMetadataEntities;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mediaId);
        dest.writeLong(this.resultId);
        dest.writeString(this.type);
        dest.writeString(this.subtype);
        dest.writeString(this.caption);
        dest.writeString(this.copyright);
        dest.writeInt(this.approvedForSyndication);
        dest.writeList(this.mediaMetadataEntities);
    }
}
