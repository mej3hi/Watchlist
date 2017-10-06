package com.example.watchlist.service.client;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * It add the api key to the url for ever request that are made.
 */

public class ApiKeyInterceptor implements Interceptor{

    private String apikey;

    public ApiKeyInterceptor(String apikey) {
        this.apikey = apikey;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();

        HttpUrl url = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key",apikey)
                .build();

        Request.Builder requestBuilder = original.newBuilder()
                .url(url);


        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
