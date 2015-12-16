package com.wkdgusdn3.weatherdelivery.time;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.wkdgusdn3.weatherdelivery.R;
import com.wkdgusdn3.weatherdelivery.alarm.AlarmReceiver;
import com.wkdgusdn3.weatherdelivery.manager.InfoManager;

import java.util.Calendar;

public class TimeSettingActivity extends ActionBarActivity {

    Button button_ampm;
    EditText editText_hour;
    EditText editText_minute;
    Button button_apply;
    Context context;
    ImageView imageView_hourUp;
    ImageView imageView_hourDown;
    ImageView imageView_minuteUp;
    ImageView imageView_minuteDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_setting);

        context = getApplicationContext();

        setView();
        setVar();
        setOnClickListener();
    }

    void setView() {
        button_ampm = (Button) findViewById(R.id.timeSetting_ampm);
        editText_hour = (EditText) findViewById(R.id.timeSetting_hour);
        editText_minute = (EditText) findViewById(R.id.timeSetting_minute);
        button_apply = (Button) findViewById(R.id.timeSetting_apply);
        imageView_hourUp = (ImageView)findViewById(R.id.timeSetting_hourUp);
        imageView_hourDown = (ImageView)findViewById(R.id.timeSetting_hourDown);
        imageView_minuteUp = (ImageView)findViewById(R.id.timeSetting_minuteUp);
        imageView_minuteDown = (ImageView)findViewById(R.id.timeSetting_minuteDown);
    }

    void setVar() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        int hour = Integer.parseInt(sharedPreferences.getString("HOUR", "0"));
        int minute = Integer.parseInt(sharedPreferences.getString("MINUTE", "0"));

        if(hour > 11) hour -= 12;

        editText_hour.setText(hour + "");
        editText_minute.setText(minute + "");
    }

    void setOnClickListener() {
        button_ampm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button_ampm.getText().equals("오전"))
                    button_ampm.setText("오후");
                else
                    button_ampm.setText("오전");
            }
        });

        editText_hour.addTextChangedListener(new TextWatcher() {
            int beforeHour;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                try {
                    beforeHour = Integer.parseInt(s.toString());
                } catch (Exception e) {
                    beforeHour = 0;
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int hour;
                try {
                    hour = Integer.parseInt(s.toString());
                } catch (Exception e) {
                    hour = 0;
                }
                if (hour > 11) {
                    editText_hour.setText(beforeHour + "");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        editText_minute.addTextChangedListener(new TextWatcher() {
            int beforeMinute;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                try {
                    beforeMinute = Integer.parseInt(s.toString());
                } catch (Exception e) {
                    beforeMinute = 0;
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int hour;
                try {
                    hour = Integer.parseInt(s.toString());
                } catch (Exception e) {
                    hour = 0;
                }
                if (hour > 59) {
                    editText_minute.setText(beforeMinute + "");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        imageView_hourUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = Integer.parseInt(editText_hour.getText().toString());

                if (hour < 11) {
                    editText_hour.setText(++hour + "");
                }
            }
        });

        imageView_hourDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = Integer.parseInt(editText_hour.getText().toString());

                if (hour > 0) {
                    editText_hour.setText(--hour + "");
                }
            }
        });

        imageView_minuteUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int minute = Integer.parseInt(editText_minute.getText().toString());

                if (minute < 60) {
                    editText_minute.setText(++minute + "");
                }
            }
        });

        imageView_minuteDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int minute = Integer.parseInt(editText_minute.getText().toString());

                if(minute > 0) {
                    editText_minute.setText(--minute + "");
                }
            }
        });

        button_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedHour;
                int selectedMinute;

                if (button_ampm.getText().equals("오전"))
                    selectedHour = Integer.parseInt(editText_hour.getText().toString());
                else
                    selectedHour = Integer.parseInt(editText_hour.getText().toString()) + 12;
                selectedMinute = Integer.parseInt(editText_minute.getText().toString());

                // 저장된 시간 저장
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("HOUR", selectedHour + "");
                editor.putString("MINUTE", selectedMinute + "");
                editor.commit();

                InfoManager.setData(getApplicationContext());

                // alarm 등록
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                Calendar calendar = Calendar.getInstance();

                int curHour = calendar.get(Calendar.HOUR_OF_DAY);
                int curMinute = calendar.get(Calendar.MINUTE);

                if (selectedHour > curHour) {    // 오늘
                    calendar.set(calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH),
                            selectedHour,
                            selectedMinute,
                            0);
                } else {
                    if (selectedHour == curHour && selectedMinute >= curMinute) {   // 오늘
                        calendar.set(calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH),
                                selectedHour,
                                selectedMinute,
                                0);
                    } else {    // 내일
                        calendar.set(calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH) + 1,
                                selectedHour,
                                selectedMinute,
                                0);
                    }
                }

                Intent intent = new Intent(context, AlarmReceiver.class);
                PendingIntent pender = PendingIntent.getBroadcast(context, 0, intent, 0);
                alarmManager.cancel(pender);
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pender);

                Toast.makeText(getApplicationContext(),
                        selectedHour + "시 " +
                                selectedMinute + "분에 날씨 배달이 예약되었습니다",
                        Toast.LENGTH_LONG).show();

                finish();
            }
        });
    }
}
