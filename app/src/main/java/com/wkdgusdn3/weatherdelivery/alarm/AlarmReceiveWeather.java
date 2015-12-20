package com.wkdgusdn3.weatherdelivery.alarm;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.wkdgusdn3.weatherdelivery.R;
import com.wkdgusdn3.weatherdelivery.manager.InfoManager;
import com.wkdgusdn3.weatherdelivery.model.WeatherInfo;
import com.wkdgusdn3.weatherdelivery.view.activity.MainActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

class AlarmReceiveWeather extends AsyncTask<URL, Integer, Long> {
    Context context;
    ArrayList<WeatherInfo> weatherInfo = new ArrayList<WeatherInfo>();
    String weatherText = "";

    public AlarmReceiveWeather(Context context) {
        this.context = context;
    }

    protected Long doInBackground(URL... urls) {

        InfoManager.setData(context);
        String url = "http://www.kma.go.kr/wid/queryDFSRSS.jsp?zone=" + InfoManager.cityCode;

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = null;

        try {
            response = client.newCall(request).execute();
            parseXML(response.body().string());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    protected void onPostExecute(Long result) {

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder mBuilder = new Notification.Builder(context);
        mBuilder.setTicker("날씨배달!!");
        mBuilder.setSmallIcon(R.drawable.noti_icon);
        mBuilder.setWhen(System.currentTimeMillis());
        mBuilder.setNumber(10);
        mBuilder.setContentTitle("오늘의 날씨!");
        mBuilder.setContentText(weatherText);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setAutoCancel(true);

        Notification.BigTextStyle style = new Notification.BigTextStyle(mBuilder);

        style.setSummaryText("날씨배달!!");
        style.setBigContentTitle("오늘의 날씨!!!");
        style.bigText(weatherText);

        nm.notify(111, mBuilder.build());

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        // 저장된 시간 load
        int selectedHour = Integer.parseInt(sharedPreferences.getString("HOUR", "7"));
        int selectedMinute = Integer.parseInt(sharedPreferences.getString("MINUTE", "0"));

        // alarm 등록
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();

        int curHour = calendar.get(Calendar.HOUR_OF_DAY);
        int curMinute = calendar.get(Calendar.MINUTE);

        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH) + 1,
                selectedHour,
                selectedMinute,
                0);

        Log.e("wkdgusdn3", calendar.get(Calendar.YEAR) + " " +
                calendar.get(Calendar.MONTH) + " " +
                calendar.get(Calendar.DAY_OF_MONTH) + " " +
                calendar.get(Calendar.HOUR) + " " +
                calendar.get(Calendar.MINUTE) + " ");

        Intent intent_alarmReceiver = new Intent(context, AlarmReceiver.class);
        PendingIntent pender = PendingIntent.getBroadcast(context, 0, intent_alarmReceiver, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pender);
    }

    void parseXML(String xml) {
        try {
            String tagName = "";
            boolean onHour = false;
            boolean onTem = false;
            boolean onWfKor = false;
            boolean onEnd = false;
            boolean isItemTag1 = false;
            int i = 0;

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();

            parser.setInput(new StringReader(xml));

            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    tagName = parser.getName();
                    if (tagName.equals("data")) {
                        weatherInfo.add(new WeatherInfo());
                        onEnd = false;
                        isItemTag1 = true;
                    }
                } else if (eventType == XmlPullParser.TEXT && isItemTag1) {
                    if (tagName.equals("hour") && !onHour) {
                        weatherInfo.get(i).setHour(parser.getText());
                        onHour = true;
                    }
                    if (tagName.equals("temp") && !onTem) {
                        weatherInfo.get(i).setTemp(parser.getText());
                        onTem = true;
                    }
                    if (tagName.equals("wfKor") && !onWfKor) {
                        weatherInfo.get(i).setWfKor(parser.getText());
                        onWfKor = true;
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    if (tagName.equals("s06") && onEnd == false) {
                        i++;
                        onHour = false;
                        onTem = false;
                        onWfKor = false;
                        isItemTag1 = false;
                        onEnd = true;
                    }
                }

                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 5; i++) {
            weatherText = weatherText + weatherInfo.get(i).getHour() + "시 " + weatherInfo.get(i).getTemp() + "º " + weatherInfo.get(i).getWfKor() + "\n";
        }
    }
}