package com.wkdgusdn3.weatherdelivery.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.wkdgusdn3.weatherdelivery.R;
import com.wkdgusdn3.weatherdelivery.alarm.AlarmService;
import com.wkdgusdn3.weatherdelivery.manager.InfoManager;


public class MainActivity extends FragmentActivity implements View.OnClickListener {

    TextView textView_currentWeather;
    TextView textView_todayWeather;
    TextView textView_weekWeather;
    TextView textView_notificationReservation;

    public static final int FRAGMENT_ONE = 0;
    public static final int FRAGMENT_TWO = 1;
    public static final int FRAGMENT_THREE = 2;
    public static final int FRAGMENT_FOUR = 3;
    int currentFragmentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InfoManager.setData(getApplicationContext());

        setVariable();
        textView_currentWeather.setOnClickListener(this);
        textView_todayWeather.setOnClickListener(this);
        textView_weekWeather.setOnClickListener(this);
        textView_notificationReservation.setOnClickListener(this);

        fragmentReplace(currentFragmentIndex);

        startService(new Intent(getApplicationContext(), AlarmService.class));

    }

    public void fragmentReplace(int reqNewFragmentIndex) {

        Fragment newFragment = null;

        newFragment = getFragment(reqNewFragmentIndex);

        // replace fragment
        final FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();

        transaction.replace(R.id.main_fragment, newFragment);

        // Commit the transaction
        transaction.commit();

    }

    private Fragment getFragment(int idx) {
        Fragment newFragment = null;

        switch (idx) {
            case FRAGMENT_ONE:
                newFragment = new OneFragment();
                break;
            case FRAGMENT_TWO:
                newFragment = new TwoFragment();
                break;
            case FRAGMENT_THREE:
                newFragment = new ThreeFragment();
                break;
            case FRAGMENT_FOUR:
                newFragment = new FourFragment();
                break;
            default:
                break;
        }

        return newFragment;
    }

    void setVariable() {
        textView_currentWeather = (TextView) findViewById(R.id.main_currentWeather);
        textView_todayWeather = (TextView)findViewById(R.id.main_todayWeather);
        textView_weekWeather = (TextView)findViewById(R.id.main_weekWeather);
        textView_notificationReservation = (TextView)findViewById(R.id.main_notificationReservation);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.main_currentWeather:
                currentFragmentIndex = FRAGMENT_ONE;
                fragmentReplace(currentFragmentIndex);
                break;
            case R.id.main_todayWeather:
                currentFragmentIndex = FRAGMENT_TWO;
                fragmentReplace(currentFragmentIndex);
                break;
            case R.id.main_weekWeather:
                currentFragmentIndex = FRAGMENT_THREE;
                fragmentReplace(currentFragmentIndex);
                break;
            case R.id.main_notificationReservation:
                currentFragmentIndex = FRAGMENT_FOUR;
                fragmentReplace(currentFragmentIndex);
                break;
        }
    }
}
