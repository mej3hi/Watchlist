package com.example.watchlist.service.response.tvShows;

import com.example.watchlist.themoviedb.TvShow;

/**
 * Is the response object for the popular tv shows.
 */

public class ResPopularTvShows {
    private TvShow.TvShowsResults tvShowsResults;
    private int code;

    public ResPopularTvShows(TvShow.TvShowsResults tvShowsResults, int code) {
        this.tvShowsResults = tvShowsResults;
        this.code = code;
    }

    public TvShow.TvShowsResults getTvShowsResults() {
        return tvShowsResults;
    }

    public void setTvShowsResults(TvShow.TvShowsResults tvShowsResults) {
        this.tvShowsResults = tvShowsResults;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
