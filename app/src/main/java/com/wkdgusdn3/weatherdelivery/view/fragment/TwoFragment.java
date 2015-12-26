package com.wkdgusdn3.weatherdelivery.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.wkdgusdn3.weatherdelivery.R;
import com.wkdgusdn3.weatherdelivery.controller.adapter.TodayWeatherListViewAdapter;
import com.wkdgusdn3.weatherdelivery.controller.network.ReceiveTodayWeather;

public class TwoFragment extends Fragment {

    View view;
    ListView listView_todayWeather;
    TodayWeatherListViewAdapter todayWeatherLVA;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceState) {

        view = inflater.inflate(R.layout.fragment_main_two, container, false);

        setView();
        todayWeatherLVA = new TodayWeatherListViewAdapter(view.getContext());

        new ReceiveTodayWeather(view.getContext(), listView_todayWeather, todayWeatherLVA).execute();

        return view;
    }

    private void setView() {
        listView_todayWeather = (ListView)view.findViewById(R.id.mainFragmentTwo_listView);
    }
}
