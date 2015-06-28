package com.wkdgusdn3.alarm;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.wkdgusdn3.weather.CheckTime;

import java.util.Timer;
import java.util.TimerTask;

public class AlarmService extends Service{

    @Override
    public void onCreate() {
        super.onCreate();

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                CheckTime checkTime = new CheckTime(getApplicationContext());
            }
        };

        timer.schedule(timerTask, 1000, 18000000);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

}
