package com.example.conga.tvo.models;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ConGa on 12/04/2016.
 */
public class RssHandler extends DefaultHandler{
    private static String TAG = RssHandler.class.getSimpleName();
    private RssItem item;
    private List<RssItem> items = new ArrayList<RssItem>();
    private StringBuffer sBuffer = new StringBuffer();
    private boolean started = false;
    public static String ITEM = "item";
    public static String TITLE = "title";
    public static String DESCRIPTION = "description";
    public static String LINK = "link";
    public static String PUBDATE = "pubDate";


    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        super.characters(ch, start, length);
        if (started && (sBuffer != null)) {
            sBuffer.append(ch, start, length);
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if (localName.equalsIgnoreCase(ITEM)) {
            started = true;
            item = new RssItem();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        super.endElement(uri, localName, qName);
        if (localName.equalsIgnoreCase(ITEM)) {
           // item.setImage(getImage(item.getDescription()));
            items.add(item);
            started = false;

            Log.d("Title", item.getTitle());
            Log.d("Description", item.getDescription());
            Log.d("Link", item.getLink());
             Log.d("Date", item.getPubDate());
            Log.d("Image", item.getImage());
         //  Log.d("Link Tag" , item.getLinkTag());
        } else if (started) {
            if (localName.equalsIgnoreCase(TITLE)) {
                item.setTitle(sBuffer.toString().trim());
            } else if (localName.equalsIgnoreCase(DESCRIPTION)) {
                item.setDescription(sBuffer.toString().trim());
            } else if (localName.equalsIgnoreCase(LINK)) {
                item.setLink(sBuffer.toString().trim());
            } else if (localName.equalsIgnoreCase(PUBDATE)) {

                    item.setPubDate(sBuffer.toString().trim());

            }
            sBuffer = new StringBuffer();
        }
    }

//    private String getImage(String description) {
//        int a = description.indexOf("src=");
//        int start = description.indexOf("\"", a);
//        int end = description.indexOf("\"", start + 1);
//        Log.d("a", a + "");
//        Log.d("start", start + "");
//        Log.d("end", end + "");
//        String image = "";
//        return image;
//    }
private String getImage(String description) {
    int a = description.indexOf("src=");
    int start = description.indexOf("\"", a);
    int end = description.indexOf("\"", start + 1);
    Log.d("a", a + "");
    Log.d("start", start + "");
    Log.d("end", end + "");
    String image = "";
    Log.d("Image", image);
    return image;
}

    public List<RssItem> getItems() {
        return this.items;
    }
}
