package com.example.watchlist.database;


import java.util.Calendar;
import java.util.List;

/**
 * Created year 2017.
 * Author:
 *  Eiríkur Kristinn Hlöðversson
 *  Martin Einar Jensen
 *
 * Class for adding and removing tv shows to and from users watchlist.
 */
public class TvDatabaseUtil {
    /**
     * It check whether the tv show is added to watchlist
     * and if so it return true else false.
     * @param tvId TvId is the id of the tv show
     * @return It return true if exists else false
     */
    public static boolean isTvAddedToWatchlist(long tvId){
        List<TvShowsWatch> t = TvShowsWatch.find(TvShowsWatch.class, "tv_id = ?", String.valueOf(tvId));
        return t.size() != 0;
    }

    /**
     * It remove the tv show from watchlist
     * @param tvId TvId is the id of the tv show
     */
    public static void removeTvFromWatchlist(long tvId){
        List<TvShowsWatch> t = TvShowsWatch.find(TvShowsWatch.class, "tv_id = ?", String.valueOf(tvId));
        if(t.size() != 0) {
            t.get(0).delete();
        }
    }

    /**
     * It add the tv show to the watchlist
     * @param tvId TvId is the id of the tv show
     * @param name Name is the of the tv show
     * @param posterPath PosterPath is the path to the poster
     * @param rating Rating is the rating of the tv show
     * @param genreIds GenreIds is the genre ids of the tv show
     */
    public static void addTvToWatchlist(long tvId,String name,String posterPath,double rating,String genreIds){
        TvShowsWatch tvShowsWatch = new TvShowsWatch(
                tvId,
                name,
                posterPath,
                rating,
                genreIds,
                Calendar.getInstance().getTimeInMillis());
        tvShowsWatch.save();
    }

    /**
     * It get all the tv shows that have been added to the watchlist.
     * @return It return all the tv shows as list.
     */
    public static List<TvShowsWatch> getAllTvShows(){
        return TvShowsWatch.listAll(TvShowsWatch.class);
    }

}
