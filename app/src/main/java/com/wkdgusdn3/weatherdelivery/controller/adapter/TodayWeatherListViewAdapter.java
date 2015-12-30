package com.wkdgusdn3.weatherdelivery.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wkdgusdn3.weatherdelivery.R;
import com.wkdgusdn3.weatherdelivery.model.TodayWeatherInfo;

import java.util.ArrayList;
import java.util.Calendar;

public class TodayWeatherListViewAdapter extends BaseAdapter {
    ArrayList<TodayWeatherInfo> weathers;
    Context context;

    public TodayWeatherListViewAdapter(Context context) {
        this.context = context;

        weathers = new ArrayList<TodayWeatherInfo>();
    }

    @Override
    public int getCount() {
        return weathers.size();
    }

    @Override
    public Object getItem(int position) {
        return weathers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TodayWeatherHolder todayWeatherHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_today_weather, parent, false);
            todayWeatherHolder = new TodayWeatherHolder();

            todayWeatherHolder.textView_date = (TextView)convertView.findViewById(R.id.itemTodayWeather_date);
            todayWeatherHolder.textView_time = (TextView)convertView.findViewById(R.id.itemTodayWeather_time);
            todayWeatherHolder.imageView_icon = (ImageView)convertView.findViewById(R.id.itemTodayWeather_icon);
            todayWeatherHolder.textView_temp = (TextView)convertView.findViewById(R.id.itemTodayWeather_temp);
            todayWeatherHolder.textView_pop = (TextView)convertView.findViewById(R.id.itemTodayWeather_pop);

            convertView.setTag(todayWeatherHolder);
        } else {
            todayWeatherHolder = (TodayWeatherHolder) convertView.getTag();
        }

        TodayWeatherInfo todayWeatherInfo = (TodayWeatherInfo)weathers.get(position);

        todayWeatherHolder.textView_date.setText(setDate(todayWeatherInfo.getDay(), todayWeatherInfo.getHour()));
        todayWeatherHolder.textView_time.setText(setTime(todayWeatherInfo.getHour()));
        todayWeatherHolder.imageView_icon.setBackgroundResource(setWeatherIcon(todayWeatherInfo.getWfKor()));
        todayWeatherHolder.textView_temp.setText(todayWeatherInfo.getTemp() + "º");
        todayWeatherHolder.textView_pop.setText(todayWeatherInfo.getPop() + "%");

        return convertView;
    }

    public void addTodayWeather(TodayWeatherInfo todayWeatherInfo) {
        weathers.add(todayWeatherInfo);
    }

    class TodayWeatherHolder {
        TextView textView_date;
        TextView textView_time;
        ImageView imageView_icon;
        TextView textView_temp;
        TextView textView_pop;
    }

    private String setDate(String string_day, String string_time) {
        int time = Integer.parseInt(string_time);
        Calendar calendar = Calendar.getInstance();

        if(string_day.equals("0")) {
            if(string_time.equals("24"))
                return "내일";
            else
                return "오늘";
        } else {
            return "내일";
        }
    }

    private String setTime(String string_time) {
        int time = Integer.parseInt(string_time);

        if(time == 24) {
            return "오전 0시";
        } else if(time == 12) {
            return "오후 12시";
        } else if(time > 12) {
            time -= 12;

            return "오후 " + time + "시";
        } else {
            return "오전 " + time + "시";
        }
    }

    private int setWeatherIcon(String weather) {

        if(weather.equals("맑음")) {
            return R.drawable.sun_white;
        } else if(weather.equals("구름 조금")) {
            return R.drawable.cloud_white;
        } else if(weather.equals("구름 많음")) {
            return R.drawable.clouds_white;
        } else if(weather.equals("흐림")) {
            return R.drawable.partly_cloudy_white;
        } else if(weather.equals("비")) {
            return R.drawable.rain_white;
        } else if(weather.equals("눈/비")) {
            return R.drawable.rain_and_snow_white;
        } else if(weather.equals("눈")) {
            return R.drawable.snow_white;
        } else {
            return R.drawable.sun_white;
        }
    }
}