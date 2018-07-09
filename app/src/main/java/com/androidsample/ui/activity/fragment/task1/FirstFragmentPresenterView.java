package com.androidsample.ui.activity.fragment.task1;

import com.androidsample.beans.ResultsEntity;

import java.util.List;


public interface FirstFragmentPresenterView {

    interface Presenter{


        void loadApi(String date, String apiKey);
    }

    interface View{

        void setAdapter(List<ResultsEntity> list);


        void showToast(String s);

        String getStringIds(int stringId);
    }
}
