package com.androidsample.di.module;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;

import com.androidsample.di.qualifer.ApplicationContext;
import com.androidsample.di.qualifer.RecyclerScope;

import dagger.Module;
import dagger.Provides;


@Module
public class RecyclerViewModule {


    @Provides
    @RecyclerScope
    public Integer provideAnimationDuration() {
        return 500; // in milliseconds
    }

    @Provides
    @RecyclerScope
    public DividerItemDecoration providesDividerDecoration(@RecyclerScope LinearLayoutManager lm, @ApplicationContext Context context) {
        return new DividerItemDecoration(context, lm.getOrientation());
    }


    @Provides
    @RecyclerScope
        // layout manager for recyclerView
    public LinearLayoutManager provideLayoutManager(@ApplicationContext Context context) {
        LinearLayoutManager lm = new LinearLayoutManager(context);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        return lm;
    }


}
