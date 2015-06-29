package com.wkdgusdn3.weatherdelivery;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.wkdgusdn3.alarm.AlarmService;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    ArrayList<String> array_hour;
    ArrayList<String> array_minute;
    Spinner spinner_hour;
    Spinner spinner_minute;
    Button button_apply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        array_hour = new ArrayList<String>();
        array_minute = new ArrayList<String>();

        setVariable();
        setArrayList();

        ArrayAdapter<String> adapter_hour = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, array_hour);
        ArrayAdapter<String> adapter_minute = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, array_minute);

        spinner_hour.setAdapter(adapter_hour);
        spinner_minute.setAdapter(adapter_minute);

        setOnClickListener();

        startService(new Intent(getApplicationContext(), AlarmService.class));
    }

    void setVariable() {
        spinner_hour = (Spinner) findViewById(R.id.hour);
        spinner_minute = (Spinner) findViewById(R.id.minute);
        button_apply = (Button) findViewById(R.id.apply);
    }

    void setOnClickListener() {
        button_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("HOUR", spinner_hour.getSelectedItem() + "");
                editor.putString("MINUTE", spinner_minute.getSelectedItem() + "");
                editor.commit();

                Toast.makeText(getApplicationContext(),
                        spinner_hour.getSelectedItem() + "시 " +
                                spinner_minute.getSelectedItem() + "분에 날씨 배달이 예약되었습니다",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    void setArrayList() {
        array_hour.add("0");
        array_hour.add("1");
        array_hour.add("2");
        array_hour.add("3");
        array_hour.add("4");
        array_hour.add("5");
        array_hour.add("6");
        array_hour.add("7");
        array_hour.add("8");
        array_hour.add("9");
        array_hour.add("10");
        array_hour.add("11");
        array_hour.add("12");
        array_hour.add("13");
        array_hour.add("14");
        array_hour.add("15");
        array_hour.add("16");
        array_hour.add("17");
        array_hour.add("18");
        array_hour.add("19");
        array_hour.add("20");
        array_hour.add("21");
        array_hour.add("22");
        array_hour.add("23");

        array_minute.add("0");
        array_minute.add("10");
        array_minute.add("20");
        array_minute.add("30");
        array_minute.add("40");
        array_minute.add("50");
    }
}
