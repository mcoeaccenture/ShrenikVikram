package com.shrenikvikam.textalbums.ui.album;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.shrenikvikam.textalbums.api.TextAlbumService;
import com.shrenikvikam.textalbums.model.Album;
import com.shrenikvikam.textalbums.network.NetworkImpl;

import java.util.List;

import javax.inject.Singleton;

import static com.shrenikvikam.textalbums.Constants.TAG;

@Singleton
public class AlbumListActivityViewModel extends ViewModel {

    private final NetworkImpl networkImpl;

    private MutableLiveData<List<Album>> textAlbums;

    public AlbumListActivityViewModel(NetworkImpl networkImpl) {
        this.networkImpl = networkImpl;
    }

    public LiveData<List<Album>> getTextAlbums(int userId) {
        if (textAlbums == null) {
            textAlbums = new MutableLiveData<>();
            loadTextAlbums(userId);
        }
        return textAlbums;
    }

    private void loadTextAlbums(int userId) {
        FetchTextAlbumTask fetchTextAlbumTask = new FetchTextAlbumTask();
        fetchTextAlbumTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, userId);
    }

    static class Factory implements ViewModelProvider.Factory {

        private final NetworkImpl mNetworkImpl;

        public Factory(NetworkImpl networkImpl) {
            mNetworkImpl = networkImpl;
        }

        @NonNull
        @Override
        @SuppressWarnings("unchecked")
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(AlbumListActivityViewModel.class)) {
                return (T) new AlbumListActivityViewModel(mNetworkImpl);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }

    private class FetchTextAlbumTask extends AsyncTask<Integer, Void, List<Album>> {
        List<Album> albumsData = null;

        @Override
        protected List<Album> doInBackground(Integer... params) {
            try {
                TextAlbumService textAlbumService = networkImpl.getTextAlbumService();
                if (params.length == 0 || params[0] == -1) {
                    albumsData = textAlbumService.listAlbums().execute().body();
                } else {
                    albumsData = textAlbumService.listAlbumsByUser(params[0]).execute().body();
                }
            } catch (Exception e) {
                Log.w(TAG, "Exception :" + e.getMessage());
            }
            return albumsData;
        }

        @Override
        protected void onPostExecute(List<Album> albumsData) {
            textAlbums.setValue(albumsData);
        }
    }
}
