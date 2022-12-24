package uz.klimuz.weatherclock;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;

public class Parser {
    private static Document getPage() throws IOException {
        String url = "https://yandex.uz/pogoda/tashkent?utm_campaign=informer&utm_content=main_informer&utm_medium=web&utm_source=home&lat=41.311151&lon=69.279737";

        Document page = Jsoup.parse(new URL(url), 10000);
        return page;
    }
    public static String[] mainMethod() throws IOException {
        String tempCurr = "";
        String imgCurr = "";
        String tempForecast = "";
        String imgForecast = "";
        String stringsNow = "";
        String stringsFor = "";
        String[] factWeatherInfo = new String[4];
        try {
            Document page = getPage();
            Element informer = page.select("div[class=fact__temp-wrap]").first();
            Element tempNow = null;
            if (informer != null) {
                tempNow = informer.select("span[class=temp__value temp__value_with-unit]").first();
            }
            tempCurr = tempNow.text();

            stringsNow = informer.select("img").toString();

            if (stringsNow.contains("skc-n ")){ //ясно night
                imgCurr = "skc_n";
            } else if (stringsNow.contains("skc-d ")){ //ясно day
                imgCurr = "skc_d";
            } else if (stringsNow.contains("bkn-n ")){ //облачно night
                imgCurr = "bkn_n";
            } else if (stringsNow.contains("bkn-d ")){ //облачно day
                imgCurr = "bkn_d";
            } else if (stringsNow.contains("ovc-sn ")){ //снег
                imgCurr = "ovc_sn";
            } else if (stringsNow.contains("ovc-m-sn ")){ //небольшой снег
                imgCurr = "ovc_m_sn";
            } else if (stringsNow.contains("ovc-m-ra ")){ //небольшой дождь
                imgCurr = "ovc_m_ra";
            } else if (stringsNow.contains("ovc ")){ //пасмурно
                imgCurr = "ovc";
            } else if (stringsNow.contains("ovc-ra-sn ")){ //дождь со снегом
                imgCurr = "ovc_ra_sn";
            } else if (stringsNow.contains("ovc-ra ")){ //дождь
                imgCurr = "ovc_ra";
            } else if (stringsNow.contains("bkn-p-ra-d ")){ //ливень днём
                imgCurr = "bkn_p_ra_d";
            } else if (stringsNow.contains("bkn-p-ra-n ")){ //ливень night
                imgCurr = "bkn_p_ra_n";
            }
           // Log.i("fff:", tempCurr);
            factWeatherInfo[0] = tempCurr;
            factWeatherInfo[1] = imgCurr;

        }catch (NullPointerException e){
            e.printStackTrace();
        }
        try {
            Calendar calendar = Calendar.getInstance();
            Document page2 = getPage();
            Elements forecast = page2.select("ul[class=swiper-wrapper]");
            Element forecast1;
            Elements forecast2 = null;
            Element forecast3 = null;
//            Log.i("ggg:", String.valueOf(forecast));
            if (forecast.size() >= 2) {
                forecast1 = forecast.get(1);
                forecast2 = forecast1.getAllElements();
            }

            if (calendar.get(Calendar.DAY_OF_WEEK) == 7){ //in saturday forecast for sunday
                forecast3 = forecast2.select("li[class=forecast-briefly__day forecast-briefly__day_sunday forecast-briefly__day_weekend swiper-slide]").get(0);
            } else if (calendar.get(Calendar.DAY_OF_WEEK) == 1){ //in sunday forecast for monday
                forecast3 = forecast2.select("li[class=forecast-briefly__day forecast-briefly__day_weekstart swiper-slide]").get(0);
            } else if (calendar.get(Calendar.DAY_OF_WEEK) == 6){  //in friday forecast for saturday
                forecast3 = forecast2.select("li[class=forecast-briefly__day forecast-briefly__day_weekend swiper-slide]").get(0);
            } else { // in other weekdays
                forecast3 = forecast2.select("li[class=forecast-briefly__day swiper-slide]").get(1);
            }
            Element tempTomorr = forecast3.select("span[class=temp__value temp__value_with-unit]").first();
            tempForecast = tempTomorr.text();
            stringsFor = forecast3.select("img").toString();

            Log.i("ggg:", String.valueOf(page2));

            if (stringsFor.contains("skc-n ")){ //ясно night
                imgForecast = "skc_n";
            } else if (stringsFor.contains("skc-d ")){ //ясно day
                imgForecast = "skc_d";
            } else if (stringsFor.contains("bkn-n ")){ //облачно night
                imgForecast = "bkn_n";
            } else if (stringsFor.contains("bkn-d ")){ //облачно day
                imgForecast = "bkn_d";
            } else if (stringsFor.contains("ovc-sn ")){ //снег
                imgForecast = "ovc_sn";
            } else if (stringsFor.contains("ovc-m-sn ")){ //небольшой снег
                imgForecast = "ovc_m_sn";
            } else if (stringsFor.contains("ovc-m-ra ")){ //небольшой дождь
                imgForecast = "ovc_m_ra";
            } else if (stringsFor.contains("ovc ")){ //пасмурно
                imgForecast = "ovc";
            } else if (stringsFor.contains("ovc-ra-sn ")){ //дождь со снегом
                imgForecast = "ovc_ra_sn";
            } else if (stringsFor.contains("ovc-ra ")){ //дождь
                imgForecast = "ovc_ra";
            } else if (stringsFor.contains("bkn-p-ra-d ")){ //ливень днём
                imgForecast = "bkn_p_ra_d";
            } else if (stringsFor.contains("bkn-p-ra-n ")){ //ливень night
                imgForecast = "bkn_p_ra_n";
            }
            factWeatherInfo[2] = tempForecast;
            factWeatherInfo[3] = imgForecast;

        }catch (NullPointerException e) {
            e.printStackTrace();
        }
        Log.i("fff:", "temp1:" + tempCurr + "\n" + "img1:" + imgCurr + "\n" + "temp2:" + tempForecast + "\n" + "img2:" + imgForecast);
        return factWeatherInfo;
    }
}
