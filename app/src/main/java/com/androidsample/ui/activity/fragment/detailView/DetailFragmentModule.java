package com.androidsample.ui.activity.fragment.detailView;

import com.androidsample.utils.schedulers.SchedulerProvider;

import dagger.Module;
import dagger.Provides;


@Module
public class DetailFragmentModule {

    @Provides
    public DetailFragmentViewModel provideViewModel(SchedulerProvider schedulerProvider) {
        return new DetailFragmentViewModel();
    }
}
