package com.wkdgusdn3.weather;

import android.content.Context;
import android.util.Log;

import java.util.Calendar;

public class CheckTime {

    Context context;

    public CheckTime(Context context) {
        this.context = context;
        compareTime();
    }

    void compareTime() {
        Calendar curTime = Calendar.getInstance();

        String hour = Calendar.HOUR_OF_DAY + "";
        String minute = Calendar.MINUTE + "";
        String second = Calendar.SECOND + "";
        Log.e("wkdgusdn3", "0");

//        if(hour.equals("7") && minute.equals("0") && second.equals("0")) {
        if(true) {
            Log.e("wkdgusdn3", "0-1");
            new ReceiveWeather(context).execute();
        }
    }
}
