package com.example.conga.tvo.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.Date;

/**
 * Created by ConGa on 29/03/2016.
 */
public class RssItem implements Parcelable {
    private static String TAG = RssItem.class.getSimpleName();
    private int id_rssItem;
    private String title;
    private String link;
    private Date pubDate;
    private int status_check;
    private int status_piority;

    private String imageUrl;
    private  String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;

        if (description.contains("<img ")){
            String img  = description.substring(description.indexOf("<img "));
            String cleanUp = img.substring(0, img.indexOf(">")+1);
            img = img.substring(img.indexOf("src=") + 5);
            int indexOf = img.indexOf("'");
            if (indexOf==-1){
                indexOf = img.indexOf("\"");
            }
            img = img.substring(0, indexOf);
            setImageUrl(img);

            this.description = this.description.replace(cleanUp, "");
        }

    }

//    private void setImgLink(String img) {
//        this.imageUrl =img;
//    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public RssItem(String title, String link, Date pubDate, int status_check, int status_piority) {
        this.title = title;
        this.link = link;
        this.pubDate = pubDate;
        this.status_check = status_check;
        this.status_piority = status_piority;
   //     this.status_delete_date = status_delete_date;
        Log.d(TAG, "create rss object");
    }

    public RssItem(int id_rssItem, String title, String link, Date pubDate, int status_check, int status_piority) {
        this.id_rssItem = id_rssItem;
        this.title = title;
        this.link = link;
        this.pubDate = pubDate;
        this.status_check = status_check;
        this.status_piority = status_piority;
//        this.status_delete_date = status_delete_date;
        Log.d(TAG, "create rss object");
    }

    public RssItem() {
        Log.d(TAG, "create rss object");

    }

//    public int getStatus_delete_date() {
//        return status_delete_date;
//    }
//
//    public void setStatus_delete_date(int status_delete_date) {
//        this.status_delete_date = status_delete_date;
//    }

    public int getStatus_check() {
        return status_check;
    }

    public void setStatus_check(int status_check) {
        this.status_check = status_check;
    }

    public int getStatus_piority() {
        return status_piority;
    }

    public void setStatus_piority(int status_piority) {
        this.status_piority = status_piority;
    }

    public int getId_rssItem() {
        return id_rssItem;
    }

    public void setId_rssItem(int id_rssItem) {
        this.id_rssItem = id_rssItem;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    @Override
    public String toString() {
        return "RssItem{" +
                "id_rssItem=" + id_rssItem +
                ", title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", pubDate=" + pubDate +
                ", status_check=" + status_check +
                ", status_piority=" + status_piority +
//                ", status_delete_date=" + status_delete_date +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(id_rssItem);
        parcel.writeString(title);
        parcel.writeString(link);
        parcel.writeLong(pubDate.getTime());
        parcel.writeInt(status_check);
        parcel.writeInt(status_piority);
//        parcel.writeInt(status_delete_date);
    }
    public static final Parcelable.Creator<RssItem> CREATOR = new Creator<RssItem>() {
        public RssItem createFromParcel(Parcel source) {
            RssItem mRssItem = new RssItem();
            mRssItem.id_rssItem = source.readInt();
            mRssItem.title = source.readString();
            mRssItem.link = source.readString();
            mRssItem.pubDate = new Date(source.readLong());
            mRssItem.status_check = source.readInt();
            mRssItem.status_piority=source.readInt();
//            mRssItem.status_delete_date=source.readInt();
            return mRssItem;
        }

        public RssItem[] newArray(int size) {
            return new RssItem[size];

        }

    };

}
