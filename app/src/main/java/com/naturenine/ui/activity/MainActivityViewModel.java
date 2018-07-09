package com.naturenine.ui.activity;


import android.databinding.ObservableBoolean;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.naturenine.R;
import com.naturenine.enums.FragmentAvailable;
import com.naturenine.ui.activity.fragment.HomeFragment.MainFragment;
import com.naturenine.ui.activity.fragment.task1.FirstFragment;
import com.naturenine.ui.activity.fragment.task2.SecondFragment;
import com.naturenine.ui.baseclass.BaseViewModel;

import static com.naturenine.enums.FragmentAvailable.HOME_SCREEN;
import static com.naturenine.enums.FragmentAvailable.TASK_FIRST;
import static com.naturenine.enums.FragmentAvailable.TASK_TWO;


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

    MainFragment mainFragment;

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
                case HOME_SCREEN:
                    mainFragment = getMainFragment();
                    if (extras != null)
                        mainFragment.setArguments(extras);
                    newFragment = mainFragment;
                    break;
                case TASK_FIRST:
                    FirstFragment firstFragment = new FirstFragment();
                    newFragment = firstFragment;
                    break;
                case TASK_TWO:
                    SecondFragment secondFragment = new SecondFragment();
                    newFragment = secondFragment;
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

    MainFragment getMainFragment() {
        return new MainFragment();
    }
}
