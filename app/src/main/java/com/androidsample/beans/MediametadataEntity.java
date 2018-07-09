package com.androidsample.beans;

import com.google.gson.annotations.SerializedName;

public class MediametadataEntity {
    /**
     * url : https://static01.nyt.com/images/2018/07/02/world/02mexico-top/02mexico-top-square320.jpg
     * format : square320
     * height : 320
     * width : 320
     */

    @SerializedName("url")
    private String url;
    @SerializedName("format")
    private String format;
    @SerializedName("height")
    private int height;
    @SerializedName("width")
    private int width;

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
