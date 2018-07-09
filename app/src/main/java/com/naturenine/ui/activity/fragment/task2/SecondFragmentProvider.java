package com.naturenine.ui.activity.fragment.task2;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Gagandeep on 17-06-2018.
 */
@Module
public interface SecondFragmentProvider {
    @ContributesAndroidInjector(modules = {SecondFragmentModule.class})
    abstract SecondFragment provideSecondFragmentFactory();
}
