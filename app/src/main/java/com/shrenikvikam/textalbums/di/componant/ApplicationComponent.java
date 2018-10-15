package com.shrenikvikam.textalbums.di.componant;

import com.shrenikvikam.textalbums.MyApplication;
import com.shrenikvikam.textalbums.di.module.ActivityBindingModule;
import com.shrenikvikam.textalbums.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        ActivityBindingModule.class,
        ApplicationModule.class,
})
public interface ApplicationComponent extends AndroidInjector<MyApplication> {

    void inject(MyApplication application);

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<MyApplication> {
    }
}
