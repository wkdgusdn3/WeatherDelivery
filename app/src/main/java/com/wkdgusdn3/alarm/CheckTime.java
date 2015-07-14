package com.wkdgusdn3.alarm;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Calendar;

public class CheckTime {

    Context context;

    public CheckTime(Context context) {
        this.context = context;
        compareTime();
    }

    void compareTime() {
        Calendar curTime = Calendar.getInstance();

        String curHour = curTime.get(Calendar.HOUR_OF_DAY) + "";
        String curMinute = curTime.get(Calendar.MINUTE) + "";

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String setHour = sharedPreferences.getString("HOUR", "7");
        String setMinute = sharedPreferences.getString("MINUTE", "0");

        if(curHour.equals(setHour) && curMinute.equals(setMinute)) {
            new AlarmReceiveWeather(context).execute();
        }
    }
}
