package com.wkdgusdn3.item;

public class WeatherInfo {
    protected String hour;  // 시간
    protected String temp;  // 온도
    protected String wfKor; // 상태

    public void setHour(String hour) {
        this.hour = hour;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public void setWfKor(String wfKor) {
        this.wfKor = wfKor;
    }

    public String getHour() {
        return hour;
    }

    public String getTemp() {
        return temp;
    }

    public String getWfKor() {
        return wfKor;
    }
}
