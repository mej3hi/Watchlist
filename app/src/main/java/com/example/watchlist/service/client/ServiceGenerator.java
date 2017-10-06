package com.example.watchlist.service.client;



import okhttp3.OkHttpClient;
import retrofit2.Retrofit;


import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    /**
     * Is the Domain Name System (DNS) of the homepage.
     */
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    /**
     * Is the api key for the themoviedatabase website.
     */
    private static final String API_KEY = "";
    /**
     * Tells what the ApiKeyInterceptor has been add to rhe Interceptors.
     */
    private static boolean isApiKeyInterceptorAdd = false;

    /**
     * Here we crate httpClient to add header to the Http request.
     */
    private static  OkHttpClient.Builder httpClient = new OkHttpClient.Builder();


    /**
     * Here we crate builder so we can call Http request and listen to Http respond.
     */
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    /**
     * Here we crate standard server and set the api key.
     * @return createService(serviceClass)
     */
    public static <S> S createService(Class<S> serviceClass){

        if(!isApiKeyInterceptorAdd){
            httpClient.addInterceptor(new ApiKeyInterceptor(API_KEY));

            builder.client(httpClient.build());
            retrofit = builder.build();

            isApiKeyInterceptorAdd = true;
        }

        return retrofit.create(serviceClass);
    }

}
