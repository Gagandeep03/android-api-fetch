/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.androidsample.di.builder;


import com.androidsample.ui.activity.MainActivity;
import com.androidsample.ui.activity.MainActivityModule;
import com.androidsample.ui.activity.fragment.HomeFragment.MainFragmentProvider;
import com.androidsample.ui.activity.fragment.task1.FirstFragmentProvider;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = {MainActivityModule.class, FirstFragmentProvider.class,
            MainFragmentProvider.class})
    abstract MainActivity bindMainActivity();

}