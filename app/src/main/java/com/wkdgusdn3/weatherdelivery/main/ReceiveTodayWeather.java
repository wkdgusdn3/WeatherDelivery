package com.wkdgusdn3.weatherdelivery.main;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.wkdgusdn3.weatherdelivery.R;
import com.wkdgusdn3.weatherdelivery.item.WeatherInfo;
import com.wkdgusdn3.weatherdelivery.manager.InfoManager;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

class ReceiveTodayWeather extends AsyncTask<URL, Integer, Long> {
    Context context;

    ArrayList<WeatherInfo> weatherInfoList = new ArrayList<WeatherInfo>();
    ArrayList<TextView> textViewList_date;
    ArrayList<TextView> textViewList_time;
    ArrayList<ImageView> imageViewList_weatherIcon;
    ArrayList<TextView> textViewList_temperature;
    ArrayList<TextView> textViewList_rainFallProbability;

    public ReceiveTodayWeather(Context context,
                               ArrayList<TextView> textViewList_date,
                               ArrayList<TextView> textViewList_time,
                               ArrayList<ImageView> imageViewList_weatherIcon,
                               ArrayList<TextView> textViewList_temperature,
                               ArrayList<TextView> textViewList_rainFallProbability) {

        this.context = context;
        this.textViewList_date = textViewList_date;
        this.textViewList_time = textViewList_time;
        this.imageViewList_weatherIcon = imageViewList_weatherIcon;
        this.textViewList_temperature = textViewList_temperature;
        this.textViewList_rainFallProbability = textViewList_rainFallProbability;
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
        for(int i=0; i<textViewList_date.size(); i++) {
            textViewList_date.get(i).setText(setDate(weatherInfoList.get(i).getHour()));
            textViewList_time.get(i).setText(setTime(weatherInfoList.get(i).getHour()));
            imageViewList_weatherIcon.get(i).setBackgroundResource(
                    setWeatherIcon(weatherInfoList.get(i).getWfKor()));
            textViewList_temperature.get(i).setText(weatherInfoList.get(i).getTemp() + "º");
            textViewList_rainFallProbability.get(i).setText(weatherInfoList.get(i).getPop() + "%");
        }
    }

    void parseXML(String xml) {
        try {
            String tagName = "";
            boolean onHour = false;
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
                        weatherInfoList.add(new WeatherInfo());
                        onEnd = false;
                        isItemTag1 = true;
                    }
                } else if (eventType == XmlPullParser.TEXT && isItemTag1) {
                    if (tagName.equals("hour") && !onHour) {
                        weatherInfoList.get(i).setHour(parser.getText());
                        onHour = true;
                    }
                    if (tagName.equals("temp") && !onTem) {
                        weatherInfoList.get(i).setTemp(parser.getText());
                        onTem = true;
                    }
                    if (tagName.equals("wfKor") && !onWfKor) {
                        weatherInfoList.get(i).setWfKor(parser.getText());
                        onWfKor = true;
                    }
                    if(tagName.equals("pop") && !onPop) {
                        weatherInfoList.get(i).setPop(parser.getText());
                        onPop = true;
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    if (tagName.equals("s06") && onEnd == false) {
                        i++;
                        onHour = false;
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