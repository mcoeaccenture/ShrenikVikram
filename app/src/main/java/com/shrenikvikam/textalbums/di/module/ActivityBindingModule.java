package com.shrenikvikam.textalbums.di.module;

import com.shrenikvikam.textalbums.ui.album.AlbumListActivity;
import com.shrenikvikam.textalbums.ui.album.AlbumListActivityModule;
import com.shrenikvikam.textalbums.ui.user.UserListActivity;
import com.shrenikvikam.textalbums.ui.user.UserListActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = AlbumListActivityModule.class)
    abstract AlbumListActivity albumListActivity();

    @ContributesAndroidInjector(modules = UserListActivityModule.class)
    abstract UserListActivity userListActivity();
}
