package com.wkdgusdn3.weatherdelivery.view.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wkdgusdn3.weatherdelivery.R;
import com.wkdgusdn3.weatherdelivery.manager.InfoManager;

public class LocationSettingActivity extends ActionBarActivity {

    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    TextView textView_pre;
    EditText editText_search;
    String[] item_address;
    String[] item_code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_setting);

        Toolbar toolbar = (Toolbar) findViewById(R.id.locationSetting_toolbar);
        setSupportActionBar(toolbar);

        setView();
        setListener();

        item_address = getResources().getStringArray(R.array.address);
        item_code = getResources().getStringArray(R.array.code);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, item_address);
        listView.setAdapter(arrayAdapter);

        listView.setTextFilterEnabled(true);
        listView.setOnItemClickListener(onClickListItem);
        editText_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                arrayAdapter.getFilter().filter(s);
                arrayAdapter.notifyDataSetChanged();
            }
        });

    }

    void setView() {
        textView_pre = (TextView)findViewById(R.id.locationSetting_pre);
        editText_search = (EditText)findViewById(R.id.locationSetting_search);
        listView = (ListView)findViewById(R.id.locationSetting_listView);
    }

    void setListener() {
        textView_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // 아이템 터치 이벤트
    private AdapterView.OnItemClickListener onClickListItem = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

            SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor =sharedPreferences.edit();
            editor.putString("CITY", arrayAdapter.getItem(arg2));
            editor.putString("CITY2", getCityName2(arrayAdapter.getItem(arg2)));
            editor.putString("CITYCODE", item_code[arg2]);
            editor.putString("CITYCODE2", getCityCode2(arrayAdapter.getItem(arg2)));
            editor.commit();

            InfoManager.setData(getApplicationContext());
            Toast.makeText(getApplicationContext(), arrayAdapter.getItem(arg2) + "지역으로 설정되었습니다", Toast.LENGTH_LONG).show();

            finish();
        }
    };

    String getCityName2(String city) {

        String parsed[] = city.split(" ");

        if (parsed[0].equals("서울특별시")) {
            return "서울";
        } else if (parsed[0].equals("부산광역시")) {
            return "부산";
        } else if (parsed[0].equals("대구광역시")) {
            return "대구";
        } else if (parsed[0].equals("인천광역시")) {
            return "인천";
        } else if (parsed[0].equals("광주광역시")) {
            return "광주";
        } else if (parsed[0].equals("대전광역시")) {
            return "대전";
        } else if (parsed[0].equals("울산광역시")) {
            return "울산";
        } else if (parsed[0].equals("세종특별자치시")) {
            return "세종";
        } else if (parsed[0].equals("경기도")) {
            return "서울";
        } else if (parsed[0].equals("강원도")) {
            return "강릉";
        } else if (parsed[0].equals("충청북도")) {
            return "청주";
        } else if (parsed[0].equals("충청남도")) {
            return "대전";
        } else if (parsed[0].equals("전라북도")) {
            return "전주";
        } else if (parsed[0].equals("전라남도")) {
            return "광주";
        } else if (parsed[0].equals("경상북도")) {
            return "대구";
        } else if (parsed[0].equals("경상남도")) {
            return "부산";
        } else if (parsed[0].equals("제주특별자치도")) {
            return "제주";
        } else {
            return "서울";
        }
    }

    String getCityCode2(String city) {

        String parsed[] = city.split(" ");

        if (parsed[0].equals("서울특별시")) {
            return "109";
        } else if (parsed[0].equals("부산광역시")) {
            return "159";
        } else if (parsed[0].equals("대구광역시")) {
            return "143";
        } else if (parsed[0].equals("인천광역시")) {
            return "109";
        } else if (parsed[0].equals("광주광역시")) {
            return "156";
        } else if (parsed[0].equals("대전광역시")) {
            return "133";
        } else if (parsed[0].equals("울산광역시")) {
            return "159";
        } else if (parsed[0].equals("세종특별자치시")) {
            return "133";
        } else if (parsed[0].equals("경기도")) {
            return "109";
        } else if (parsed[0].equals("강원도")) {
            return "184";
        } else if (parsed[0].equals("충청북도")) {
            return "131";
        } else if (parsed[0].equals("충청남도")) {
            return "133";
        } else if (parsed[0].equals("전라북도")) {
            return "146";
        } else if (parsed[0].equals("전라남도")) {
            return "156";
        } else if (parsed[0].equals("경상북도")) {
            return "143";
        } else if (parsed[0].equals("경상남도")) {
            return "159";
        } else if (parsed[0].equals("제주특별자치도")) {
            return "184";
        } else {
            return "109";
        }
    }
}
