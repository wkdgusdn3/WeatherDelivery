package com.wkdgusdn3.weatherdelivery.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wkdgusdn3.weatherdelivery.R;
import com.wkdgusdn3.weatherdelivery.controller.network.ReceiveTodayWeather;
import com.wkdgusdn3.weatherdelivery.controller.network.ReceiveWeekWeather;

public class ThreeFragment extends Fragment {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceState) {

        view = inflater.inflate(R.layout.fragment_main_three, container, false);

        new ReceiveWeekWeather(view.getContext()).execute();

        return view;
    }
}
