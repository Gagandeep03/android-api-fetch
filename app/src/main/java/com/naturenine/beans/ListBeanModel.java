package com.naturenine.beans;

/**
 * Created by Gagandeep on 16-06-2018.
 */
public class ListBeanModel {

    private String name;
    private String imageUrl;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    private boolean isSelected;

    public ListBeanModel(String name, String url) {
        this.name = name;
        this.imageUrl = url;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
