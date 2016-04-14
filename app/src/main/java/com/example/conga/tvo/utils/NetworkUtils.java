package com.example.conga.tvo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by ConGa on 29/03/2016.
 */
public class NetworkUtils {
    private Context context;
    public NetworkUtils(Context context){
        this.context = context;
    }

    public boolean isConnectingToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }

    public static void main(String[] args) {
        final String USER_AGENT_BROWER = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36";
        try {
            Document doc = Jsoup.connect("http://vnexpress.net")
                    .timeout(10*1000)
                    .userAgent(USER_AGENT_BROWER).get();
            doc.outputSettings().escapeMode(Entities.EscapeMode.xhtml);

            Elements elements = doc.select("ul.list_menu_header li a");
            for (Element element : elements){
                String href = element.attr("href");
                String title = element.text();
                System.out.println(title);
                System.out.println(href);
            }
//            System.out.println(elements);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
