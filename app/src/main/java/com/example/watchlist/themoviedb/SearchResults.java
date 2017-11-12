package com.example.watchlist.themoviedb;

import java.util.List;

public class SearchResults {

    private long id;
    private String name;
    private double rating;
    private String PosterPath;
    private List<Integer> genreIds = null;
    private String mediaType;

    public SearchResults() {
    }

    public SearchResults(long id, String name, double rating, String posterPath, List<Integer> genreIds, String mediaType) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        PosterPath = posterPath;
        this.genreIds = genreIds;
        this.mediaType = mediaType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public String getPosterPath() {
        return PosterPath;
    }

    public void setPosterPath(String posterPath) {
        PosterPath = posterPath;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }
}
