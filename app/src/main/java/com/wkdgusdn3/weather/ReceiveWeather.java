package com.wkdgusdn3.weather;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.wkdgusdn3.item.WeatherInfo;
import com.wkdgusdn3.weatherdelivery.MainActivity;
import com.wkdgusdn3.weatherdelivery.R;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;

class ReceiveWeather extends AsyncTask<URL, Integer, Long> {
    Context context;
    JSONObject jsonObject;
    ArrayList<WeatherInfo> weatherInfo = new ArrayList<WeatherInfo>();
    String weatherText = "";

    public ReceiveWeather(Context context) {
        this.context = context;
    }

    protected Long doInBackground(URL... urls) {

        String url = "http://www.kma.go.kr/wid/queryDFSRSS.jsp?zone=1100000000";

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
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
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