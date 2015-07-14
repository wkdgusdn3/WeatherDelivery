package com.wkdgusdn3.weatherdelivery;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.wkdgusdn3.alarm.AlarmService;
import com.wkdgusdn3.location.LocationSettingActivity;
import com.wkdgusdn3.manager.InfoManager;
import com.wkdgusdn3.time.TimeSettingActivity;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends ActionBarActivity {

    TextView textView_info;
    ListView listView;
    String[] item = {"시간설정", "지역설정"};
    TextView textView_time;
    TextView textView_ampm;
    TextView textView_temperature;
    TextView textView_humidity;
    TextView textView_rainfallProbability;
    ImageView imageView_weatherIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InfoManager.setData(getApplicationContext());

        setVariable();
        setTime();

        textView_info.setText(InfoManager.city + "지역의 날씨를\n" +
                InfoManager.hour + "시 " + InfoManager.minute + "분에 배달합니다.");

        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, item));
        listView.setOnItemClickListener(new DrawerItemClickListener());

        new ReceiveWeather(getApplicationContext(), textView_temperature,
                textView_humidity, textView_rainfallProbability,
                imageView_weatherIcon).execute();
        startService(new Intent(getApplicationContext(), AlarmService.class));

    }

    @Override
    protected void onResume() {
        super.onResume();

        textView_info.setText(InfoManager.city + "지역의 날씨를\n" +
        InfoManager.hour + "시 " + InfoManager.minute + "분에 배달합니다.");
    }

    void setVariable() {
        textView_info = (TextView) findViewById(R.id.main_info);
        listView = (ListView) findViewById(R.id.main_list);
        textView_time = (TextView) findViewById(R.id.main_time);
        textView_ampm = (TextView)findViewById(R.id.main_ampm);
        textView_temperature = (TextView)findViewById(R.id.main_temperature);
        textView_humidity = (TextView)findViewById(R.id.main_humidity);
        textView_rainfallProbability = (TextView)findViewById(R.id.main_rainfallProbability);
        imageView_weatherIcon = (ImageView)findViewById(R.id.main_weatherIcon);
    }

    void setTime() {

        Timer timer = new Timer();
        final Handler handler = new Handler();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                final Calendar calendar = Calendar.getInstance();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        textView_time.setText(changeHour("" + calendar.get(Calendar.HOUR_OF_DAY)) +
                                ":" +
                                changeMinute("" + calendar.get(Calendar.MINUTE)));
                        textView_ampm.setText(changeAMPM("" + calendar.get(Calendar.HOUR_OF_DAY)));
                    }
                });
            }
        };

        timer.schedule(timerTask, 1000, 1000);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position,
                                long id) {
            switch (position) {
                case 0:
                    Intent timeSettingActivity = new Intent(getApplicationContext(), TimeSettingActivity.class);
                    startActivity(timeSettingActivity);
                    break;
                case 1:
                    Intent locationSettingAcivity = new Intent(getApplicationContext(), LocationSettingActivity.class);
                    startActivity(locationSettingAcivity);
                    break;
            }
        }
    }

    private String changeHour(String hour) {
        int int_hour = Integer.parseInt(hour);

        if(int_hour == 0 || int_hour == 12) {
            return "12";
        } else if(int_hour < 10) {
            return "0" + Integer.toString(int_hour);
        } else if(int_hour < 12){
            return Integer.toString(int_hour);
        } else {
            return Integer.toString(int_hour - 12);
        }
    }

    private String changeMinute(String minute) {
        int int_minute = Integer.parseInt(minute);

        if(int_minute < 10) {
            return "0" + Integer.toString(int_minute);
        } else {
            return Integer.toString(int_minute);
        }

    }

    private String changeAMPM(String hour) {
        int int_hour = Integer.parseInt(hour);

        if(int_hour < 12) {
            return "AM";
        } else {
            return "PM";
        }
    }
}
