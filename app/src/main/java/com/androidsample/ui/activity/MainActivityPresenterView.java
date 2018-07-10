package com.androidsample.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.androidsample.fragmentId.FragmentAvailable;


public interface MainActivityPresenterView {

    interface Presenter {
        void changeCurrentFragment(@FragmentAvailable int newFragmentType, Bundle extras, boolean isBackStep, boolean isFragReplace);


        void setCurrentFragment(@FragmentAvailable int currentFragment1);
    }

    interface View {
        void navigateToWithBundle(int container_layout, Fragment newFragment, boolean isBackStep, Bundle extras);

        void addFragmentWithBundle(int container_layout, Fragment newFragment, boolean isBackStep, Bundle extras);

        void showToolBarVisibility(boolean show);

        void showToast(@NonNull String message);

        String getStringResource(int stringId);
    }

}
