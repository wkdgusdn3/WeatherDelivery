package com.wkdgusdn3.weatherdelivery.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Calendar;

public class BootReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

            // 저장된 시간 load
            int selectedHour = Integer.parseInt(sharedPreferences.getString("HOUR", "7"));
            int selectedMinute = Integer.parseInt(sharedPreferences.getString("MINUTE", "0"));

            // alarm 등록
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Calendar calendar = Calendar.getInstance();

            int curHour = calendar.get(Calendar.HOUR_OF_DAY);
            int curMinute = calendar.get(Calendar.MINUTE);

            if (selectedHour > curHour) {    // 오늘
                calendar.set(calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH),
                        selectedHour,
                        selectedMinute,
                        0);
            } else {
                if (selectedHour == curHour && selectedMinute >= curMinute) {   // 오늘
                    calendar.set(calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH),
                            selectedHour,
                            selectedMinute,
                            0);
                } else {    // 내일
                    calendar.set(calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH) + 1,
                            selectedHour,
                            selectedMinute,
                            0);
                }
            }

            Log.e("wkdgusdn3", calendar.get(Calendar.YEAR) + " " +
                    calendar.get(Calendar.MONTH) + " " +
                    calendar.get(Calendar.DAY_OF_MONTH) + " " +
                    calendar.get(Calendar.HOUR) + " " +
                    calendar.get(Calendar.MINUTE) + " ");

            Intent intent_alarmReceiver = new Intent(context, AlarmReceiver.class);
            PendingIntent pender = PendingIntent.getBroadcast(context, 0, intent_alarmReceiver, 0);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pender);

//            Toast.makeText(context, "BootReceiver", Toast.LENGTH_LONG).show();
//            Toast.makeText(context, calendar.get(Calendar.YEAR) + " " +
//                    calendar.get(Calendar.MONTH) + " " +
//                    calendar.get(Calendar.DAY_OF_MONTH) + " " +
//                    calendar.get(Calendar.HOUR) + " " +
//                    calendar.get(Calendar.MINUTE) + " ", Toast.LENGTH_LONG).show();

//            context.sendBroadcast(new Intent("com.wkdgusdn3.weatherdelivery.alarm.AlarmReceiver"));
        }
    }
}