package com.example.watchlist.shareInfo;

import com.example.watchlist.themoviedb.Movie;
import com.example.watchlist.themoviedb.TvShow;

import java.util.ArrayList;
import java.util.List;

public class Cache {


    public static class TodayTvshows {
        private static List<TvShow> tvShowList = new ArrayList<>();
        private static long time;

        public static void addToTvShows(List<TvShow> list){
            TodayTvshows.tvShowList.addAll(list);
        }

        public static List<TvShow> getTvShowList() {
            return tvShowList;
        }

        public static long getTime() {
            return time;
        }

        public static void setTime(long time) {
            TodayTvshows.time = time;
        }

        public static boolean isEmpty(){
            return tvShowList.isEmpty();
        }

        public static void clear(){
            tvShowList.clear();

        }
    }

    public static class NowPlayingMovie {
        private static List<Movie> movieList = new ArrayList<>();
        private static long time;

        public static void addToMovie(List<Movie> list){
            NowPlayingMovie.movieList.addAll(list);
        }

        public static List<Movie> getMovieList() {
            return movieList;
        }

        public static long getTime() {
            return time;
        }

        public static void setTime(long time) {
            NowPlayingMovie.time = time;
        }

        public static boolean isEmpty(){
            return movieList.isEmpty();
        }

        public static void clear(){
            movieList.clear();

        }
    }

}
