package com.example.watchlist.shareInfo;

import com.example.watchlist.themoviedb.Genre;

import java.util.List;

/**
 * Created year 2017.
 * Author:
 *  Eiríkur Kristinn Hlöðversson
 *  Martin Einar Jensen
 *
 * List of genre for tv shows and movies
 */

public class GerneList {

    private static List<Genre> genreTvList;
    private static List<Genre> genreMovieList;

    public GerneList() {
    }

    /*
    Setter and getter for genreTvList and genreMovieList
     */
    public static List<Genre> getGenreTvList() {
        return genreTvList;
    }

    public static void setGenreTvList(List<Genre> genreTvList) {
        GerneList.genreTvList = genreTvList;
    }

    public static List<Genre> getGenreMovieList() {
        return genreMovieList;
    }

    public static void setGenreMovieList(List<Genre> genreMovieList) {
        GerneList.genreMovieList = genreMovieList;
    }
}
