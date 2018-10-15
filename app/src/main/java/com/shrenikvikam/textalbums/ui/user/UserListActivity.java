package com.shrenikvikam.textalbums.ui.user;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shrenikvikam.textalbums.R;
import com.shrenikvikam.textalbums.model.UserDetails;
import com.shrenikvikam.textalbums.ui.album.AlbumListActivity;
import com.shrenikvikam.textalbums.ui.user.adapter.UserListAdapter;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class UserListActivity extends AppCompatActivity implements UserListAdapter.OnUserDetailsClickListener {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;
    private TextView mTextInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        setTitle(R.string.activity_title_user);
        mProgressBar = findViewById(R.id.progress_bar);
        mTextInformation = findViewById(R.id.txt_information);
        mRecyclerView = findViewById(R.id.recycler_view);

        UserListActivityViewModel mViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(UserListActivityViewModel.class);
        mViewModel.getUserDetails().observe(this, this::updateUiWithData);
        mTextInformation.setText(R.string.loading);
        showLoadingView();
    }

    public void showLoadingView() {
        mTextInformation.setVisibility(VISIBLE);
        mProgressBar.setVisibility(VISIBLE);
        mRecyclerView.setVisibility(GONE);
    }

    public void stopLoadingView() {
        mTextInformation.setVisibility(GONE);
        mProgressBar.setVisibility(GONE);
        mRecyclerView.setVisibility(VISIBLE);
    }

    public void showInformation(int resId) {
        mTextInformation.setText(resId);
        mTextInformation.setVisibility(VISIBLE);
        mRecyclerView.setVisibility(GONE);
    }

    private void updateUiWithData(List<UserDetails> userDetailsList) {
        stopLoadingView();
        if (userDetailsList == null) {
            showInformation(R.string.retry_message);
            return;
        }
        if (userDetailsList.isEmpty()) {
            showInformation(R.string.empty_data);
        }
        UserListAdapter userListAdapter = new UserListAdapter(userDetailsList, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(userListAdapter);
    }

    @Override
    public void onUserDetailsClick(UserDetails userDetails) {
        startActivity(AlbumListActivity.createIntent(this, userDetails));
    }
}
