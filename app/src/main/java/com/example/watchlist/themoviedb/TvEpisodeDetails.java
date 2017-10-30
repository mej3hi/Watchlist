package com.example.watchlist.themoviedb;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by martin on 30.10.2017.
 */

public class TvEpisodeDetails {
    @SerializedName("air_date")
    @Expose
    private String airDate;
    @SerializedName("crew")
    @Expose
    private List<Crew> crew = null;
    @SerializedName("episode_number")
    @Expose
    private int episodeNumber;
    @SerializedName("guest_stars")
    @Expose
    private List<GuestStar> guestStars = null;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("production_code")
    @Expose
    private String productionCode;
    @SerializedName("season_number")
    @Expose
    private int seasonNumber;
    @SerializedName("still_path")
    @Expose
    private String stillPath;
    @SerializedName("vote_average")
    @Expose
    private double voteAverage;
    @SerializedName("vote_count")
    @Expose
    private int voteCount;

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public List<Crew> getCrew() {
        return crew;
    }

    public void setCrew(List<Crew> crew) {
        this.crew = crew;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public List<GuestStar> getGuestStars() {
        return guestStars;
    }

    public void setGuestStars(List<GuestStar> guestStars) {
        this.guestStars = guestStars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductionCode() {
        return productionCode;
    }

    public void setProductionCode(String productionCode) {
        this.productionCode = productionCode;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public String getStillPath() {
        return stillPath;
    }

    public void setStillPath(String stillPath) {
        this.stillPath = stillPath;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }


    public static class Crew {

        @SerializedName("id")
        @Expose
        private long id;
        @SerializedName("credit_id")
        @Expose
        private String creditId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("department")
        @Expose
        private String department;
        @SerializedName("job")
        @Expose
        private String job;
        @SerializedName("profile_path")
        @Expose
        private String profilePath;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getCreditId() {
            return creditId;
        }

        public void setCreditId(String creditId) {
            this.creditId = creditId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public String getProfilePath() {
            return profilePath;
        }

        public void setProfilePath(String profilePath) {
            this.profilePath = profilePath;
        }

    }


    public static class GuestStar {

        @SerializedName("id")
        @Expose
        private long id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("credit_id")
        @Expose
        private String creditId;
        @SerializedName("character")
        @Expose
        private String character;
        @SerializedName("order")
        @Expose
        private int order;
        @SerializedName("profile_path")
        @Expose
        private String profilePath;

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

        public String getCreditId() {
            return creditId;
        }

        public void setCreditId(String creditId) {
            this.creditId = creditId;
        }

        public String getCharacter() {
            return character;
        }

        public void setCharacter(String character) {
            this.character = character;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public String getProfilePath() {
            return profilePath;
        }

        public void setProfilePath(String profilePath) {
            this.profilePath = profilePath;
        }

    }

}
