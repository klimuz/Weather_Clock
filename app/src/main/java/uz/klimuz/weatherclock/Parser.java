package uz.klimuz.weatherclock;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;

public class Parser {
    private static Document getPage() throws IOException {
        String url = "https://yandex.uz/pogoda/tashkent?utm_campaign=informer&utm_content=main_informer&utm_medium=web&utm_source=home&lat=41.311151&lon=69.279737";

        Document page = Jsoup.parse(new URL(url), 10000);
        return page;
    }
    public static String[] mainMethod() throws IOException {
        String temperature = "";
        String conditions = "";
        String[] factWeatherInfo = new String[2];
        try {
            Document page = getPage();
            Element informer = page.select("div[class=fact__temp-wrap]").first();
            Element temp = informer.select("span[class=temp__value temp__value_with-unit]").first();
            String strings = informer.select("img").toString();
            temperature = temp.select("span[class=temp__value temp__value_with-unit]").text();
            conditions = informer.select("div[class=link__feelings fact__feelings]").text();

            if (strings.contains("skc-n ")){ //ясно night
                conditions = "skc-n";
            } else if (strings.contains("skc-d ")){ //ясно day
                conditions = "skc-d";
            } else if (strings.contains("bkn-n ")){ //облачно night
                conditions = "bkn-n";
            } else if (strings.contains("bkn-d ")){ //облачно day
                conditions = "bkn-d";
            } else if (strings.contains("ovc-sn ")){ //снег
                conditions = "ovc-sn";
            } else if (strings.contains("ovc-m-sn ")){ //небольшой снег
                conditions = "ovc-m-sn";
            } else if (strings.contains("ovc-m-ra ")){ //небольшой дождь
                conditions = "ovc-m-ra";
            } else if (strings.contains("ovc ")){ //пасмурно
                conditions = "ovc";
            } else if (strings.contains("ovc-r-sn ")){ //дождь со снегом
                conditions = "ovc-r-sn";
            } else if (strings.contains("ovc-r ")){ //дождь
                conditions = "ovc-r";
            } else if (strings.contains("bkn-p-ra-d ")){ //ливень днём
                conditions = "bkn-p-ra-d";
            } else if (strings.contains("bkn-p-ra-n ")){ //ливень night
                conditions = "bkn-p-ra-n";
            }
            factWeatherInfo[0] = temperature;
            factWeatherInfo[1] = conditions;
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return factWeatherInfo;
    }
}
