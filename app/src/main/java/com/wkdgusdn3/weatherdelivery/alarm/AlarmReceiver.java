package com.wkdgusdn3.weatherdelivery.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by HyunWoo on 2015-12-15.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        new AlarmReceiveWeather(context).execute();
    }
}
