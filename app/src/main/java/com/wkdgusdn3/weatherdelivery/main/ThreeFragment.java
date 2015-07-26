package com.wkdgusdn3.weatherdelivery.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wkdgusdn3.weatherdelivery.R;
import com.wkdgusdn3.weatherdelivery.manager.InfoManager;

import java.util.ArrayList;

public class ThreeFragment extends Fragment {

    View view;
    TextView textView_location;
    ArrayList<TextView> textViewList_date = new ArrayList<TextView>();
    ArrayList<ImageView> imageViewList_weatherIcon = new ArrayList<ImageView>();
    ArrayList<TextView> textViewList_temperatureMin = new ArrayList<TextView>();
    ArrayList<TextView> textViewList_temperatureMax = new ArrayList<TextView>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceState) {

        view = inflater.inflate(R.layout.fragment_main_three, container, false);

        setVariable();
        textView_location.setText(InfoManager.city);

        return view;
    }

    private void setVariable() {
        textView_location = (TextView)view.findViewById(R.id.mainFragmentThree_location);
        textViewList_date.add((TextView)view.findViewById(R.id.mainFragmentThree_date1));
        textViewList_date.add((TextView)view.findViewById(R.id.mainFragmentThree_date2));
        textViewList_date.add((TextView)view.findViewById(R.id.mainFragmentThree_date3));
        textViewList_date.add((TextView)view.findViewById(R.id.mainFragmentThree_date4));
        textViewList_date.add((TextView)view.findViewById(R.id.mainFragmentThree_date5));
        textViewList_date.add((TextView)view.findViewById(R.id.mainFragmentThree_date6));
        textViewList_date.add((TextView)view.findViewById(R.id.mainFragmentThree_date7));
        textViewList_date.add((TextView)view.findViewById(R.id.mainFragmentThree_date8));
        imageViewList_weatherIcon.add((ImageView)view.findViewById(R.id.mainFragmentThree_weatherIcon1));
        imageViewList_weatherIcon.add((ImageView)view.findViewById(R.id.mainFragmentThree_weatherIcon2));
        imageViewList_weatherIcon.add((ImageView)view.findViewById(R.id.mainFragmentThree_weatherIcon3));
        imageViewList_weatherIcon.add((ImageView)view.findViewById(R.id.mainFragmentThree_weatherIcon4));
        imageViewList_weatherIcon.add((ImageView)view.findViewById(R.id.mainFragmentThree_weatherIcon5));
        imageViewList_weatherIcon.add((ImageView)view.findViewById(R.id.mainFragmentThree_weatherIcon6));
        imageViewList_weatherIcon.add((ImageView)view.findViewById(R.id.mainFragmentThree_weatherIcon7));
        imageViewList_weatherIcon.add((ImageView)view.findViewById(R.id.mainFragmentThree_weatherIcon8));
        textViewList_temperatureMin.add((TextView)view.findViewById(R.id.mainFragmentThree_temperatureMin1));
        textViewList_temperatureMin.add((TextView)view.findViewById(R.id.mainFragmentThree_temperatureMin2));
        textViewList_temperatureMin.add((TextView)view.findViewById(R.id.mainFragmentThree_temperatureMin3));
        textViewList_temperatureMin.add((TextView)view.findViewById(R.id.mainFragmentThree_temperatureMin4));
        textViewList_temperatureMin.add((TextView)view.findViewById(R.id.mainFragmentThree_temperatureMin5));
        textViewList_temperatureMin.add((TextView)view.findViewById(R.id.mainFragmentThree_temperatureMin6));
        textViewList_temperatureMin.add((TextView)view.findViewById(R.id.mainFragmentThree_temperatureMin7));
        textViewList_temperatureMin.add((TextView)view.findViewById(R.id.mainFragmentThree_temperatureMin8));
        textViewList_temperatureMax.add((TextView)view.findViewById(R.id.mainFragmentThree_temperatureMax1));
        textViewList_temperatureMax.add((TextView)view.findViewById(R.id.mainFragmentThree_temperatureMax2));
        textViewList_temperatureMax.add((TextView)view.findViewById(R.id.mainFragmentThree_temperatureMax3));
        textViewList_temperatureMax.add((TextView)view.findViewById(R.id.mainFragmentThree_temperatureMax4));
        textViewList_temperatureMax.add((TextView)view.findViewById(R.id.mainFragmentThree_temperatureMax5));
        textViewList_temperatureMax.add((TextView)view.findViewById(R.id.mainFragmentThree_temperatureMax6));
        textViewList_temperatureMax.add((TextView)view.findViewById(R.id.mainFragmentThree_temperatureMax7));
        textViewList_temperatureMax.add((TextView)view.findViewById(R.id.mainFragmentThree_temperatureMax8));


    }
}
