package com.example.watchlist.service.endpoint;

import com.example.watchlist.themoviedb.Genre;
import com.example.watchlist.themoviedb.TvDetails;
import com.example.watchlist.themoviedb.TvEpisodeDetails;
import com.example.watchlist.themoviedb.TvSeasonDetails;
import com.example.watchlist.themoviedb.TvShow;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by martin on 8.12.2017.
 */

public interface ApiTvShow {

    @GET("tv/airing_today")
    Call<TvShow.TvShowsResults> toDayShows(@Query("page") int page);

    @GET("tv/popular")
    Call<TvShow.TvShowsResults> popularShows(@Query("page") int page);

    @GET("tv/on_the_air")
    Call<TvShow.TvShowsResults> onAirShows(@Query("page") int page);

    @GET("tv/top_rated")
    Call<TvShow.TvShowsResults> ratedShows(@Query("page") int page);

    @GET("tv/{tv_id}")
    Call<TvDetails> tvDetails(@Path("tv_id") long tv_id);

    @GET("tv/{tv_id}/season/{season_number}")
    Call<TvSeasonDetails> seasonDetails(@Path("tv_id") long tv_id, @Path("season_number") int season_number);

    @GET("tv/{tv_id}/season/{season_number}/episode/{episode_number}")
    Call<TvEpisodeDetails> episodeDetails(@Path("tv_id") long tv_id, @Path("season_number") int season_number, @Path("episode_number") int episode_number);

    @GET("search/tv")
    Call<TvShow.TvShowsResults> searchTvShows(@Query("query") String query, @Query("page") int page);

    @GET("genre/tv/list")
    Call<Genre.GenreResults> genreTv();

}
