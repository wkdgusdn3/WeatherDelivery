package com.wkdgusdn3.weatherdelivery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.wkdgusdn3.alarm.AlarmService;
import com.wkdgusdn3.location.LocationSettingActivity;
import com.wkdgusdn3.manager.InfoManager;
import com.wkdgusdn3.time.TimeSettingActivity;


public class MainActivity extends ActionBarActivity {

    TextView textView_info;
    ListView listView;
    String[] item = {"시간설정", "지역설정"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InfoManager.setData(getApplicationContext());

        setVariable();

        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, item));
        listView.setOnItemClickListener(new DrawerItemClickListener());

        startService(new Intent(getApplicationContext(), AlarmService.class));

        textView_info.setText(InfoManager.city + "지역의 날씨를\n" +
                InfoManager.hour + "시 " + InfoManager.minute + "분에 배달합니다.");
    }

    @Override
    protected void onResume() {
        super.onResume();

        textView_info.setText(InfoManager.city + "지역의 날씨를\n" +
        InfoManager.hour + "시 " + InfoManager.minute + "분에 배달합니다.");
    }

    void setVariable() {
        textView_info = (TextView) findViewById(R.id.main_info);
        listView = (ListView) findViewById(R.id.main_list);
    }

    class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position,
                                long id) {
            switch (position) {
                case 0:
                    Intent timeSettingActivity = new Intent(getApplicationContext(), TimeSettingActivity.class);
                    startActivity(timeSettingActivity);
                    break;
                case 1:
                    Intent locationSettingAcivity = new Intent(getApplicationContext(), LocationSettingActivity.class);
                    startActivity(locationSettingAcivity);
                    break;
            }
        }
    }
}
