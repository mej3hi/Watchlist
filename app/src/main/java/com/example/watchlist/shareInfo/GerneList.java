package com.example.watchlist.shareInfo;

import com.example.watchlist.themoviedb.Genre;

import java.util.List;

/**
 * List of genre for tv shows and movies
 */

public class GerneList {

    private static List<Genre> genreTvList;
    private static List<Genre> genreMovieList;

    public GerneList() {
    }

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
