package com.naturenine.ui.activity;

import android.content.Context;


import com.naturenine.di.qualifer.ActivityContext;

import dagger.Module;
import dagger.Provides;




@Module
public class MainActivityModule {

    /**
     * Inject this to {@link MainActivity
     * }
     *
     * @param context
     * @param
     * @return
     */
    @Provides
    public MainActivityViewModel provideModule(@ActivityContext Context context) {
        return new MainActivityViewModel();
    }


    @Provides
    @ActivityContext
    public Context provideContext(MainActivity activity) {
        return activity;
    }
}
