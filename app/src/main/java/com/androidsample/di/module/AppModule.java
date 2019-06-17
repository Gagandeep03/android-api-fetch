package com.androidsample.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.androidsample.api.ApiInterface;
import com.androidsample.di.qualifer.ApplicationContext;
import com.androidsample.roomdatabase.AppDatabase;
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

    @Provides
    @Singleton
    AppDatabase provideAppDatabase(@ApplicationContext Application application) {
        return Room.databaseBuilder(application, AppDatabase.class, "media_database")
                // allow queries on the main thread.
                // Don't do this on a real app! See PersistenceBasicSample for an example.

                .build();
    }
}












