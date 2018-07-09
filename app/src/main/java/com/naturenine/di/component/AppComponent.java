package com.naturenine.di.component;

import android.app.Application;



import com.naturenine.application.GlobalApp;
import com.naturenine.di.builder.ActivityBuilder;
import com.naturenine.di.module.AppModule;
import com.naturenine.di.module.RecyclerViewModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;



@Singleton
@Component(modules = {AndroidInjectionModule.class, AppModule.class,
        ActivityBuilder.class, RecyclerViewModule.class})
public interface AppComponent {

    void inject(GlobalApp app);

    @Component.Builder
    interface Builder
    {
        @BindsInstance
        Builder application(Application application);



        AppComponent build();
    }
}
