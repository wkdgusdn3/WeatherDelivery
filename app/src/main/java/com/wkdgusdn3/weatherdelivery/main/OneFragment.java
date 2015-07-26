package com.wkdgusdn3.weatherdelivery.main;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wkdgusdn3.weatherdelivery.R;
import com.wkdgusdn3.weatherdelivery.manager.InfoManager;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class OneFragment extends Fragment {

    View view;
    Activity activity;
    TextView textView_location;
    TextView textView_time;
    TextView textView_ampm;
    TextView textView_temperature;
    TextView textView_humidity;
    TextView textView_rainfallProbability;
    TextView textView_weatherText;
    ImageView imageView_weatherIcon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_one, container, false);


        setVariable();
        textView_location.setText(InfoManager.city);
        setTime();

        new ReceiveCurrentWeather(view.getContext(), textView_temperature,
                textView_humidity, textView_rainfallProbability,
                textView_weatherText, imageView_weatherIcon).execute();

        return view;
    }

    private void setVariable() {
        activity = getActivity();
        textView_location = (TextView) view.findViewById(R.id.mainFragmentOne_location);
        textView_time = (TextView) view.findViewById(R.id.mainFragmentOne_time);
        textView_ampm = (TextView) view.findViewById(R.id.mainFragmentOne_ampm);
        textView_temperature = (TextView) view.findViewById(R.id.mainFragmentOne_temperature);
        textView_humidity = (TextView) view.findViewById(R.id.mainFragmentOne_humidity);
        textView_rainfallProbability = (TextView) view.findViewById(R.id.mainFragmentOne_rainfallProbability);
        textView_weatherText = (TextView) view.findViewById(R.id.mainFragmentOne_weatherText);
        imageView_weatherIcon = (ImageView) view.findViewById(R.id.mainFragmentOne_weatherIcon);
    }

    private void setTime() {

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

        timer.schedule(timerTask, 0, 1000);
    }

    private String changeHour(String hour) {
        int int_hour = Integer.parseInt(hour);

        if (int_hour == 0 || int_hour == 12) {
            return "12";
        } else if (int_hour < 10) {
            return "0" + Integer.toString(int_hour);
        } else if (int_hour < 12) {
            return Integer.toString(int_hour);
        } else {
            return Integer.toString(int_hour - 12);
        }
    }

    private String changeMinute(String minute) {
        int int_minute = Integer.parseInt(minute);

        if (int_minute < 10) {
            return "0" + Integer.toString(int_minute);
        } else {
            return Integer.toString(int_minute);
        }

    }

    private String changeAMPM(String hour) {
        int int_hour = Integer.parseInt(hour);

        if (int_hour < 12) {
            return "AM";
        } else {
            return "PM";
        }
    }
}
