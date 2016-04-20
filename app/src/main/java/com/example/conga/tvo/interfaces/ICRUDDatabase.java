package com.example.conga.tvo.interfaces;

import com.example.conga.tvo.models.ContentRss;
import com.example.conga.tvo.models.RssItem;

import java.util.ArrayList;

/**
 * Created by ConGa on 19/04/2016.
 */
public interface ICRUDDatabase {
    //offline
    public void addNewItemContent(ContentRss contentRss);
    public ArrayList<ContentRss> getAllItemsContent();
    public void deleteItemContent(int id_content);
    //favorites
    public void addNewItemRss(RssItem rssItem);
    public ArrayList<RssItem> getAllItemsRss();
    public void deleteItemRssItem(int id_rss);
}
