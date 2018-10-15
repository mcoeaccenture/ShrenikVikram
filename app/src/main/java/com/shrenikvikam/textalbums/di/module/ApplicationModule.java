package com.shrenikvikam.textalbums.di.module;

import android.content.Context;

import com.shrenikvikam.textalbums.MyApplication;
import com.shrenikvikam.textalbums.network.NetworkImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    @Provides
    Context provideContext(MyApplication application) {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    NetworkImpl provideNetworkImpl() {
        return new NetworkImpl();
    }
}
