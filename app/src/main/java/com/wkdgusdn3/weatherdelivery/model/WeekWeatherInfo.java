package com.wkdgusdn3.weatherdelivery.model;

public class WeekWeatherInfo {
    private String tmEf;
    private String wf;
    private String tmn;
    private String tmx;

    private String month;
    private String day;

    public WeekWeatherInfo() {}

    public WeekWeatherInfo(String tmEf, String wf, String tmn, String tmx) {
        this.tmEf = tmEf;
        this.wf = wf;
        this.tmn = tmn;
        this. tmx = tmx;
    }

    public String getTmEf() {
        return tmEf;
    }

    public void setTmEf(String tmEf) {
        this.tmEf = tmEf;
    }

    public String getWf() {
        return wf;
    }

    public void setWf(String wf) {
        this.wf = wf;
    }

    public String getTmn() {
        return tmn;
    }

    public void setTmn(String tmn) {
        this.tmn = tmn;
    }

    public String getTmx() {
        return tmx;
    }

    public void setTmx(String tmx) {
        this.tmx = tmx;
    }
}