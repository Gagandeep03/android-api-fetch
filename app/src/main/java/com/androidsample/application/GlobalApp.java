package com.androidsample.application;

import android.app.Activity;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.androidsample.di.component.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;


public class GlobalApp extends MultiDexApplication implements HasActivityInjector {


    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        MultiDex.install(this);
        // Initialize the Application level dagger Component here.
        DaggerAppComponent.builder().application(this)
                // .baseUrl(NetModule.API_BASE_URL)
                .build()
                .inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }
}
