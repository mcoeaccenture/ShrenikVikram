package com.shrenikvikam.textalbums.api;

import com.shrenikvikam.textalbums.model.Album;
import com.shrenikvikam.textalbums.model.UserDetails;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TextAlbumService {
    @GET("/albums")
    Call<List<Album>> listAlbums();

    @GET("/albums?")
    Call<List<Album>> listAlbumsByUser(@Query("userId") int userId);

    @GET("/users")
    Call<List<UserDetails>> listUserDetails();
}
