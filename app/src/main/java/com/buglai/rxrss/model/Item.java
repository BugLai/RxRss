package com.buglai.rxrss.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Created by buglai on 16-5-11.
 */
@Root(name = "item",strict = false)
public class Item implements Serializable {

    @Element(name="title",required = false, data = true)
    public String title;

    @Element(name="link",required = false, data = true)
    public String link;

//    @Element(name="comments",required = false, data = true)
//    public String comments;

    @Element(name="creator",required = false, data = true)
    public String creator;

    @Element(name="pubDate",required = false, data = true)
    public String pubDate;

//    @Element(name="guid",required = false, data = true)
//    public String guid;


    @Element(name="encoded",required = false)
    public String content;

    @Element(name="description",required = false, data = true)
    public String description;


    @Element(name="image",required = false, data = true)
    @Attribute
    public String icon;


    private boolean isRead;

    public String image;

    private String newsCat;

    private int newsType;


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

//    public String getComments() {
//        return comments;
//    }
//
//    public void setComments(String comments) {
//        this.comments = comments;
//    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

//    public String getGuid() {
//        return guid;
//    }
//
//    public void setGuid(String guid) {
//        this.guid = guid;
//    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }


    public String getNewsCat() {
        return newsCat;
    }

    public void setNewsCat(String newsCat) {
        this.newsCat = newsCat;
    }


    public int getNewsType() {
        return newsType;
    }

    public void setNewsType(int newsType) {
        this.newsType = newsType;
    }

    @Override
    public String toString() {
        return "Item{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
//                ", comments='" + comments + '\'' +
                ", pubDate=" + pubDate +
//                ", guid='" + guid + '\'' +
                ", description='" + description + '\'' +
//                ", category='" + category + '\'' +
                ", icon='" + icon + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
