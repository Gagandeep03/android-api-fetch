package com.naturenine.ui.activity.fragment.task1;

import android.app.Activity;
import android.content.Context;

import com.naturenine.beans.ListBeanModel;
import com.naturenine.di.qualifer.FragmentContext;
import com.naturenine.ui.activity.fragment.task1.adapter.ListAdapter;

import java.util.Collections;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Gagandeep on 16-06-2018.
 */
@Module
public class FirstFragmentModule {

    @Provides
    public FirstFragmentViewModel provideFirstFragment()
    {
        return new FirstFragmentViewModel();
    }

    @Provides
    public ListAdapter providesListAdapter(@FragmentContext Context context)
    {
        return new ListAdapter(context, Collections.<ListBeanModel>emptyList());
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
