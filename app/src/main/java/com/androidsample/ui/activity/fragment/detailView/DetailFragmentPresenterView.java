package com.androidsample.ui.activity.fragment.detailView;


import com.androidsample.beans.ResultsEntity;

public interface DetailFragmentPresenterView {

    interface Presenter {

        void setResultEntity(ResultsEntity entity);
    }

    interface View {

        void loadImage(String imageUrl);

        String getAppendString(int section_string, String section);
    }
}
