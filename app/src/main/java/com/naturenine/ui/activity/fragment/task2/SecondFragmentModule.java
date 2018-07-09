package com.naturenine.ui.activity.fragment.task2;

import android.app.Activity;
import android.content.Context;

import com.naturenine.di.qualifer.FragmentContext;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Gagandeep on 17-06-2018.
 */
@Module
public class SecondFragmentModule {

    @Provides
    SecondFragmentViewModel providesSecondViewModel() {
        return new SecondFragmentViewModel();
    }

    @Provides
    @FragmentContext
    Context provideActivityContext(Activity activity) {
        return activity;
    }

    @Provides
    Activity provideActivity(SecondFragment fragment) {
        return fragment.getActivity();
    }
}
