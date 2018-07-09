package com.androidsample.ui.baseclass;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;

/**
 * Created by Gagandeep on 19-09-2017.
 */

public abstract class BaseViewModel<N>  extends ViewModel {


    private final ObservableBoolean mIsLoading = new ObservableBoolean(false);

    private N mNavigator;


    public BaseViewModel() {

    }

    public N getmNavigator() {
        return mNavigator;
    }

    public void setmNavigator(N mNavigator) {
        this.mNavigator = mNavigator;
    }



    public void onViewCreated() {

    }

    public void onDestroyView() {

    }



    public ObservableBoolean getIsLoading() {
        return mIsLoading;
    }

    public void setIsLoading(boolean isLoading) {
        mIsLoading.set(isLoading);
    }


}
