package com.example.conga.tvo.models;

/**
 * Created by ConGa on 15/04/2016.
 */
public class ContentRss {
    private static String TAG = ContentRss.class.getSimpleName();
    public int id_content;
    public String title;
  //  public String image;
    public String content;

    public ContentRss(int id_content, String title, String content) {
        this.id_content = id_content;
        this.title = title;
        this.content = content;
    }

    public static String getTAG() {
        return TAG;
    }

    public static void setTAG(String TAG) {
        ContentRss.TAG = TAG;
    }

    public int getId_content() {
        return id_content;
    }

    public void setId_content(int id_content) {
        this.id_content = id_content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ContentRss{" +
                "id_content=" + id_content +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
