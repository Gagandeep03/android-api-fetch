package com.naturenine.ui.activity.fragment.task1;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Gagandeep on 16-11-2017.
 */
@Module
public interface FirstFragmentProvider {
    @ContributesAndroidInjector(modules = {FirstFragmentModule.class})
    abstract FirstFragment provideFirstFragmentFactory();
}
