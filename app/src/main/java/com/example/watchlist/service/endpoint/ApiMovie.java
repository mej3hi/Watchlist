package com.example.watchlist.service.endpoint;


import com.example.watchlist.themoviedb.Genre;
import com.example.watchlist.themoviedb.Movie;
import com.example.watchlist.themoviedb.MovieDetails;
import com.example.watchlist.themoviedb.TvDetails;
import com.example.watchlist.themoviedb.TvEpisodeDetails;
import com.example.watchlist.themoviedb.TvSeasonDetails;
import com.example.watchlist.themoviedb.TvShow;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created year 2017.
 * Author:
 *  Eiríkur Kristinn Hlöðversson
 *  Martin Einar Jensen
 *
 * All the endpoint for the movie.
 */

public interface ApiMovie {

    @GET("movie/top_rated")
    Call<Movie.MoviesResults> ratedMovies(@Query("page") int page, @Query("region") String region);

    @GET("movie/now_playing")
    Call<Movie.MoviesResults> nowPlayingMovies(@Query("page") int page,@Query("region") String region);

    @GET("movie/popular")
    Call<Movie.MoviesResults> popularMovies(@Query("page") int page,@Query("region") String region);

    @GET("movie/upcoming")
    Call<Movie.MoviesResults> upcomingMovies(@Query("page") int page,@Query("region") String region);

    @GET("movie/{movie_id}")
    Call<MovieDetails> movieDetails(@Path("movie_id") long movie_id);

    @GET("search/movie")
    Call<Movie.MoviesResults> searchMovie(@Query("query") String query, @Query("page") int page,@Query("region") String region);

    @GET("genre/movie/list")
    Call<Genre.GenreResults> genreMovies();


}

