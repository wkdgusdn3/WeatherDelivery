package com.wkdgusdn3.weatherdelivery.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wkdgusdn3.weatherdelivery.R;
import com.wkdgusdn3.weatherdelivery.model.WeekWeatherInfo;

import java.util.ArrayList;

public class WeekWeatherListViewAdapter extends BaseAdapter {
    ArrayList<WeekWeatherInfo> weathers;
    Context context;

    public WeekWeatherListViewAdapter(Context context) {
        this.context = context;

        weathers = new ArrayList<WeekWeatherInfo>();
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

        WeekWeatherHolder weekWeatherHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_week_weather, parent, false);
            weekWeatherHolder = new WeekWeatherHolder();

            weekWeatherHolder.textView_time = (TextView)convertView.findViewById(R.id.itemWeekWeather_time);
            weekWeatherHolder.imageView_icon = (ImageView)convertView.findViewById(R.id.itemWeekWeather_icon);
            weekWeatherHolder.textView_temp = (TextView)convertView.findViewById(R.id.itemWeekWeather_temp);

            convertView.setTag(weekWeatherHolder);
        } else {
            weekWeatherHolder = (WeekWeatherHolder) convertView.getTag();
        }

        WeekWeatherInfo weekWeatherInfo = (WeekWeatherInfo)weathers.get(position);

        weekWeatherHolder.textView_time.setText(weekWeatherInfo.getTmEf());
        weekWeatherHolder.imageView_icon.setBackgroundResource(setWeatherIcon(weekWeatherInfo.getWf()));
        weekWeatherHolder.textView_temp.setText(weekWeatherInfo.getTmn() + "º / " + weekWeatherInfo.getTmx() + "º");

        return convertView;
    }

    public void addWeekWeather(WeekWeatherInfo weekWeatherInfo) {
        weathers.add(weekWeatherInfo);
    }

    class WeekWeatherHolder {
        TextView textView_time;
        ImageView imageView_icon;
        TextView textView_temp;
    }

    private int setWeatherIcon(String weather) {

        if(weather.equals("맑음")) {
            return R.drawable.sun_white;
        } else if(weather.equals("구름조금")) {
            return R.drawable.cloud_white;
        } else if(weather.equals("구름 많음")) {
            return R.drawable.clouds;
        } else if(weather.equals("흐림")) {
            return R.drawable.partly_cloudy_white;
        } else if(weather.equals("비") ||
                weather.equals("구름많고 비") ||
                weather.equals("흐리고 비")) {
            return R.drawable.rain_white;
        } else if(weather.equals("눈/비") ||
                weather.equals("구름많고 비/눈") ||
                weather.equals("구름많고 눈/비") ||
                weather.equals("흐리고 비/눈") ||
                weather.equals("흐리고 눈/비")) {
            return R.drawable.rain_and_snow_white;
        } else if(weather.equals("눈") ||
                weather.equals("구름많고 눈") ||
                weather.equals("흐리고 눈")) {
            return R.drawable.snow_white;
        } else {
            return R.drawable.sun_white;
        }
    }
}