package com.androidsample.ui.activity.fragment.task1;

import android.arch.lifecycle.MutableLiveData;
import android.support.v7.widget.RecyclerView;

import com.androidsample.beans.ListBeanModel;

import java.util.List;

/**
 * Created by Gagandeep on 16-06-2018.
 */
public interface FirstFragmentPresenterView {

    interface Presenter{


        MutableLiveData<String> getCurrentName();

        void updateSelectedName(String name);

        void calculateRecyclerView();
    }

    interface View{

        void setAdapter(List<ListBeanModel> list);

        RecyclerView getRecyclerView();

        void setAdapterSelectItem(int setColorDate);
    }
}
