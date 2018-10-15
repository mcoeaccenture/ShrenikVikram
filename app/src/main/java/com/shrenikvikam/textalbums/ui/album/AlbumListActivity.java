package com.shrenikvikam.textalbums.ui.album;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shrenikvikam.textalbums.R;
import com.shrenikvikam.textalbums.model.Album;
import com.shrenikvikam.textalbums.model.UserDetails;
import com.shrenikvikam.textalbums.ui.album.adapter.AlbumListAdapter;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class AlbumListActivity extends AppCompatActivity {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    public static final String PARAM_USER_ID = "param_user_id";
    public static final String PARAM_USER_NAME = "param_user_name";

    private int mUserId = -1;
    private String mUserName = "";

    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;
    private TextView mTextInformation;

    public static Intent createIntent(Context context, UserDetails userDetails) {
        Intent intent = new Intent(context, AlbumListActivity.class);
        intent.putExtra(PARAM_USER_ID, userDetails.getId());
        intent.putExtra(PARAM_USER_NAME, userDetails.getName());
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        setTitle(R.string.activity_title_album);
        if (getIntent() != null) {
            mUserId = getIntent().getIntExtra(PARAM_USER_ID, -1);
            mUserName = getIntent().getStringExtra(PARAM_USER_NAME);
        }

        mProgressBar = findViewById(R.id.progress_bar);
        mTextInformation = findViewById(R.id.txt_information);
        mRecyclerView = findViewById(R.id.recycler_view);

        AlbumListActivityViewModel mViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(AlbumListActivityViewModel.class);
        mViewModel.getTextAlbums(mUserId).observe(this, this::updateUiWithData);
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

    private void updateUiWithData(List<Album> albumList) {
        stopLoadingView();
        if (albumList == null) {
            showInformation(R.string.retry_message);
            return;
        }
        if (albumList.isEmpty()) {
            showInformation(R.string.empty_data);
        }
        if (!TextUtils.isEmpty(mUserName)) {
            setTitle(getString(R.string.album_list_header, mUserName));
        }
        AlbumListAdapter albumListAdapter = new AlbumListAdapter(albumList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Collections.sort(albumList, (o1, o2) -> o1.getTitle().compareTo(o2.getTitle()));
        mRecyclerView.setAdapter(albumListAdapter);
    }
}
