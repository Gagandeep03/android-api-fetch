package com.androidsample.ui.activity;


import android.databinding.ObservableBoolean;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.androidsample.R;
import com.androidsample.fragmentId.FragmentAvailable;
import com.androidsample.ui.activity.fragment.detailView.DetailFragment;
import com.androidsample.ui.activity.fragment.task1.FirstFragment;
import com.androidsample.ui.baseclass.BaseViewModel;

import static com.androidsample.fragmentId.FragmentAvailable.DETAIL_SCREEN;
import static com.androidsample.fragmentId.FragmentAvailable.TASK_FIRST;


public class MainActivityViewModel extends BaseViewModel<MainActivityPresenterView.View> implements MainActivityPresenterView.Presenter {

    private final String TAG = MainActivityViewModel.class.getSimpleName();
    // Observable for toolbar visible/gone with binding view
    public ObservableBoolean isToolbarVisible = new ObservableBoolean(false);
    //Fragment

    // Variables
    @FragmentAvailable
    int currentFragment;
    @FragmentAvailable
    int newFragment;


    public MainActivityViewModel() {
        super();
    }

    public int getCurrentFragment() {
        return currentFragment;
    }

    @Override
    public void setCurrentFragment(@FragmentAvailable int currentFragment) {
        this.currentFragment = currentFragment;
    }

    @Override
    public void changeCurrentFragment(@FragmentAvailable int newFragmentType, Bundle extras, boolean isBackStep, boolean isFragReplace) {
        try {
            newFragment = newFragmentType;
            Fragment newFragment = null;
            switch (newFragmentType) {
                case TASK_FIRST:
                    FirstFragment firstFragment = getFirstFragment();
                    if (extras != null) {
                        firstFragment.setArguments(extras);
                    }
                    newFragment = firstFragment;
                    break;
                case DETAIL_SCREEN:
                    DetailFragment detailFragment = getDetailFragment();
                    if (extras != null) {
                        detailFragment.setArguments(extras);
                    }
                    newFragment = detailFragment;
                    break;

            }

            if (newFragment != null) {
                currentFragment = newFragmentType;
                if (isFragReplace)
                    getmNavigator().navigateToWithBundle(R.id.dashboard_container, newFragment, isBackStep, extras); // For Fragment Replace call
                else {
                    getmNavigator().addFragmentWithBundle(R.id.dashboard_container, newFragment, isBackStep, extras); // For Fragment Add not replace
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setIsLoading(boolean isLoading) {
        super.setIsLoading(isLoading);
    }


    /**
     * Observer bind with the layout, to make the toolbar visibility on/off
     *
     * @param isToolbarVisible
     */
    public void setIsToolbarVisible(boolean isToolbarVisible) {
        this.isToolbarVisible.set(isToolbarVisible);
    }

    DetailFragment getDetailFragment() {
        return new DetailFragment();
    }

    FirstFragment getFirstFragment() {
        return new FirstFragment();
    }
}
