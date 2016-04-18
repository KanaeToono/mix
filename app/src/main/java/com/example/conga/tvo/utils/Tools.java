package com.example.conga.tvo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ConGa on 18/04/2016.
 */
public class Tools {
    public int newFeeds;
    public boolean hasMoreFeeds = true;
    private static String TAG = Tools.class.getSimpleName();

    public Tools() {

    }

    public void verifyDates(String rssPubDate, String lastPubDate) {
        if (hasMoreFeeds) {
            SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
            Date dLastPubDate = null;
            Date dRssPubDate = null;
            try {
                dLastPubDate = format.parse(lastPubDate.substring(5));
                dRssPubDate = format.parse(rssPubDate.substring(5));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (dRssPubDate.getTime() > dLastPubDate.getTime()) {
                newFeeds++;
            } else {
                hasMoreFeeds = false;
            }
        }
    }
    public  Date parseXmlPubDate(String pubDateRss) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
        Date pubParseDateRss = null;
        pubParseDateRss= format.parse(pubDateRss.substring(5));
        return pubParseDateRss;
    }
}
