package com.wkdgusdn3.weatherdelivery.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wkdgusdn3.weatherdelivery.R;

public class ThreeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceState) {

        View v = inflater.inflate(R.layout.fragment_main_three, container, false);

        return v;
    }
}