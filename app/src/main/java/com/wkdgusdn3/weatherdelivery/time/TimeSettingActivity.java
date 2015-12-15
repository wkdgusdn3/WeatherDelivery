package com.wkdgusdn3.weatherdelivery.time;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.wkdgusdn3.weatherdelivery.R;
import com.wkdgusdn3.weatherdelivery.alarm.AlarmReceiver;
import com.wkdgusdn3.weatherdelivery.manager.InfoManager;

import java.util.ArrayList;
import java.util.Calendar;

public class TimeSettingActivity extends ActionBarActivity {

    ArrayList<String> array_hour;
    ArrayList<String> array_minute;
    ArrayAdapter<String> adapter_hour;
    ArrayAdapter<String> adapter_minute;
    Spinner spinner_hour;
    Spinner spinner_minute;
    Button button_apply;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_setting);

        context = getApplicationContext();

        setVariable();
        setOnClickListener();
    }

    void setVariable() {
        array_hour = new ArrayList<String>();
        array_minute = new ArrayList<String>();

        setArrayList();

        spinner_hour = (Spinner) findViewById(R.id.timeSetting_hour);
        spinner_minute = (Spinner) findViewById(R.id.timeSetting_minute);
        button_apply = (Button) findViewById(R.id.timeSetting_apply);

        adapter_hour = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, array_hour);
        adapter_minute = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, array_minute);

        spinner_hour.setAdapter(adapter_hour);
        spinner_minute.setAdapter(adapter_minute);
    }

    void setOnClickListener() {
        button_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedHour = Integer.parseInt(spinner_hour.getSelectedItem().toString());
                int selectedMinute = Integer.parseInt(spinner_minute.getSelectedItem().toString());

                // 저장된 시간 저장
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("HOUR", selectedHour + "");
                editor.putString("MINUTE", selectedMinute + "");
                editor.commit();

                InfoManager.setData(getApplicationContext());

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

                Intent intent = new Intent(context, AlarmReceiver.class);
                PendingIntent pender = PendingIntent.getBroadcast(context, 0, intent, 0);
                alarmManager.cancel(pender);
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pender);

                Toast.makeText(getApplicationContext(),
                        selectedHour + "시 " +
                                selectedMinute + "분에 날씨 배달이 예약되었습니다",
                        Toast.LENGTH_LONG).show();

                finish();
            }
        });
    }

    void setArrayList() {
        array_hour.add("0");
        array_hour.add("1");
        array_hour.add("2");
        array_hour.add("3");
        array_hour.add("4");
        array_hour.add("5");
        array_hour.add("6");
        array_hour.add("7");
        array_hour.add("8");
        array_hour.add("9");
        array_hour.add("10");
        array_hour.add("11");
        array_hour.add("12");
        array_hour.add("13");
        array_hour.add("14");
        array_hour.add("15");
        array_hour.add("16");
        array_hour.add("17");
        array_hour.add("18");
        array_hour.add("19");
        array_hour.add("20");
        array_hour.add("21");
        array_hour.add("22");
        array_hour.add("23");

        array_minute.add("0");
        array_minute.add("1");
        array_minute.add("2");
        array_minute.add("3");
        array_minute.add("4");
        array_minute.add("5");
        array_minute.add("6");
        array_minute.add("7");
        array_minute.add("8");
        array_minute.add("9");
        array_minute.add("10");
        array_minute.add("11");
        array_minute.add("12");
        array_minute.add("13");
        array_minute.add("14");
        array_minute.add("15");
        array_minute.add("16");
        array_minute.add("17");
        array_minute.add("18");
        array_minute.add("19");
        array_minute.add("20");
        array_minute.add("21");
        array_minute.add("22");
        array_minute.add("23");
        array_minute.add("24");
        array_minute.add("25");
        array_minute.add("26");
        array_minute.add("27");
        array_minute.add("28");
        array_minute.add("29");
        array_minute.add("30");
        array_minute.add("31");
        array_minute.add("32");
        array_minute.add("33");
        array_minute.add("34");
        array_minute.add("35");
        array_minute.add("36");
        array_minute.add("37");
        array_minute.add("38");
        array_minute.add("39");
        array_minute.add("40");
        array_minute.add("41");
        array_minute.add("42");
        array_minute.add("43");
        array_minute.add("44");
        array_minute.add("45");
        array_minute.add("46");
        array_minute.add("47");
        array_minute.add("48");
        array_minute.add("49");
        array_minute.add("50");
        array_minute.add("51");
        array_minute.add("52");
        array_minute.add("53");
        array_minute.add("54");
        array_minute.add("55");
        array_minute.add("56");
        array_minute.add("57");
        array_minute.add("58");
        array_minute.add("59");
    }
}
