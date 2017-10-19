package com.example.watchlist.utils;

import android.content.Context;
import android.widget.ImageView;

import com.example.watchlist.themoviedb.Movie;
import com.example.watchlist.themoviedb.TvShow;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

/**
 * Created by ekh on 19/10/2017.
 */

public class BackgroundPoster {
    /**
     * It pick a random image and set it to the back poster.
     * @param list List contains the list of the movies.
     */
    public static void setRandomBackPosterMovie(List<Movie> list, Context context, ImageView poster){
        int random = randomNumber(list.size());
        Picasso.with(context).load("http://image.tmdb.org/t/p/w342"+list.get(random).getPosterPath()).into(poster);

    }

    public static void setRandomBackPosterTv(List<TvShow> list, Context context, ImageView poster){
        int random = randomNumber(list.size());
        Picasso.with(context).load("http://image.tmdb.org/t/p/w342"+list.get(random).getPosterPath()).into(poster);

    }

    private static int randomNumber (int i){
        Random r = new Random();
        int random = r.nextInt(i);
        return random;
    }
}
