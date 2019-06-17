package com.androidsample.ui.activity.fragment.detailView;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;

import dagger.Module;
import dagger.Provides;


@Module
public class DetailFragmentModule {

    @Provides
    public DetailFragmentViewModel provideViewModel(Fragment fragment) {
        return ViewModelProviders.of(fragment).get(DetailFragmentViewModel.class);
    }

    @Provides
    Fragment provideFragment(DetailFragment fragment) {
        return fragment;
    }

}
