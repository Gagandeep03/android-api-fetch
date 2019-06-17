package com.androidsample.ui.activity.fragment.task1;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.androidsample.api.ApiInterface;
import com.androidsample.di.qualifer.FragmentContext;
import com.androidsample.ui.activity.fragment.task1.adapter.ResultAdapter;
import com.androidsample.utils.schedulers.SchedulerProvider;

import dagger.Module;
import dagger.Provides;


@Module
public class FirstFragmentModule {

    @Provides
    ViewModelProvider.Factory provideFactory(SchedulerProvider schedulerProvider, ApiInterface apiInterface) {
        return new FirstFragmentViewModelFactory(schedulerProvider, apiInterface);
    }

    @Provides
    FirstFragmentViewModel provideFirstViewModel(Fragment fragment, ViewModelProvider.Factory factory) {
        return ViewModelProviders.of(fragment, factory).get(FirstFragmentViewModel.class);
    }

    @Provides
    public ResultAdapter provideAdapter(@FragmentContext Context context) {
        return new ResultAdapter(context);
    }

    @Provides
    @FragmentContext
    Context provideActivityContext(Activity activity) {
        return activity;
    }

    @Provides
    Activity provideActivity(FirstFragment fragment) {
        return fragment.getActivity();
    }

    @Provides
    Fragment provideFragment(FirstFragment fragment) {
        return fragment;
    }
}
