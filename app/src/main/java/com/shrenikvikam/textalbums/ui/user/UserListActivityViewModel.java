package com.shrenikvikam.textalbums.ui.user;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.shrenikvikam.textalbums.api.TextAlbumService;
import com.shrenikvikam.textalbums.model.UserDetails;
import com.shrenikvikam.textalbums.network.NetworkImpl;

import java.util.List;

import javax.inject.Singleton;

import static com.shrenikvikam.textalbums.Constants.TAG;

@Singleton
public class UserListActivityViewModel extends ViewModel {

    private final NetworkImpl networkImpl;

    private MutableLiveData<List<UserDetails>> userDetails;

    public UserListActivityViewModel(NetworkImpl networkImpl) {
        this.networkImpl = networkImpl;
    }

    public LiveData<List<UserDetails>> getUserDetails() {
        if (userDetails == null) {
            userDetails = new MutableLiveData<>();
            loadUsersData();
        }
        return userDetails;
    }

    private void loadUsersData() {
        FetchUsersDataTask fetchUsersDataTask = new FetchUsersDataTask();
        fetchUsersDataTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
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
            if (modelClass.isAssignableFrom(UserListActivityViewModel.class)) {
                return (T) new UserListActivityViewModel(mNetworkImpl);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }

    private class FetchUsersDataTask extends AsyncTask<Void, Void, List<UserDetails>> {
        List<UserDetails> userDetailsData = null;

        @Override
        protected List<UserDetails> doInBackground(Void... params) {
            try {
                TextAlbumService textAlbumService = networkImpl.getTextAlbumService();
                userDetailsData = textAlbumService.listUserDetails().execute().body();
            } catch (Exception e) {
                Log.w(TAG, "Exception :" + e.getMessage());
            }
            return userDetailsData;
        }

        @Override
        protected void onPostExecute(List<UserDetails> userDetailsData) {
            userDetails.setValue(userDetailsData);
        }
    }
}
