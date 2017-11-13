package com.example.watchlist.utils;

import android.content.Context;
import android.widget.ImageView;

import com.example.watchlist.themoviedb.Movie;
import com.example.watchlist.themoviedb.SearchResults;
import com.example.watchlist.themoviedb.TvShow;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;


public class BackgroundPoster {

    public static final String path = "http://image.tmdb.org/t/p/w342";


    /**
     * It pick a random image and set it to the back poster.
     * @param list List contains the list of the movies.
     */
    public static void setRandomBackPosterMovie(List<Movie> list, Context context, ImageView poster){
        int random = randomNumber(list.size());
        Picasso.with(context).load(path+list.get(random).getPosterPath()).into(poster);
    }

    /**
     * It pick a random image and set it to the back poster.
     * @param list List contains the list of the tvShow.
     */
    public static void setRandomBackPosterTv(List<TvShow> list, Context context, ImageView poster){
        int random = randomNumber(list.size());
        Picasso.with(context).load(path+list.get(random).getPosterPath()).into(poster);
    }

    /**
     * It pick a random image and set it to the back poster.
     * @param list List contains the list of the searchResults.
     */
    public static void setRandomBackPosterSearch(List<SearchResults> list, Context context, ImageView poster){
        int random = randomNumber(list.size());
        Picasso.with(context).load(path+list.get(random).getPosterPath()).into(poster);
    }

    private static int randomNumber (int i){
        Random r = new Random();
        return r.nextInt(i);
    }
}
