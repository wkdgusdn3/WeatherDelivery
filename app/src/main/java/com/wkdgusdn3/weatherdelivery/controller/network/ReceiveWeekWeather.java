package com.wkdgusdn3.weatherdelivery.controller.network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.wkdgusdn3.weatherdelivery.R;
import com.wkdgusdn3.weatherdelivery.manager.InfoManager;
import com.wkdgusdn3.weatherdelivery.model.TodayWeatherInfo;
import com.wkdgusdn3.weatherdelivery.model.WeekWeatherInfo;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

public class ReceiveWeekWeather extends AsyncTask<URL, Integer, Long> {
    Context context;

    ArrayList<TodayWeatherInfo> todayWeathers = new ArrayList<TodayWeatherInfo>();
    ArrayList<WeekWeatherInfo> weekWeathers = new ArrayList<WeekWeatherInfo>();

    public ReceiveWeekWeather(Context context) {

        this.context = context;
    }

    protected Long doInBackground(URL... urls) {
        InfoManager.setData(context);
        receiveTodayWeather();
        receiveWeekWeather();

        for(int i=0; i<todayWeathers.size(); i++) {
            Log.e("wkdgusdn3", "today : " + todayWeathers.get(i).getTemp());
        }

        for(int i=0; i<weekWeathers.size(); i++) {
            Log.e("wkdgusdn3", "week : " + weekWeathers.get(i).getTmn());
        }





        return null;
    }

    protected void onPostExecute(Long result) {
    }

    private void receiveTodayWeather() {
        String url = "http://www.kma.go.kr/wid/queryDFSRSS.jsp?zone=" + InfoManager.cityCode;

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = null;

        try {
            response = client.newCall(request).execute();
            parseTodayXML(response.body().string());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void receiveWeekWeather() {
        String url = "http://www.kma.go.kr/weather/forecast/mid-term-rss3.jsp?stnId=" + InfoManager.cityCode2;

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = null;

        try {
            response = client.newCall(request).execute();
            parseWeekXML(response.body().string());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void parseTodayXML(String xml) {
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
                        todayWeathers.add(new TodayWeatherInfo());
                        onEnd = false;
                        isItemTag1 = true;
                    }
                } else if (eventType == XmlPullParser.TEXT && isItemTag1) {
                    if (tagName.equals("hour") && !onHour) {
                        todayWeathers.get(i).setHour(parser.getText());
                        onHour = true;
                    }
                    if (tagName.equals("day") && !onDay) {
                        todayWeathers.get(i).setDay(parser.getText());
                        onDay = true;
                    }
                    if (tagName.equals("temp") && !onTem) {
                        todayWeathers.get(i).setTemp(parser.getText());
                        onTem = true;
                    }
                    if (tagName.equals("wfKor") && !onWfKor) {
                        todayWeathers.get(i).setWfKor(parser.getText());
                        onWfKor = true;
                    }
                    if (tagName.equals("pop") && !onPop) {
                        todayWeathers.get(i).setPop(parser.getText());
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

    void parseWeekXML(String xml) {
        try {
            String tagName = "";
            boolean onTmEf = false;
            boolean onWf = false;
            boolean onTmn = false;
            boolean onTmx = false;
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
                        weekWeathers.add(new WeekWeatherInfo());
                        onEnd = false;
                        isItemTag1 = true;
                    }
                } else if (eventType == XmlPullParser.TEXT && isItemTag1) {
                    if (tagName.equals("tmEf") && !onTmEf) {
                        weekWeathers.get(i).setTmEf(parser.getText());
                        onTmEf = true;
                    }
                    if (tagName.equals("wf") && !onWf) {
                        weekWeathers.get(i).setWf(parser.getText());
                        onWf = true;
                    }
                    if (tagName.equals("tmn") && !onTmn) {
                        weekWeathers.get(i).setTmn(parser.getText());
                        onTmn = true;
                    }
                    if(tagName.equals("tmx") && !onTmx) {
                        weekWeathers.get(i).setTmx(parser.getText());
                        onTmx = true;
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    if (tagName.equals("reliability") && onEnd == false) {
                        i++;
                        onTmEf = false;
                        onWf = false;
                        onTmn = false;
                        onTmx = false;
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

    private String setDate(String string_time) {
        int time = Integer.parseInt(string_time);
        Calendar calendar = Calendar.getInstance();

        if(time == 24) {
            return "내일";
        } else if(time > calendar.get(Calendar.HOUR_OF_DAY)) {
            return "오늘";
        } else {
            return "내일";
        }
    }

    private String setTime(String string_time) {
        int time = Integer.parseInt(string_time);

        if(time == 24) {
            return "오전 0시";
        } else if(time == 12) {
            return "오후 12시";
        } else if(time > 12) {
            time -= 12;

            return "오후 " + time + "시";
        } else {
            return "오전 " + time + "시";
        }
    }

    private int setWeatherIcon(String weather) {

        if(weather.equals("맑음")) {
            return R.drawable.sun;
        } else if(weather.equals("구름 조금")) {
            return R.drawable.cloud;
        } else if(weather.equals("구름 많음")) {
            return R.drawable.clouds;
        } else if(weather.equals("흐림")) {
            return R.drawable.partly_cloudy;
        } else if(weather.equals("비")) {
            return R.drawable.rain;
        } else if(weather.equals("눈/비")) {
            return R.drawable.rain_and_snow;
        } else if(weather.equals("눈")) {
            return R.drawable.snow;
        } else {
            return R.drawable.sun;
        }
    }
}