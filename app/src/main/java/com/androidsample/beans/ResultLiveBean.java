package com.androidsample.beans;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Relation;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import com.androidsample.roomdatabase.TableConstants;
import com.androidsample.roomdatabase.tables.MediaEntity;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ResultLiveBean implements Parcelable {

    public static final Parcelable.Creator<ResultLiveBean> CREATOR = new Parcelable.Creator<ResultLiveBean>() {
        @Override
        public ResultLiveBean createFromParcel(Parcel source) {
            return new ResultLiveBean(source);
        }

        @Override
        public ResultLiveBean[] newArray(int size) {
            return new ResultLiveBean[size];
        }
    };
    public static DiffUtil.ItemCallback<ResultLiveBean> DIFF_CALLBACK = new DiffUtil.ItemCallback<ResultLiveBean>() {
        @Override
        public boolean areItemsTheSame(@NonNull ResultLiveBean oldItem, @NonNull ResultLiveBean newItem) {
            return (oldItem.id == newItem.id);
        }

        @Override
        public boolean areContentsTheSame(@NonNull ResultLiveBean oldItem, @NonNull ResultLiveBean newItem) {
            return oldItem.equals(newItem);
        }
    };
    @Relation(parentColumn = TableConstants.RESULT_ID,
            entity = MediaEntity.class,
            entityColumn = TableConstants.MEDIA_RESULT_ID)
    @SerializedName(value = "media")
    public List<MediaLiveBean> mediaEntities;
    @SerializedName(TableConstants.RESULT_ID)
    private long id;
    @SerializedName(TableConstants.RESULT_TITLE)
    private String title;
    @SerializedName(TableConstants.RESULT_BYLINE)
    private String byline;
    @SerializedName(value = TableConstants.RESULT_PUBLISHED_DATE, alternate = "publishedDate")
    private String publishedDate;
    @SerializedName(TableConstants.RESULT_SOURCE)
    private String source;
    @SerializedName(TableConstants.RESULT_ABSTRACT)
    private String abstractX;
    @SerializedName(TableConstants.RESULT_SECTION)
    private String section;
    @SerializedName(TableConstants.RESULT_VIEWS)
    private int views;
    @ColumnInfo(name = TableConstants.RESULT_TIME)
    private long systemTime;

    public ResultLiveBean() {
    }

    protected ResultLiveBean(Parcel in) {
        this.id = in.readLong();
        this.title = in.readString();
        this.byline = in.readString();
        this.publishedDate = in.readString();
        this.source = in.readString();
        this.abstractX = in.readString();
        this.section = in.readString();
        this.views = in.readInt();
        this.systemTime = in.readLong();
        this.mediaEntities = new ArrayList<MediaLiveBean>();
        in.readList(this.mediaEntities, MediaLiveBean.class.getClassLoader());
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAbstractX() {
        return abstractX;
    }

    public void setAbstractX(String abstractX) {
        this.abstractX = abstractX;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public long getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(long systemTime) {
        this.systemTime = systemTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getByline() {
        return byline;
    }

    public void setByline(String byline) {
        this.byline = byline;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public List<MediaLiveBean> getMediaEntities() {
        return mediaEntities;
    }

    public void setMediaEntities(List<MediaLiveBean> mediaEntities) {
        this.mediaEntities = mediaEntities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultLiveBean that = (ResultLiveBean) o;
        if (!title.equals(that.title)) return false;
        if (!publishedDate.equals(that.publishedDate)) return false;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeString(this.byline);
        dest.writeString(this.publishedDate);
        dest.writeString(this.source);
        dest.writeString(this.abstractX);
        dest.writeString(this.section);
        dest.writeInt(this.views);
        dest.writeLong(this.systemTime);
        dest.writeList(this.mediaEntities);
    }
}
