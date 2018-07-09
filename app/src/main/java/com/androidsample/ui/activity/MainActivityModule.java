package com.androidsample.ui.activity;

import android.content.Context;

import com.androidsample.di.qualifer.ActivityContext;

import dagger.Module;
import dagger.Provides;




@Module
public class MainActivityModule {

    /**
     * Inject this to {@link MainActivity
     * }
     *
     *
     * @param
     * @return
     */
    @Provides
    public MainActivityViewModel provideModule() {
        return new MainActivityViewModel();
    }


    @Provides
    @ActivityContext
    public Context provideContext(MainActivity activity) {
        return activity;
    }
}
