package com.wkdgusdn3.weatherdelivery.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wkdgusdn3.weatherdelivery.R;
import com.wkdgusdn3.weatherdelivery.manager.InfoManager;
import com.wkdgusdn3.weatherdelivery.view.fragment.FourFragment;
import com.wkdgusdn3.weatherdelivery.view.fragment.OneFragment;
import com.wkdgusdn3.weatherdelivery.view.fragment.ThreeFragment;
import com.wkdgusdn3.weatherdelivery.view.fragment.TwoFragment;

import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    TextView textView_time;
    TextView textView_ampm;
    TextView textView_location;

    public static final int FRAGMENT_ONE = 0;
    public static final int FRAGMENT_TWO = 1;
    public static final int FRAGMENT_THREE = 2;
    public static final int FRAGMENT_FOUR = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InfoManager.setData(getApplicationContext());
        setView();
        setListener();
        setTime();
        textView_location.setText(InfoManager.city);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void onResume() {
        super.onResume();

        textView_location.setText(InfoManager.city);
    }

    private void setView() {
        textView_time = (TextView)findViewById(R.id.mainActivity_time);
        textView_ampm = (TextView)findViewById(R.id.mainActivity_ampm);
        textView_location = (TextView)findViewById(R.id.mainActivity_location);
    }

    private void setListener() {
        textView_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), LocationSettingActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            Fragment newFragment = null;

            switch (position) {
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

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
                case 3:
                    return getString(R.string.title_section4).toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main_one, container, false);
            return rootView;
        }
    }

    private void setTime() {

        Timer timer = new Timer();
        final Handler handler = new Handler();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                final Calendar calendar = Calendar.getInstance();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        textView_time.setText(changeHour("" + calendar.get(Calendar.HOUR_OF_DAY)) +
                                ":" +
                                changeMinute("" + calendar.get(Calendar.MINUTE)));
                        textView_ampm.setText(changeAMPM("" + calendar.get(Calendar.HOUR_OF_DAY)));
                    }
                });
            }
        };

        timer.schedule(timerTask, 0, 1000);
    }

    private String changeHour(String hour) {
        int int_hour = Integer.parseInt(hour);

        if (int_hour == 0 || int_hour == 12) {
            return "12";
        } else if (int_hour < 10) {
            return "0" + Integer.toString(int_hour);
        } else if (int_hour < 12) {
            return Integer.toString(int_hour);
        } else {
            return Integer.toString(int_hour - 12);
        }
    }

    private String changeMinute(String minute) {
        int int_minute = Integer.parseInt(minute);

        if (int_minute < 10) {
            return "0" + Integer.toString(int_minute);
        } else {
            return Integer.toString(int_minute);
        }
    }

    private String changeAMPM(String hour) {
        int int_hour = Integer.parseInt(hour);

        if (int_hour < 12) {
            return "AM";
        } else {
            return "PM";
        }
    }

}
