package com.example.conga.tvo.interfaces;

import com.example.conga.tvo.models.RssItem;

import java.util.ArrayList;

/**
 * Created by ConGa on 19/04/2016.
 */
public interface ICRUDDatabase {
    //daotao
    public void addNewItemRssDaoTao(RssItem rssItem);
    public ArrayList<RssItem> getAllItemsRssDaoTao();
    public void deleteItemRssItemDaoTao(int id_rss);
  //  public void upDateStatusTinQuanTrongDaoTao(RssItem rssItem);

    //khoatin
    public void addNewItemRssKhoaTin(RssItem rssItem);
    public ArrayList<RssItem> getAllItemsRssKhoaTin();
    public void deleteItemRssItemKhoaTin(int id_rss);
  //  public void upDateStatusTinQuanTrongKhoaTin(RssItem rssItem);
}
