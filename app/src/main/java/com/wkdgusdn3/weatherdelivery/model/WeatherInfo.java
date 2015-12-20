package com.wkdgusdn3.weatherdelivery.model;

public class WeatherInfo {
    private String hour;  // 시간
    private String temp;  // 온도
    private String wfKor; // 상태
    private String pop; // 강수확률
    private String reh; // 습도

    public String getReh() {
        return reh;
    }

    public void setReh(String reh) {
        this.reh = reh;
    }

    public String getPop() {
        return pop;
    }

    public void setPop(String pop) {
        this.pop = pop;
    }

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
