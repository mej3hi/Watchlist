package com.example.watchlist.utils;

import java.util.Calendar;

/**
 * Created by martin on 15.10.2017.
 */

public class Time {

    public final long ONE_HOUR = 3600000;
    public final long TWELVE_HOUR = 3600000*12;
    private final Calendar calendar = Calendar.getInstance();

    private long firstTime;

    public Time() {
        this.firstTime = calendar.getTimeInMillis();
    }

    public boolean isOverTime(long time){
        return firstTime + time < calendar.getTimeInMillis();
    }

    public boolean isOverTime(long ComperTime, long time){
        return ComperTime + time < calendar.getTimeInMillis();
    }


    public long getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(long firstTime) {
        this.firstTime = firstTime;
    }

    public long getTimeInMillis(){
        return calendar.getTimeInMillis();
    }
}
