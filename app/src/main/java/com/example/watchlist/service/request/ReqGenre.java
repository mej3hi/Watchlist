package com.example.watchlist.service.request;

import com.example.watchlist.service.client.ServiceGenerator;
import com.example.watchlist.service.endpoint.ApiTheMovieDb;

import com.example.watchlist.themoviedb.Genre;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReqGenre {

    /**
     * Send Get method url ("genre/tv/list").
     * It gets the genre list for tv shows.
     */
    public static void genreTvList(Callback callback) {
        ApiTheMovieDb apiTheMovieDb = ServiceGenerator.createService(ApiTheMovieDb.class);
        Call<Genre.GenreResults> call = apiTheMovieDb.genreTv();

        call.enqueue(callback);
    }

    /**
     * Send Get method url ("genre/movie/list").
     * It gets the genre list for movies.
     */
    public static void genreMovieList(Callback callback) {
        ApiTheMovieDb apiTheMovieDb = ServiceGenerator.createService(ApiTheMovieDb.class);
        Call<Genre.GenreResults> call = apiTheMovieDb.genreMovies();

        call.enqueue(callback);
    }


}
