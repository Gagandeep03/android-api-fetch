package com.androidsample.ui.activity.fragment.task1;

import android.arch.lifecycle.LiveData;

import com.androidsample.roomdatabase.tables.MediaEntity;
import com.androidsample.roomdatabase.tables.MediaMetadataEntity;
import com.androidsample.roomdatabase.tables.ResultsEntity;

import java.util.List;


public interface FirstFragmentPresenterView {

    interface Presenter {
        LiveData getResultLiveData();

        void setResultLiveData(LiveData resultLiveData);

        void loadApi(String date, String apiKey);
    }

    interface View {

        void showToast(String s);

        String getStringIds(int stringId);

        long insertResultEntry(ResultsEntity entity);

        long insertMediaEntry(MediaEntity mediaEntity);

        void insertMediaMetaList(List<MediaMetadataEntity> mediaMetadataEntities);

        void hideLoading();
    }
}
