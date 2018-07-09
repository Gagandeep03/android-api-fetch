package com.androidsample.ui.activity.fragment.task1;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public interface FirstFragmentProvider {
    @ContributesAndroidInjector(modules = {FirstFragmentModule.class})
    abstract FirstFragment provideFirstFragmentFactory();
}
