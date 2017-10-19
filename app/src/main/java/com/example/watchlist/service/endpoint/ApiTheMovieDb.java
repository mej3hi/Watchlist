package com.example.watchlist.service.endpoint;


import com.example.watchlist.themoviedb.Genre;
import com.example.watchlist.themoviedb.Movie;
import com.example.watchlist.themoviedb.TvShow;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
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


    @GET("movie/top_rated")
    Call<Movie.MoviesResults> ratedMovies(@Query("page") int page, @Query("region") String region);

    @GET("movie/now_playing")
    Call<Movie.MoviesResults> nowPlayingMovies(@Query("page") int page,@Query("region") String region);

    @GET("movie/popular")
    Call<Movie.MoviesResults> popularMovies(@Query("page") int page,@Query("region") String region);

    @GET("movie/upcoming")
    Call<Movie.MoviesResults> upcomingMovies(@Query("page") int page,@Query("region") String region);
}
