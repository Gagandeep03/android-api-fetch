package com.androidsample.ui.activity.fragment.task1;

import android.app.Activity;
import android.content.Context;

import com.androidsample.api.ApiInterface;
import com.androidsample.beans.ResultsEntity;
import com.androidsample.di.qualifer.FragmentContext;
import com.androidsample.ui.activity.fragment.task1.adapter.ListAdapter;
import com.androidsample.utils.schedulers.SchedulerProvider;

import java.util.Collections;

import dagger.Module;
import dagger.Provides;


@Module
public class FirstFragmentModule {

    @Provides
    public FirstFragmentViewModel provideFirstFragment(SchedulerProvider schedulerProvider, ApiInterface apiInterface)
    {
        return new FirstFragmentViewModel(schedulerProvider, apiInterface);
    }

    @Provides
    public ListAdapter providesListAdapter(@FragmentContext Context context)
    {
        return new ListAdapter(context, Collections.<ResultsEntity>emptyList());
    }

    @Provides
    @FragmentContext
    Context provideActivityContext(Activity activity) {
        return activity;
    }

    @Provides
    Activity provideActivity(FirstFragment fragment)
    {
        return fragment.getActivity();
    }
}
