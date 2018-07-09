package com.androidsample.di.module;

import android.app.Application;
import android.content.Context;

import com.androidsample.di.qualifer.ApplicationContext;

import dagger.Module;
import dagger.Provides;



@Module

public class AppModule {


    @Provides
    @ApplicationContext
    Context provideApplicationContext(Application application) {
        return application;
    }
}












