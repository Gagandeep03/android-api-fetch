package com.androidsample.di.module;

import android.app.Application;

import com.androidsample.api.ApiInterface;
import com.androidsample.di.qualifer.ApplicationContext;
import com.androidsample.utils.schedulers.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;


@Module

public class AppModule {


    @Provides
    @ApplicationContext
    Application provideApplicationContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    SchedulerProvider provideSchedulerProvider() {
        return new SchedulerProvider();
    }


    @Provides
    @Singleton
    ApiInterface provideApiInterface(Retrofit retrofit) {
        return retrofit.create(ApiInterface.class);
    }
}












