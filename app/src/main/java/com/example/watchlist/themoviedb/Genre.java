package com.example.watchlist.themoviedb;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Genre {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public static class GenreResults {

        @SerializedName("genres")
        @Expose
        private List<Genre> results = null;

        public List<Genre> getResults() {
            return results;
        }

        public void setResults(List<Genre> results){
            this.results = results;
        }

    }

}
