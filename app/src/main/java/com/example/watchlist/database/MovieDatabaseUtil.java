package com.example.watchlist.database;

import java.util.Calendar;
import java.util.List;

/**
 * Created year 2017.
 * Author:
 *  Eiríkur Kristinn Hlöðversson
 *  Martin Einar Jensen
 *
 * Class for adding and removing movies to and from the users watchlist.
 */
public class MovieDatabaseUtil {

    /**
     * It check whether the movie is added to watchlist
     * and if so it return true else false.
     * @param movieId MovieId is the id of the movie
     * @return It return true if exists else false
     */
    public static boolean isMovieAddedToWatchlist(long movieId){
        List<MovieWatch> t = MovieWatch.find(MovieWatch.class, "movie_id = ?", String.valueOf(movieId));
        return t.size() != 0;
    }

    /**
     * It remove the movie from watchlist
     * @param movieId MovieId is the id of the movie
     */
    public static void removeMovieFromWatchlist(long movieId){
        List<MovieWatch> t = MovieWatch.find(MovieWatch.class, "movie_id = ?", String.valueOf(movieId));
        if(t.size() != 0) {
            t.get(0).delete();
        }
    }

    /**
     * It add the movie to the watchlist
     * @param movieId  MovieId is the id of the movie
     * @param name Name is the of the movie
     * @param posterPath PosterPath is the path to the poster
     * @param rating Rating is the rating of the movie
     * @param genreIds GenreIds is the genre ids of the movie
     */
    public static void addMovieToWatchlist(long movieId,String name,String posterPath,double rating,String genreIds){
        MovieWatch movieWatch = new MovieWatch(
                movieId,
                name,
                posterPath,
                rating,
                genreIds,
                Calendar.getInstance().getTimeInMillis());
        movieWatch.save();
    }

    /**
     * It get all the movie that have been added to the watchlist.
     * @return It return all the movie as list.
     */
    public static List<MovieWatch> getAllMovie(){
        return MovieWatch.listAll(MovieWatch.class);
    }
}
