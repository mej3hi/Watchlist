package com.example.watchlist.service.response.tvShows;

import com.example.watchlist.themoviedb.Genre;

/**
 * Is the response object for the tv shows genre.
 */

public class ResTvGenre {

    private Genre.GenreResults genreResults;
    private int code;

    public ResTvGenre(Genre.GenreResults genreResults, int code) {
        this.genreResults = genreResults;
        this.code = code;
    }


    public Genre.GenreResults getGenreResults() {
        return genreResults;
    }

    public void setGenreResults(Genre.GenreResults genreResults) {
        this.genreResults = genreResults;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
