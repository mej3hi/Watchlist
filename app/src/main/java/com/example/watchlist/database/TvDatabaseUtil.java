package com.example.watchlist.database;


import com.example.watchlist.themoviedb.TvShow;

import java.util.Calendar;
import java.util.List;

/**
 * Class for adding and removing tv shows to and from users watchlist.
 */


public class TvDatabaseUtil {

    public static boolean isTvAddedToWatchlist(long tvId){
        List<TvShowsWatch> t = TvShowsWatch.find(TvShowsWatch.class, "tv_id = ?", String.valueOf(tvId));
        if(t.size() != 0) {
            return true;

        }else{
            return false;
        }
    }

    public static boolean removeTvFromWatchlist(long tvId){
        List<TvShowsWatch> t = TvShowsWatch.find(TvShowsWatch.class, "tv_id = ?", String.valueOf(tvId));
        if(t.size() != 0) {
            t.get(0).delete();
            return true;

        }else{
            return false;
        }
    }

    public static void addTvToWatchlist(long tvId,String name,String posterPath,double rating,String genreId){
        TvShowsWatch tvShowsWatch = new TvShowsWatch(
                tvId,
                name,
                posterPath,
                rating,
                genreId,
                Calendar.getInstance().getTimeInMillis());
        tvShowsWatch.save();
    }

    public static List<TvShowsWatch> getallTvShows(){
        return TvShowsWatch.listAll(TvShowsWatch.class);
    }

}
