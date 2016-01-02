package com.wkdgusdn3.weatherdelivery.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.wkdgusdn3.weatherdelivery.R;
import com.wkdgusdn3.weatherdelivery.controller.adapter.WeekWeatherListViewAdapter;
import com.wkdgusdn3.weatherdelivery.controller.network.ReceiveWeekWeather;

public class ThreeFragment extends Fragment {

    View view;
    ListView listView_weekWeather;
    WeekWeatherListViewAdapter weekWeatherLVA;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceState) {

        view = inflater.inflate(R.layout.fragment_main_three, container, false);

        setView();
        weekWeatherLVA = new WeekWeatherListViewAdapter(view.getContext());

        new ReceiveWeekWeather(view.getContext(), listView_weekWeather,
                weekWeatherLVA, progressBar).execute();


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setView() {
        listView_weekWeather = (ListView)view.findViewById(R.id.mainFragmentThree_listView);
        progressBar = (ProgressBar)view.findViewById(R.id.mainFragmentThree_progressBar);
    }
}
