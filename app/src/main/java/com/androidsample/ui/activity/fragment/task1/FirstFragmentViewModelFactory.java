package com.androidsample.ui.activity.fragment.task1;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.androidsample.api.ApiInterface;
import com.androidsample.utils.schedulers.SchedulerProvider;

public class FirstFragmentViewModelFactory implements ViewModelProvider.Factory {

    private final SchedulerProvider schedulerProvider;
    private final ApiInterface apiInterface;

    public FirstFragmentViewModelFactory(SchedulerProvider schedulerProvider, ApiInterface apiInterface) {
        this.schedulerProvider = schedulerProvider;
        this.apiInterface = apiInterface;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new FirstFragmentViewModel(schedulerProvider, apiInterface);
    }
}
