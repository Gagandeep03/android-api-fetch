package com.androidsample.ui.baseclass;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;

import com.androidsample.utils.schedulers.SchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;


public abstract class BaseViewModel<N> extends ViewModel {

    private final ObservableBoolean mIsLoading = new ObservableBoolean(false);
    private SchedulerProvider mSchedulerProvider;
    private N mNavigator;
    private CompositeDisposable mCompositeDisposable;

    public BaseViewModel(SchedulerProvider mSchedulerProvider) {
        this.mSchedulerProvider = mSchedulerProvider;
    }

    public BaseViewModel() {

    }

    public N getmNavigator() {
        return mNavigator;
    }

    public void setmNavigator(N mNavigator) {
        this.mNavigator = mNavigator;
    }

    public void setmSchedulerProvider(SchedulerProvider mSchedulerProvider) {
        this.mSchedulerProvider = mSchedulerProvider;
    }

    public void onViewCreated() {
        this.mCompositeDisposable = new CompositeDisposable();
    }

    public void onDestroyView() {

    }

    public SchedulerProvider getSchedulerProvider() {
        return mSchedulerProvider;
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public ObservableBoolean getIsLoading() {
        return mIsLoading;
    }

    public void setIsLoading(boolean isLoading) {
        mIsLoading.set(isLoading);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed())
            mCompositeDisposable.dispose();
    }
}