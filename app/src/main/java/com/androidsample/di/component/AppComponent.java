package com.androidsample.di.component;

import android.app.Application;

import com.androidsample.application.GlobalApp;
import com.androidsample.di.builder.ActivityBuilder;
import com.androidsample.di.module.AppModule;
import com.androidsample.di.module.NetworkModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;


@Singleton
@Component(modules = {AndroidInjectionModule.class, AppModule.class, NetworkModule.class,
        ActivityBuilder.class})
public interface AppComponent {

    void inject(GlobalApp app);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);


        AppComponent build();
    }
}
