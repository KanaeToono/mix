package com.example.conga.tvo.models;

import android.util.Log;

/**
 * Created by ConGa on 12/04/2016.
 */
public class RssItem {
    private int id;
    private String title;
    private String description;
    private String image;
    private String link;
    private String pubDate;

    public RssItem() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RssItem(String title, String link, String pubDate,String image) {
        this.title =title;
        this.link =link;
        this.image=image;
        this.pubDate=pubDate;
    }


    // private String linkTag;
   
//    public String getLinkTag() {
//        return linkTag;
//    }
//
//    public void setLinkTag(String linkTag) {
//        this.linkTag = linkTag;
//    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {

            this.description = description;
//           if (description.contains("<a href=")){
//               String linkTag = description.substring(description.indexOf("<a href="), description.indexOf("<img"));
//               Log.d("LINK TAG" , linkTag);
//               String cleanUpLinkTag = linkTag.substring(0, linkTag.indexOf(">")+1);
//               Log.d("CLEAN UP link ",cleanUpLinkTag);
//               linkTag=linkTag.substring(linkTag.indexOf("<a href=") +9);
//               Log.d("link tag 2" , linkTag);
//               int indexOf = linkTag.indexOf("'");
//
//               if (indexOf==-1){
//                   indexOf = linkTag.indexOf("\"");
//               }
//               linkTag = linkTag.substring(0, indexOf);
//               Log.d("link tag 2" , linkTag);
//               setLinkTag(linkTag);
//               this.description = this.description.replace(cleanUpLinkTag, "");
//           }

if (description.contains("<img ")){
                String img  = description.substring(description.indexOf("<img "));
    Log.d("IMAGE" , img);
                String cleanUp = img.substring(0, img.indexOf(">")+1);
    Log.d("clean up " , cleanUp);

                img = img.substring(img.indexOf("src=") + 5);
    Log.d("image 1", img);
                int indexOf = img.indexOf("'");
                if (indexOf==-1){
                    indexOf = img.indexOf("\"");
                }
                img = img.substring(0, indexOf);
    Log.d("image 2", img);
                setImage(img);

                this.description = this.description.replace(cleanUp, "");
            }


    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public String getPubDate() {
        return pubDate;
    }
    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }
}
