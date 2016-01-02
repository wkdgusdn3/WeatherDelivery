package com.wkdgusdn3.weatherdelivery.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class InfoManager {

    public static String city;
    public static String city2;
    public static String cityCode;
    public static String cityCode2;
    public static String hour;
    public static String minute;

    public static void setData(Context context) {
        SharedPreferences sharedPreferences;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        city = sharedPreferences.getString("CITY", "서울특별시 중구 황학동");
        city2 = sharedPreferences.getString("CITY2", "서울");
        cityCode = sharedPreferences.getString("CITYCODE", "1100000000");
        cityCode2 = sharedPreferences.getString("CITYCODE2", "109");
        hour = sharedPreferences.getString("HOUR", "7");
        minute = sharedPreferences.getString("MINUTE", "0");
    }

}
