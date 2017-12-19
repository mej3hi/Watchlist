package com.example.watchlist.themoviedb;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created year 2017.
 * Author:
 *  Eiríkur Kristinn Hlöðversson
 *  Martin Einar Jensen
 */
public class TvShow {
    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("vote_average")
    @Expose
    private double voteAverage;
    @SerializedName("genre_ids")
    @Expose
    private List<Integer> genreIds = null;
    @SerializedName("name")
    @Expose
    private String name;

    public TvShow() {
    }

    public TvShow(long id, String name, String posterPath, double voteAverage, List<Integer> genreIds ) {
        this.posterPath = posterPath;
        this.id = id;
        this.voteAverage = voteAverage;
        this.genreIds = genreIds;
        this.name = name;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




    public static class TvShowsResults{

        @SerializedName("page")
        @Expose
        private int page;
        @SerializedName("results")
        @Expose
        private List<TvShow> results = null;
        @SerializedName("total_pages")
        @Expose
        private int totalPages;

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public List<TvShow> getResults() {
            return results;
        }

        public void setResults(List<TvShow> results) {
            this.results = results;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }
    }

}
