package com.wkdgusdn3.weatherdelivery.view.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wkdgusdn3.weatherdelivery.R;
import com.wkdgusdn3.weatherdelivery.controller.network.ReceiveCurrentWeather;

public class OneFragment extends Fragment {

    View view;
    Activity activity;
    TextView textView_temperature;
    TextView textView_humidity;
    TextView textView_rainfallProbability;
    TextView textView_weatherText;
    ImageView imageView_weatherIcon;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_one, container, false);


        setVariable();

        new ReceiveCurrentWeather(view.getContext(), textView_temperature,
                textView_humidity, textView_rainfallProbability,
                textView_weatherText, imageView_weatherIcon,
                progressBar).execute();

        return view;
    }

    private void setVariable() {
        activity = getActivity();
        textView_temperature = (TextView) view.findViewById(R.id.mainFragmentOne_temperature);
        textView_humidity = (TextView) view.findViewById(R.id.mainFragmentOne_humidity);
        textView_rainfallProbability = (TextView) view.findViewById(R.id.mainFragmentOne_rainfallProbability);
        textView_weatherText = (TextView) view.findViewById(R.id.mainFragmentOne_weatherText);
        imageView_weatherIcon = (ImageView) view.findViewById(R.id.mainFragmentOne_weatherIcon);
        progressBar = (ProgressBar)view.findViewById(R.id.mainFragmentOne_progressBar);
    }
}
