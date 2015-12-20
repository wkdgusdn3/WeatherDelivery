package com.wkdgusdn3.weatherdelivery.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wkdgusdn3.weatherdelivery.R;
import com.wkdgusdn3.weatherdelivery.controller.ReceiveTodayWeather;
import com.wkdgusdn3.weatherdelivery.manager.InfoManager;

import java.util.ArrayList;

public class TwoFragment extends Fragment {

    View view;
    TextView textView_location;
    ArrayList<TextView> textViewList_date = new ArrayList<TextView>();
    ArrayList<TextView> textViewList_time = new ArrayList<TextView>();
    ArrayList<ImageView> imageViewList_weatherIcon = new ArrayList<ImageView>();
    ArrayList<TextView> textViewList_temperature = new ArrayList<TextView>();
    ArrayList<TextView> textViewList_rainFallProbability = new ArrayList<TextView>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceState) {

        view = inflater.inflate(R.layout.fragment_main_two, container, false);

        setView();

        new ReceiveTodayWeather(view.getContext(), textViewList_date,
                textViewList_time, imageViewList_weatherIcon,
                textViewList_temperature, textViewList_rainFallProbability).execute();
        textView_location.setText(InfoManager.city);

        return view;
    }

    private void setView() {

        textViewList_date.clear();
        textViewList_time.clear();
        imageViewList_weatherIcon.clear();
        textViewList_temperature.clear();
        textViewList_rainFallProbability.clear();

        textView_location = (TextView)view.findViewById(R.id.mainFragmentTwo_location);
        textViewList_date.add((TextView)view.findViewById(R.id.mainFragmentTwo_date1));
        textViewList_date.add((TextView)view.findViewById(R.id.mainFragmentTwo_date2));
        textViewList_date.add((TextView)view.findViewById(R.id.mainFragmentTwo_date3));
        textViewList_date.add((TextView)view.findViewById(R.id.mainFragmentTwo_date4));
        textViewList_date.add((TextView)view.findViewById(R.id.mainFragmentTwo_date5));
        textViewList_date.add((TextView)view.findViewById(R.id.mainFragmentTwo_date6));
        textViewList_date.add((TextView)view.findViewById(R.id.mainFragmentTwo_date7));
        textViewList_date.add((TextView)view.findViewById(R.id.mainFragmentTwo_date8));
        textViewList_time.add((TextView)view.findViewById(R.id.mainFragmentTwo_time1));
        textViewList_time.add((TextView)view.findViewById(R.id.mainFragmentTwo_time2));
        textViewList_time.add((TextView)view.findViewById(R.id.mainFragmentTwo_time3));
        textViewList_time.add((TextView)view.findViewById(R.id.mainFragmentTwo_time4));
        textViewList_time.add((TextView)view.findViewById(R.id.mainFragmentTwo_time5));
        textViewList_time.add((TextView)view.findViewById(R.id.mainFragmentTwo_time6));
        textViewList_time.add((TextView)view.findViewById(R.id.mainFragmentTwo_time7));
        textViewList_time.add((TextView)view.findViewById(R.id.mainFragmentTwo_time8));
        imageViewList_weatherIcon.add((ImageView) view.findViewById(R.id.mainFragmentTwo_weatherIcon1));
        imageViewList_weatherIcon.add((ImageView) view.findViewById(R.id.mainFragmentTwo_weatherIcon2));
        imageViewList_weatherIcon.add((ImageView)view.findViewById(R.id.mainFragmentTwo_weatherIcon3));
        imageViewList_weatherIcon.add((ImageView)view.findViewById(R.id.mainFragmentTwo_weatherIcon4));
        imageViewList_weatherIcon.add((ImageView)view.findViewById(R.id.mainFragmentTwo_weatherIcon5));
        imageViewList_weatherIcon.add((ImageView)view.findViewById(R.id.mainFragmentTwo_weatherIcon6));
        imageViewList_weatherIcon.add((ImageView)view.findViewById(R.id.mainFragmentTwo_weatherIcon7));
        imageViewList_weatherIcon.add((ImageView)view.findViewById(R.id.mainFragmentTwo_weatherIcon8));
        textViewList_temperature.add((TextView)view.findViewById(R.id.mainFragmentTwo_temperature1));
        textViewList_temperature.add((TextView)view.findViewById(R.id.mainFragmentTwo_temperature2));
        textViewList_temperature.add((TextView)view.findViewById(R.id.mainFragmentTwo_temperature3));
        textViewList_temperature.add((TextView)view.findViewById(R.id.mainFragmentTwo_temperature4));
        textViewList_temperature.add((TextView)view.findViewById(R.id.mainFragmentTwo_temperature5));
        textViewList_temperature.add((TextView)view.findViewById(R.id.mainFragmentTwo_temperature6));
        textViewList_temperature.add((TextView)view.findViewById(R.id.mainFragmentTwo_temperature7));
        textViewList_temperature.add((TextView)view.findViewById(R.id.mainFragmentTwo_temperature8));
        textViewList_rainFallProbability.add((TextView)view.findViewById(R.id.mainFragmentTwo_rainFallProbability1));
        textViewList_rainFallProbability.add((TextView)view.findViewById(R.id.mainFragmentTwo_rainFallProbability2));
        textViewList_rainFallProbability.add((TextView)view.findViewById(R.id.mainFragmentTwo_rainFallProbability3));
        textViewList_rainFallProbability.add((TextView)view.findViewById(R.id.mainFragmentTwo_rainFallProbability4));
        textViewList_rainFallProbability.add((TextView)view.findViewById(R.id.mainFragmentTwo_rainFallProbability5));
        textViewList_rainFallProbability.add((TextView)view.findViewById(R.id.mainFragmentTwo_rainFallProbability6));
        textViewList_rainFallProbability.add((TextView)view.findViewById(R.id.mainFragmentTwo_rainFallProbability7));
        textViewList_rainFallProbability.add((TextView)view.findViewById(R.id.mainFragmentTwo_rainFallProbability8));
    }
}
