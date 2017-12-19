package com.example.watchlist.utils;

import java.util.Calendar;

/**
 * Created year 2017.
 * Author:
 *  Eiríkur Kristinn Hlöðversson
 *  Martin Einar Jensen
 */
public class Time {

    public final long ONE_HOUR = 3600000;
    public final long TWELVE_HOUR = 3600000*12;
    private final Calendar calendar = Calendar.getInstance();

    private long firstTime;

    /**
     * Constructor set the time now.
     */
    public Time() {
        this.firstTime = calendar.getTimeInMillis();
    }

    public long getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(long firstTime) {
        this.firstTime = firstTime;
    }


    /**
     * Check whether the time is over.
     * @param time Time is long
     * @return return boolean
     */
    public boolean isOverTime(long time){
        return firstTime + time < calendar.getTimeInMillis();
    }

    /**
     * Check whether the time is over.
     * @param ComperTime ComperTime is long
     * @param time Time is long
     * @return return boolean.
     */
    public boolean isOverTime(long ComperTime, long time){
        return ComperTime + time < calendar.getTimeInMillis();
    }

    /**
     * Get the time in millisecond
     * @return return long
     */
    public long getTimeInMillis(){
        return calendar.getTimeInMillis();
    }
}
