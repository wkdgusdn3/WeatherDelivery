package com.wkdgusdn3.weatherdelivery.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.wkdgusdn3.weatherdelivery.R;
import com.wkdgusdn3.weatherdelivery.view.activity.LocationSettingActivity;
import com.wkdgusdn3.weatherdelivery.manager.InfoManager;
import com.wkdgusdn3.weatherdelivery.view.activity.TimeSettingActivity;

public class FourFragment extends Fragment implements View.OnClickListener{

    View view;
    TextView textView_reservationInfo;
    Button button_timeSetting;
    Button button_locationSetting;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_four, container, false);

        setVariable();
        button_timeSetting.setOnClickListener(this);
        button_locationSetting.setOnClickListener(this);

        textView_reservationInfo.setText(InfoManager.hour + "시" + InfoManager.minute + "분에\n" +
                InfoManager.city + "지역의 날씨를 배달합니다.");

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        textView_reservationInfo.setText(InfoManager.hour + "시 " + InfoManager.minute + "분에\n" +
                InfoManager.city + "의 날씨를 배달합니다.");
    }

    private void setVariable() {
        textView_reservationInfo = (TextView)view.findViewById(R.id.main_fourFragment_reservationInfo);
        button_timeSetting = (Button)view.findViewById(R.id.main_fourFragment_timeSetting);
        button_locationSetting = (Button)view.findViewById(R.id.main_fourFragment_locationSetting);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.main_fourFragment_timeSetting:
                Intent intent_timeSettingActivity = new Intent(view.getContext(), TimeSettingActivity.class);
                startActivity(intent_timeSettingActivity);
                break;
            case R.id.main_fourFragment_locationSetting:
                Intent intent_locationSettingActivity = new Intent(view.getContext(), LocationSettingActivity.class);
                startActivity(intent_locationSettingActivity);
                break;
        }
    }


}
