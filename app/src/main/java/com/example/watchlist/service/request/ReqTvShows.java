package com.example.watchlist.service.request;


import com.example.watchlist.service.client.ServiceGenerator;

import com.example.watchlist.service.endpoint.ApiTvShow;
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
        ApiTvShow apiTvShow = ServiceGenerator.createService(ApiTvShow.class);
        Call<TvShow.TvShowsResults> call = apiTvShow.toDayShows(page);

        call.enqueue(callback);

    }

    /**
     * Send Get method url ("tv/popular").
     * It get the popular tv shows.
     * @param page Is the page to get.
     */
    public static void popularTvShows(int page, Callback callback) {
        ApiTvShow apiTvShow = ServiceGenerator.createService(ApiTvShow.class);
        Call<TvShow.TvShowsResults> call = apiTvShow.popularShows(page);

        call.enqueue(callback);

    }

    /**
     * Send Get method url ("tv/on_the_air").
     * It get the on air tv shows.
     * @param page Is the page to get.
     */
    public static void onAirTvShows(int page, Callback callback) {
        ApiTvShow apiTvShow = ServiceGenerator.createService(ApiTvShow.class);
        Call<TvShow.TvShowsResults> call = apiTvShow.onAirShows(page);

        call.enqueue(callback);

    }

    /**
     * Send Get method url ("tv/top_rated").
     * It get the on rated shows.
     * @param page Is the page to get.
     */
    public static void ratedTvShows(int page, Callback callback) {
        ApiTvShow apiTvShow = ServiceGenerator.createService(ApiTvShow.class);
        Call<TvShow.TvShowsResults> call = apiTvShow.ratedShows(page);

        call.enqueue(callback);

    }

    /**
     * Send Get method url ("tv/{tv_id}").
     * It get the tv details.
     * @param tvId It is the tv id.
     */
    public static void tvDetails(long tvId,Callback callback) {
        ApiTvShow apiTvShow = ServiceGenerator.createService(ApiTvShow.class);
        Call<TvDetails> call = apiTvShow.tvDetails(tvId);

        call.enqueue(callback);

    }


    /**
     * Send Get method url ("tv/{tv_id}/season/{season_number}").
     * It get the season.
     * @param tvId It is the tv id.
     * @param seasonNum It is the season number.
     */
    public static void seasonDetails(long tvId,int seasonNum, Callback callback) {
        ApiTvShow apiTvShow = ServiceGenerator.createService(ApiTvShow.class);
        Call<TvSeasonDetails> call = apiTvShow.seasonDetails(tvId,seasonNum);

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
        ApiTvShow apiTvShow = ServiceGenerator.createService(ApiTvShow.class);
        Call<TvEpisodeDetails> call = apiTvShow.episodeDetails(tvId,seasonNumber,episodeNumber);
        call.enqueue(callback);


    }


    /**
     * Send Get method url ("search/tv").
     * It get the search result for tv shows.
     * @param page It the page to get.
     * @param query It is the query to search after.
     */
    public static void SearchTvShows(int page, String query, Callback callback) {
        ApiTvShow apiTvShow = ServiceGenerator.createService(ApiTvShow.class);
        Call<TvShow.TvShowsResults> call = apiTvShow.searchTvShows(query,page);

        call.enqueue(callback);

    }


}
