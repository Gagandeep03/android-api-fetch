package com.androidsample.ui.activity.fragment.HomeFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Gagandeep on 16-06-2018.
 */
@Module
public class MainFragmentModule {

    @Provides
   public    MainFragmentViewModel provideViewModel()
    {
        return new MainFragmentViewModel();
    }
}
