package com.androidsample.roomdatabase.tables;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.androidsample.roomdatabase.TableConstants;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

@Entity(indices = {@Index(value = TableConstants.RESULT_ID, unique = true)})
public class ResultsEntity implements Parcelable {

    public static final Parcelable.Creator<ResultsEntity> CREATOR = new Parcelable.Creator<ResultsEntity>() {
        @Override
        public ResultsEntity createFromParcel(Parcel source) {
            return new ResultsEntity(source);
        }

        @Override
        public ResultsEntity[] newArray(int size) {
            return new ResultsEntity[size];
        }
    };
    @ColumnInfo(name = TableConstants.RESULT_TIME)
    public long systemTime = System.currentTimeMillis();
    @PrimaryKey
    @SerializedName(TableConstants.RESULT_ID)
    private long id;
    @SerializedName(TableConstants.RESULT_URL)
    private String url;
    @SerializedName(value = TableConstants.RESULT_ADXKEYWORD, alternate = "adxKeywords")
    private String adxKeywords;
    @SerializedName(TableConstants.RESULT_SECTION)
    private String section;
    @SerializedName(TableConstants.RESULT_BYLINE)
    private String byline;
    @SerializedName(TableConstants.RESULT_TYPE)
    private String type;
    @SerializedName(TableConstants.RESULT_TITLE)
    private String title;
    @SerializedName(TableConstants.RESULT_ABSTRACT)
    private String abstractX;
    @SerializedName(value = TableConstants.RESULT_PUBLISHED_DATE, alternate = "publishedDate")
    private String publishedDate;
    @SerializedName(TableConstants.RESULT_SOURCE)
    private String source;
    @SerializedName(value = TableConstants.RESULT_ASSET_ID, alternate = "assertId")
    private long assetId;
    @SerializedName(TableConstants.RESULT_VIEWS)
    private int views;
    @Ignore
    @SerializedName("media")
    private List<MediaEntity> media;

    public ResultsEntity() {
    }

    protected ResultsEntity(Parcel in) {
        this.url = in.readString();
        this.adxKeywords = in.readString();
        this.section = in.readString();
        this.byline = in.readString();
        this.type = in.readString();
        this.title = in.readString();
        this.abstractX = in.readString();
        this.publishedDate = in.readString();
        this.source = in.readString();
        this.id = in.readLong();
        this.assetId = in.readLong();
        this.views = in.readInt();
        this.media = new ArrayList<MediaEntity>();
        in.readList(this.media, MediaEntity.class.getClassLoader());
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAdxKeywords() {
        return adxKeywords;
    }

    public void setAdxKeywords(String adxKeywords) {
        this.adxKeywords = adxKeywords;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getByline() {
        return byline;
    }

    public void setByline(String byline) {
        this.byline = byline;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbstractX() {
        return abstractX;
    }

    public void setAbstractX(String abstractX) {
        this.abstractX = abstractX;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAssetId() {
        return assetId;
    }

    public void setAssetId(long assetId) {
        this.assetId = assetId;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public List<MediaEntity> getMedia() {
        return media;
    }

    public void setMedia(List<MediaEntity> media) {
        this.media = media;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.adxKeywords);
        dest.writeString(this.section);
        dest.writeString(this.byline);
        dest.writeString(this.type);
        dest.writeString(this.title);
        dest.writeString(this.abstractX);
        dest.writeString(this.publishedDate);
        dest.writeString(this.source);
        dest.writeLong(this.id);
        dest.writeLong(this.assetId);
        dest.writeInt(this.views);
        dest.writeList(this.media);
    }
}
