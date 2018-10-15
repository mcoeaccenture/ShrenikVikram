package com.shrenikvikam.textalbums.ui.user;

import android.arch.lifecycle.ViewModelProvider;

import com.shrenikvikam.textalbums.network.NetworkImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class UserListActivityModule {
    @Provides
    ViewModelProvider.Factory providesViewModelFactory(NetworkImpl network) {
        return new UserListActivityViewModel.Factory(network);
    }
}