package com.shrenikvikam.textalbums;

import android.app.Activity;

import com.shrenikvikam.textalbums.di.componant.DaggerApplicationComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.DispatchingAndroidInjector;

public class MyApplication extends DaggerApplication {

    @Inject
    DispatchingAndroidInjector<Activity> mDispatchingAndroidActivityInjector;

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerApplicationComponent.builder().create(this);
    }
}
