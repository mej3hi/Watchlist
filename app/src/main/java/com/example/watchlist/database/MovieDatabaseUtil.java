package com.example.watchlist.database;

import java.util.Calendar;
import java.util.List;

/**
 * Class for adding and removing movies to and from the users watchlist.
 */

public class MovieDatabaseUtil {

    public static boolean isMovieAddedToWatchlist(long movieId){
        List<MovieWatch> t = MovieWatch.find(MovieWatch.class, "movie_id = ?", String.valueOf(movieId));
        if(t.size() != 0) {
            return true;

        }else{
            return false;
        }
    }

    public static boolean removeMovieFromWatchlist(long movieId){
        List<MovieWatch> t = MovieWatch.find(MovieWatch.class, "movie_id = ?", String.valueOf(movieId));
        if(t.size() != 0) {
            t.get(0).delete();
            return true;

        }else{
            return false;
        }
    }

    public static void addMovieToWatchlist(long movieId,String name,String posterPath,double rating,String genreId){
        MovieWatch movieWatch = new MovieWatch(
                movieId,
                name,
                posterPath,
                rating,
                genreId,
                Calendar.getInstance().getTimeInMillis());
        movieWatch.save();
    }

    public static List<MovieWatch> getallMovie(){
        return MovieWatch.listAll(MovieWatch.class);
    }
}
