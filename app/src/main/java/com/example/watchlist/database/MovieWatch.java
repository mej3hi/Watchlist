package com.example.watchlist.database;

import com.orm.SugarRecord;

public class MovieWatch extends SugarRecord {

    public long movieId;
    public String name;
    public String posterPath;
    public double rating;
    public String genre;
    public long updateAt;

    public MovieWatch() {

    }

    public MovieWatch(long movieId, String name, String posterPath, double rating, String genre, long updateAt) {
        this.movieId = movieId;
        this.name = name;
        this.posterPath = posterPath;
        this.rating = rating;
        this.genre = genre;
        this.updateAt = updateAt;
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
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
