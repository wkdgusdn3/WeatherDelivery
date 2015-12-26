package com.wkdgusdn3.weatherdelivery.controller.network;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.wkdgusdn3.weatherdelivery.controller.adapter.TodayWeatherListViewAdapter;
import com.wkdgusdn3.weatherdelivery.manager.InfoManager;
import com.wkdgusdn3.weatherdelivery.model.TodayWeatherInfo;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;

public class ReceiveTodayWeather extends AsyncTask<URL, Integer, Long> {
    Context context;

    ListView listView_todayWeather;
    ArrayList<TodayWeatherInfo> todayWeatherInfos = new ArrayList<TodayWeatherInfo>();
    TodayWeatherListViewAdapter todayWeatherLVA;

    public ReceiveTodayWeather(Context context, ListView listView_todayWeather, TodayWeatherListViewAdapter todayWeatherLVA) {
        this.context = context;
        this.todayWeatherLVA = todayWeatherLVA;
        this.listView_todayWeather = listView_todayWeather;
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

        listView_todayWeather.setAdapter(todayWeatherLVA);

        for (int i = 0; i < todayWeatherInfos.size(); i++) {
            if (todayWeatherInfos.get(i).getDay().equals("1") && todayWeatherInfos.get(i).getHour().equals("24")) {
                break;
            }
            todayWeatherLVA.addTodayWeather(todayWeatherInfos.get(i));
        }
    }

    void parseXML(String xml) {
        try {
            String tagName = "";
            boolean onHour = false;
            boolean onDay = false;
            boolean onTem = false;
            boolean onWfKor = false;
            boolean onPop = false;
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
                        todayWeatherInfos.add(new TodayWeatherInfo());
                        onEnd = false;
                        isItemTag1 = true;
                    }
                } else if (eventType == XmlPullParser.TEXT && isItemTag1) {
                    if (tagName.equals("hour") && !onHour) {
                        todayWeatherInfos.get(i).setHour(parser.getText());
                        onHour = true;
                    }
                    if (tagName.equals("day") && !onDay) {
                        todayWeatherInfos.get(i).setDay(parser.getText());
                        onDay = true;
                    }
                    if (tagName.equals("temp") && !onTem) {
                        todayWeatherInfos.get(i).setTemp(parser.getText());
                        onTem = true;
                    }
                    if (tagName.equals("wfKor") && !onWfKor) {
                        todayWeatherInfos.get(i).setWfKor(parser.getText());
                        onWfKor = true;
                    }
                    if (tagName.equals("pop") && !onPop) {
                        todayWeatherInfos.get(i).setPop(parser.getText());
                        onPop = true;
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    if (tagName.equals("s06") && onEnd == false) {
                        i++;
                        onHour = false;
                        onDay = false;
                        onTem = false;
                        onWfKor = false;
                        onPop = false;
                        isItemTag1 = false;
                        onEnd = true;
                    }
                }

                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}