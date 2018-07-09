package com.androidsample.ui.activity.fragment.detailView;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public interface DetailFragmentProvider {

    @ContributesAndroidInjector(modules = {DetailFragmentModule.class})
    abstract DetailFragment provideMainFragmentFactory();
}
