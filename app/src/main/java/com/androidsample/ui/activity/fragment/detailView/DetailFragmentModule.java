package com.androidsample.ui.activity.fragment.detailView;

import dagger.Module;
import dagger.Provides;


@Module
public class DetailFragmentModule {

    @Provides
    public DetailFragmentViewModel provideViewModel() {
        return new DetailFragmentViewModel();
    }


}
