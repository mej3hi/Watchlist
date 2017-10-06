package com.example.watchlist.service.endpoint;


import com.example.watchlist.themoviedb.Genre;
import com.example.watchlist.themoviedb.TvShow;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * It has all the endpoint for the TMDb.
 */

public interface ApiTheMovieDb {

    @GET("tv/airing_today")
    Call<TvShow.TvShowsResults> toDayShows(@Query("page") int page);

    @GET("tv/popular")
    Call<TvShow.TvShowsResults> popularShows(@Query("page") int page);

    @GET("tv/on_the_air")
    Call<TvShow.TvShowsResults> onAirShows(@Query("page") int page);

    @GET("tv/top_rated")
    Call<TvShow.TvShowsResults> ratedShows(@Query("page") int page);

    @GET("genre/tv/list")
    Call<Genre.GenreResults> genreTv();

    @GET("genre/movie/list")
    Call<Genre.GenreResults> genreMovies();

}
