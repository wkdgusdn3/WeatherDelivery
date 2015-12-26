package com.wkdgusdn3.weatherdelivery.controller.network;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.wkdgusdn3.weatherdelivery.R;
import com.wkdgusdn3.weatherdelivery.controller.adapter.WeekWeatherListViewAdapter;
import com.wkdgusdn3.weatherdelivery.manager.InfoManager;
import com.wkdgusdn3.weatherdelivery.model.Time;
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

    ListView listView_weekWeather;
    WeekWeatherListViewAdapter weekWeatherLVA;
    ArrayList<TodayWeatherInfo> todayWeathers = new ArrayList<TodayWeatherInfo>();
    ArrayList<WeekWeatherInfo> weekWeathers = new ArrayList<WeekWeatherInfo>();
    ArrayList<WeekWeatherInfo> realWeathers = new ArrayList<WeekWeatherInfo>();

    public ReceiveWeekWeather(Context context,
                              ListView listView_weekWeather,
                              WeekWeatherListViewAdapter weekWeatherLVA) {

        this.context = context;
        this.listView_weekWeather = listView_weekWeather;
        this.weekWeatherLVA = weekWeatherLVA;
    }

    protected Long doInBackground(URL... urls) {
        InfoManager.setData(context);
        receiveTodayWeather();
        receiveWeekWeather();

        setTodayWeather();
        setWeekWeather();

        return null;
    }

    protected void onPostExecute(Long result) {
        listView_weekWeather.setAdapter(weekWeatherLVA);

        for (int i = 0; i < realWeathers.size(); i++) {
            weekWeatherLVA.addWeekWeather(realWeathers.get(i));
        }
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
            boolean onTmx = false;
            boolean onTmn = false;
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
                    if (tagName.equals("tmx") && !onTmx) {
                        todayWeathers.get(i).setTmx(parser.getText());
                        onTmx = true;
                    }
                    if (tagName.equals("tmn") && !onTmn) {
                        todayWeathers.get(i).setTmn(parser.getText());
                        onTmn = true;
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    if (tagName.equals("s06") && onEnd == false) {
                        i++;
                        onHour = false;
                        onDay = false;
                        onTem = false;
                        onWfKor = false;
                        onPop = false;
                        onTmx = false;
                        onTmn = false;
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
            boolean onCity = false;
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

                    if (tagName.equals("city")) {
                        eventType = parser.next();
                        if (parser.getText().equals(InfoManager.city)) {
                            onCity = true;
                        } else {
                            if (onCity) { // 이미 parsing을 끝냈을 경우
                                break;
                            } else {        // 아직 parsing을 못했을 경우
                                onCity = false;
                            }
                        }
                    }

                    if (tagName.equals("data") && onCity) {
                        weekWeathers.add(new WeekWeatherInfo());
                        onEnd = false;
                        isItemTag1 = true;
                    }
                } else if (eventType == XmlPullParser.TEXT && isItemTag1 && onCity) {
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
                    if (tagName.equals("tmx") && !onTmx) {
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

    private void setTodayWeather() {    // 일간 날씨 parsing한 object를 처리
        String tomorrowMax = null;
        String tomorrowMin = null;
        String tomorrowWf = null;
        String afterTomorrowMax = null;
        String afterTomorrowMin = null;
        String afterTomorrowWf = null;

        for (int i = 0; i < todayWeathers.size(); i++) {
            if (todayWeathers.get(i).getDay().equals("1")) {
                if (tomorrowWf == null) {
                    tomorrowWf = todayWeathers.get(i).getWfKor();
                    tomorrowMax = todayWeathers.get(i).getTmx();
                    tomorrowMin = todayWeathers.get(i).getTmn();
                }
            } else if (todayWeathers.get(i).getDay().equals("2")) {
                if (afterTomorrowWf == null) {
                    afterTomorrowWf = todayWeathers.get(i).getWfKor();
                    afterTomorrowMax = todayWeathers.get(i).getTmx();
                    afterTomorrowMin = todayWeathers.get(i).getTmn();
                }
            }
        }

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);

        realWeathers.add(new WeekWeatherInfo(
                (calendar.get(Calendar.MONTH) + 1) + "/" +
                        calendar.get(Calendar.DAY_OF_MONTH) +
                        getDayOfWeek(Integer.toString(calendar.get(Calendar.DAY_OF_WEEK))),
                tomorrowWf, tomorrowMin + "", tomorrowMax + ""));

        calendar.add(Calendar.DATE, 1);

        realWeathers.add(new WeekWeatherInfo(
                (calendar.get(Calendar.MONTH) + 1) + "/" +
                        calendar.get(Calendar.DAY_OF_MONTH) +
                        getDayOfWeek(Integer.toString(calendar.get(Calendar.DAY_OF_WEEK))),
                afterTomorrowWf, afterTomorrowMin + "", afterTomorrowMax + ""));
    }

    private void setWeekWeather() { // 주간 날씨 parsing한 object를 처리
        for (int i = 0; i < weekWeathers.size(); i++) {
            Time time = parsingTime(weekWeathers.get(i).getTmEf());
            if (time.getTime().equals("00:00")) {

                Calendar calendar = Calendar.getInstance();
                calendar.set(calendar.get(Calendar.YEAR),
                        Integer.parseInt(time.getMonth()),
                        Integer.parseInt(time.getDay()));

                weekWeathers.get(i).setTmEf(
                        time.getMonth() + "/" +
                                time.getDay() +
                                getDayOfWeek(Integer.toString(calendar.get(Calendar.DAY_OF_WEEK))));
                weekWeathers.get(i).setTmx(weekWeathers.get(i).getTmx() + ".0");
                weekWeathers.get(i).setTmn(weekWeathers.get(i).getTmn() + ".0");

                realWeathers.add(weekWeathers.get(i));
            }
        }
    }

    private Time parsingTime(String time) {
        String parsed[] = time.split(" ");
        String parsed2[] = parsed[0].split("-");

        return new Time(parsed2[1], parsed2[2], parsed[1]);
    }

    private String getDayOfWeek(String num) {
        switch (num) {
            case "1":
                return "(일)";
            case "2":
                return "(월)";
            case "3":
                return "(화)";
            case "4":
                return "(수)";
            case "5":
                return "(목)";
            case "6":
                return "(금)";
            case "7":
                return "(토)";
        }

        return "(일)";
    }


    private int setWeatherIcon(String weather) {

        if (weather.equals("맑음")) {
            return R.drawable.sun;
        } else if (weather.equals("구름 조금")) {
            return R.drawable.cloud;
        } else if (weather.equals("구름 많음")) {
            return R.drawable.clouds;
        } else if (weather.equals("흐림")) {
            return R.drawable.partly_cloudy;
        } else if (weather.equals("비")) {
            return R.drawable.rain;
        } else if (weather.equals("눈/비")) {
            return R.drawable.rain_and_snow;
        } else if (weather.equals("눈")) {
            return R.drawable.snow;
        } else {
            return R.drawable.sun;
        }
    }
}