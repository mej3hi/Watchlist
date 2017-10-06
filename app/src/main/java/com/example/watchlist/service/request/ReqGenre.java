package com.example.watchlist.service.request;

import com.example.watchlist.service.client.ServiceGenerator;
import com.example.watchlist.service.endpoint.ApiTheMovieDb;
import com.example.watchlist.service.response.tvShows.ResTvGenre;
import com.example.watchlist.themoviedb.Genre;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReqGenre {

    /**
     * Send Get method url ("genre/tv/list").
     * It get the genre list for tv shows.
     */
    public static void genreTvList() {
        ApiTheMovieDb apiTheMovieDb = ServiceGenerator.createService(ApiTheMovieDb.class);
        Call<Genre.GenreResults> call = apiTheMovieDb.genreTv();

        call.enqueue(new Callback<Genre.GenreResults>() {
            @Override
            public void onResponse(Call<Genre.GenreResults> call, Response<Genre.GenreResults> response) {
                System.out.println("response raw: " + response.raw());
                EventBus.getDefault().post(new ResTvGenre(response.body(), response.code()));
            }

            @Override
            public void onFailure(Call<Genre.GenreResults> call, Throwable t) {
                System.out.println("Failure :" + t);
                t.printStackTrace();

            }
        });
    }


}
