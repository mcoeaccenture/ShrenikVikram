package com.shrenikvikam.textalbums.ui.album;

import android.arch.lifecycle.ViewModelProvider;

import com.shrenikvikam.textalbums.network.NetworkImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class AlbumListActivityModule {
    @Provides
    ViewModelProvider.Factory providesViewModelFactory(NetworkImpl network) {
        return new AlbumListActivityViewModel.Factory(network);
    }
}