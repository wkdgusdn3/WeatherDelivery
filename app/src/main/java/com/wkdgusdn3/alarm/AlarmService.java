package com.wkdgusdn3.alarm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.wkdgusdn3.weather.CheckTime;
import com.wkdgusdn3.weatherdelivery.MainActivity;

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

        timer.schedule(timerTask, 1000, 3600000);

        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, new Intent(getApplicationContext(), MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

}
