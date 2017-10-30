package com.example.watchlist.service.request;


import com.example.watchlist.service.client.ServiceGenerator;
import com.example.watchlist.service.endpoint.ApiTheMovieDb;

import com.example.watchlist.themoviedb.TvDetails;
import com.example.watchlist.themoviedb.TvEpisodeDetails;
import com.example.watchlist.themoviedb.TvSeasonDetails;
import com.example.watchlist.themoviedb.TvShow;

import retrofit2.Call;
import retrofit2.Callback;


public class ReqTvShows {


    /**
     * Send Get method url ("tv/airing_today").
     * It get the to day shows.
     * @param page Is the page to get.
     */
    public static void toDayShows(int page, Callback callback) {
        ApiTheMovieDb apiTheMovieDb = ServiceGenerator.createService(ApiTheMovieDb.class);
        Call<TvShow.TvShowsResults> call = apiTheMovieDb.toDayShows(page);

        call.enqueue(callback);

    }

    /**
     * Send Get method url ("tv/popular").
     * It get the popular tv shows.
     * @param page Is the page to get.
     */
    public static void popularTvShows(int page, Callback callback) {
        ApiTheMovieDb apiTheMovieDb = ServiceGenerator.createService(ApiTheMovieDb.class);
        Call<TvShow.TvShowsResults> call = apiTheMovieDb.popularShows(page);

        call.enqueue(callback);

    }

    /**
     * Send Get method url ("tv/on_the_air").
     * It get the on air tv shows.
     * @param page Is the page to get.
     */
    public static void onAirTvShows(int page, Callback callback) {
        ApiTheMovieDb apiTheMovieDb = ServiceGenerator.createService(ApiTheMovieDb.class);
        Call<TvShow.TvShowsResults> call = apiTheMovieDb.onAirShows(page);

        call.enqueue(callback);

    }

    /**
     * Send Get method url ("tv/top_rated").
     * It get the on rated shows.
     * @param page Is the page to get.
     */
    public static void ratedTvShows(int page, Callback callback) {
        ApiTheMovieDb apiTheMovieDb = ServiceGenerator.createService(ApiTheMovieDb.class);
        Call<TvShow.TvShowsResults> call = apiTheMovieDb.ratedShows(page);

        call.enqueue(callback);

    }

    /**
     * Send Get method url ("tv/{tv_id}").
     * It get the tv details.
     * @param tvId It is the tv id.
     */
    public static void tvDetails(long tvId,Callback callback) {
        ApiTheMovieDb apiTheMovieDb = ServiceGenerator.createService(ApiTheMovieDb.class);
        Call<TvDetails> call = apiTheMovieDb.tvDetails(tvId);

        call.enqueue(callback);

    }


    /**
     * Send Get method url ("tv/{tv_id}/season/{season_number}").
     * It get the season.
     * @param tvId It is the tv id.
     * @param seasonNum It is the season number.
     */
    public static void seasonDetails(long tvId,int seasonNum, Callback callback) {
        ApiTheMovieDb apiTheMovieDb = ServiceGenerator.createService(ApiTheMovieDb.class);
        Call<TvSeasonDetails> call = apiTheMovieDb.seasonDetails(tvId,seasonNum);

        call.enqueue(callback);

    }


    /**
     * Send Get method url ("tv/{tv_id}/season/{season_number}/episode/{episode_number}").
     * It get the episode
     * @param tvId It is the tv id.
     * @param seasonNumber It is the season number.
     * @param episodeNumber It is the episode number.
     */
    public static void episodeDetails(long tvId, int seasonNumber, int episodeNumber,Callback callback) {
        ApiTheMovieDb apiTheMovieDb = ServiceGenerator.createService(ApiTheMovieDb.class);
        Call<TvEpisodeDetails> call = apiTheMovieDb.episodeDetails(tvId,seasonNumber,episodeNumber);
        call.enqueue(callback);


    }

}
