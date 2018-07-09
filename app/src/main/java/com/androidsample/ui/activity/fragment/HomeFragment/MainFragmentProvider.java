package com.androidsample.ui.activity.fragment.HomeFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Gagandeep on 16-06-2018.
 */
@Module
public interface MainFragmentProvider {

    @ContributesAndroidInjector(modules = {MainFragmentModule.class})
    abstract MainFragment provideMainFragmentFactory();
}
