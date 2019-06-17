package com.androidsample.ui.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.androidsample.di.qualifer.ActivityContext;

import dagger.Module;
import dagger.Provides;


@Module
public class MainActivityModule {

    /**
     * Inject this to {@link MainActivity
     * }
     *
     * @param
     * @return
     */
    @Provides
    public MainActivityViewModel provideMainActivityViewModel(FragmentActivity activity) {
        return ViewModelProviders.of(activity).get(MainActivityViewModel.class);
    }


    @Provides
    @ActivityContext
    public Context provideContext(MainActivity activity) {
        return activity;
    }


    @Provides
    public FragmentActivity provideActivity(MainActivity activity) {
        return activity;
    }
}
