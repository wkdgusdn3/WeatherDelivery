package com.wkdgusdn3.weatherdelivery.location;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.wkdgusdn3.weatherdelivery.manager.InfoManager;
import com.wkdgusdn3.weatherdelivery.R;

public class LocationSettingActivity extends ActionBarActivity {

    ListView listView;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_setting);

        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.item_region);

        listView = (ListView) findViewById(R.id.locationSetting_listVIew);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(onClickListItem);

        adapter.add("서울");
        adapter.add("인천");
        adapter.add("대전");
        adapter.add("대구");
        adapter.add("울산");
        adapter.add("광주");
        adapter.add("부산");
        adapter.add("제주");
    }

    // 아이템 터치 이벤트
    private AdapterView.OnItemClickListener onClickListItem = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor =sharedPreferences.edit();
            editor.putString("CITY", adapter.getItem(arg2));
            editor.putString("CITYCODE", getCityCode(adapter.getItem(arg2)));
            editor.commit();

            InfoManager.setData(getApplicationContext());
            Toast.makeText(getApplicationContext(), adapter.getItem(arg2) + "지역으로 설정되었습니다", Toast.LENGTH_LONG).show();

            finish();
        }
    };

    String getCityCode(String city) {
        if(city.equals("서울")) {
            return "1100000000";
        } else if(city.equals("인천")) {
            return "2800000000";
        } else if(city.equals("대전")) {
            return "3000000000";
        } else if(city.equals("대구")) {
            return "2700000000";
        } else if(city.equals("울산")) {
            return "3100000000";
        } else if(city.equals("광주")) {
            return "2900000000";
        } else if(city.equals("부산")) {
            return "2600000000";
        } else if(city.equals("제주")) {
            return "5000000000";
        } else {
            return "1100000000";
        }
    }
}
