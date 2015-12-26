package com.wkdgusdn3.weatherdelivery.model;

/**
 * Created by HyunWoo on 2015-12-26.
 */
public class Time {
    String month;
    String day;
    String time;

    public Time(String month, String day, String time) {
        this.month = month;
        this.day = day;
        this.time = time;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
