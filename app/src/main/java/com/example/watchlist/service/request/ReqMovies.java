package com.example.watchlist.service.request;

import com.example.watchlist.service.client.ServiceGenerator;
import com.example.watchlist.service.endpoint.ApiTheMovieDb;
import com.example.watchlist.themoviedb.Movie;
import com.example.watchlist.themoviedb.MovieDetails;

import retrofit2.Call;
import retrofit2.Callback;


public class ReqMovies {
    /**
     * Send Get method url ("movie/top_rated").
     * It get the top rated movies.
     * @param page Is the page to get.
     */
    public static void ratedMovies(int page, Callback callback) {
        ApiTheMovieDb apiTheMovieDb = ServiceGenerator.createService(ApiTheMovieDb.class);
        Call<Movie.MoviesResults> call = apiTheMovieDb.ratedMovies(page,"US");
        call.enqueue(callback);
    }

    /**
     * Send Get method url ("movie/now_playing").
     * It get the now playing movies.
     * @param page Is the page to get.
     */
    public static void nowPlayingMovies(int page, Callback callback) {
        ApiTheMovieDb apiTheMovieDb = ServiceGenerator.createService(ApiTheMovieDb.class);
        Call<Movie.MoviesResults> call = apiTheMovieDb.nowPlayingMovies(page,"US");

        call.enqueue(callback);

    }

    /**
     * Send Get method url ("movie/popular").
     * It get the popular movies.
     * @param page Is the page to get.
     */
    public static void popularMovies(int page, Callback callback) {
        ApiTheMovieDb apiTheMovieDb = ServiceGenerator.createService(ApiTheMovieDb.class);
        Call<Movie.MoviesResults> call = apiTheMovieDb.popularMovies(page,"US");

        call.enqueue(callback);

    }

    /**
     * Send Get method url ("movie/upcoming").
     * It get the upcoming Movies.
     * @param page Is the page to get.
     */
    public static void upcomingMovies(int page, Callback callback) {
        ApiTheMovieDb apiTheMovieDb = ServiceGenerator.createService(ApiTheMovieDb.class);
        Call<Movie.MoviesResults> call = apiTheMovieDb.upcomingMovies(page,"US");

        call.enqueue(callback);

    }

    /**
     * Send Get method url ("movie/{movie_id}").
     * It get the tv details.
     * @param movieId It is the movie id.
     */
    public static void movieDetails(long movieId, Callback callback) {
        ApiTheMovieDb apiTheMovieDb = ServiceGenerator.createService(ApiTheMovieDb.class);
        Call<MovieDetails> call = apiTheMovieDb.movieDetails(movieId);

        call.enqueue(callback);

    }

    /**
     * Send Get method url ("search/movie").
     * It get the search result for movies.
     * @param page It the page to get.
     * @param query It is the query to search after.
     */
    public static void SearchMovies(int page, String query, Callback callback) {
        ApiTheMovieDb apiTheMovieDb = ServiceGenerator.createService(ApiTheMovieDb.class);
        Call<Movie.MoviesResults> call = apiTheMovieDb.searchMovie(query,page,"US");

        call.enqueue(callback);

    }
}
