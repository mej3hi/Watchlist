package com.example.watchlist.service.request;


import com.example.watchlist.service.client.ServiceGenerator;
import com.example.watchlist.service.endpoint.ApiTheMovieDb;
import com.example.watchlist.service.response.tvShows.ResOnAirTvShows;
import com.example.watchlist.service.response.tvShows.ResPopularTvShows;
import com.example.watchlist.service.response.tvShows.ResRatedTvShows;
import com.example.watchlist.service.response.tvShows.ResTodayTvShows;
import com.example.watchlist.themoviedb.TvShow;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReqTvShows {


    /**
     * Send Get method url ("tv/airing_today").
     * It get the to day shows.
     * @param page Is the page to get.
     */
    public static void toDayShows(int page) {
        ApiTheMovieDb apiTheMovieDb = ServiceGenerator.createService(ApiTheMovieDb.class);
        Call<TvShow.TvShowsResults> call = apiTheMovieDb.toDayShows(page);

        call.enqueue(new Callback<TvShow.TvShowsResults>() {
            @Override
            public void onResponse(Call<TvShow.TvShowsResults> call, Response<TvShow.TvShowsResults> response) {
                System.out.println("response raw: " + response.raw());
                EventBus.getDefault().post(new ResTodayTvShows(response.body(),response.code()));


            }

            @Override
            public void onFailure(Call<TvShow.TvShowsResults> call, Throwable t) {
                System.out.println("Failure :" +t);
                t.printStackTrace();

            }
        });

    }

    /**
     * Send Get method url ("tv/popular").
     * It get the popular tv shows.
     * @param page Is the page to get.
     */
    public static void popularTvShows(int page) {
        ApiTheMovieDb apiTheMovieDb = ServiceGenerator.createService(ApiTheMovieDb.class);
        Call<TvShow.TvShowsResults> call = apiTheMovieDb.popularShows(page);

        call.enqueue(new Callback<TvShow.TvShowsResults>() {
            @Override
            public void onResponse(Call<TvShow.TvShowsResults> call, Response<TvShow.TvShowsResults> response) {
                System.out.println("response raw: " + response.raw());
                EventBus.getDefault().post(new ResPopularTvShows(response.body(),response.code()));


            }

            @Override
            public void onFailure(Call<TvShow.TvShowsResults> call, Throwable t) {
                System.out.println("Failure :" +t);
                t.printStackTrace();

            }
        });

    }

    /**
     * Send Get method url ("tv/on_the_air").
     * It get the on air tv shows.
     * @param page Is the page to get.
     */
    public static void onAirTvShows(int page) {
        ApiTheMovieDb apiTheMovieDb = ServiceGenerator.createService(ApiTheMovieDb.class);
        Call<TvShow.TvShowsResults> call = apiTheMovieDb.onAirShows(page);

        call.enqueue(new Callback<TvShow.TvShowsResults>() {
            @Override
            public void onResponse(Call<TvShow.TvShowsResults> call, Response<TvShow.TvShowsResults> response) {
                System.out.println("response raw: " + response.raw());
                EventBus.getDefault().post(new ResOnAirTvShows(response.body(),response.code()));


            }

            @Override
            public void onFailure(Call<TvShow.TvShowsResults> call, Throwable t) {
                System.out.println("Failure :" +t);
                t.printStackTrace();

            }
        });

    }

    /**
     * Send Get method url ("tv/top_rated").
     * It get the on rated shows.
     * @param page Is the page to get.
     */
    public static void ratedTvShows(int page) {
        ApiTheMovieDb apiTheMovieDb = ServiceGenerator.createService(ApiTheMovieDb.class);
        Call<TvShow.TvShowsResults> call = apiTheMovieDb.ratedShows(page);

        call.enqueue(new Callback<TvShow.TvShowsResults>() {
            @Override
            public void onResponse(Call<TvShow.TvShowsResults> call, Response<TvShow.TvShowsResults> response) {
                System.out.println("response raw: " + response.raw());
                EventBus.getDefault().post(new ResRatedTvShows(response.body(),response.code()));


            }

            @Override
            public void onFailure(Call<TvShow.TvShowsResults> call, Throwable t) {
                System.out.println("Failure :" +t);
                t.printStackTrace();

            }
        });

    }



}
