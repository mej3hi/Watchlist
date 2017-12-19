package com.example.watchlist.service.request;

import com.example.watchlist.service.client.ServiceGenerator;
import com.example.watchlist.service.endpoint.ApiMovie;

import com.example.watchlist.service.endpoint.ApiTvShow;
import com.example.watchlist.themoviedb.Genre;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created year 2017.
 * Author:
 *  Eiríkur Kristinn Hlöðversson
 *  Martin Einar Jensen
 */
public class ReqGenre {

    /**
     * Send Get method url ("genre/tv/list").
     * It gets the genre list for tv shows.
     */
    public static void genreTvList(Callback callback) {
        ApiTvShow apiTvShow = ServiceGenerator.createService(ApiTvShow.class);
        Call<Genre.GenreResults> call = apiTvShow.genreTv();

        call.enqueue(callback);
    }

    /**
     * Send Get method url ("genre/movie/list").
     * It gets the genre list for movies.
     */
    public static void genreMovieList(Callback callback) {
        ApiMovie apiTheMovieDb = ServiceGenerator.createService(ApiMovie.class);
        Call<Genre.GenreResults> call = apiTheMovieDb.genreMovies();

        call.enqueue(callback);
    }


}
