package com.example.watchlist.database;

import com.orm.SugarRecord;

public class TvShowsWatch extends SugarRecord {

    public long tvId;
    public String name;
    public String posterPath;
    public double rating;
    public String genre;
    public long updateAt;


    public TvShowsWatch() {

    }

    public TvShowsWatch(long tvId, String name, String posterPath, double rating, String genre, long updateAt) {
        this.tvId = tvId;
        this.name = name;
        this.posterPath = posterPath;
        this.rating = rating;
        this.genre = genre;
        this.updateAt = updateAt;
    }

    public long getTvId() {
        return tvId;
    }

    public void setTvId(long tvId) {
        this.tvId = tvId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public long getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(long updateAt) {
        this.updateAt = updateAt;
    }
}
