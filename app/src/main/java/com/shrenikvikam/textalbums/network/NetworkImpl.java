package com.shrenikvikam.textalbums.network;

import com.shrenikvikam.textalbums.api.TextAlbumService;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.shrenikvikam.textalbums.Constants.BASE_URL;
import static com.shrenikvikam.textalbums.Constants.NETWORK_TIMEOUT;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Singleton
public class NetworkImpl {
    
    @Inject
    public NetworkImpl() {
    }

    public TextAlbumService getTextAlbumService() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(NETWORK_TIMEOUT, MILLISECONDS)
                .readTimeout(NETWORK_TIMEOUT, MILLISECONDS)
                .retryOnConnectionFailure(true)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(TextAlbumService.class);
    }
}
