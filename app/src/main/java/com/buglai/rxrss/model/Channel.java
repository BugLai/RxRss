package com.buglai.rxrss.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

import java.io.Serializable;
import java.util.List;

/**
 * Created by buglai on 16-5-11.
 */
@Root(name = "channel", strict = false)
public class Channel implements Serializable{

    @Path("title")
    @Text(required = false, data = true)
    private String title;

    @Path("link")
    @Text(required = false, data = true)
    private String link;

    @Element(required = false, data = true)
    private String description;


    @Element(required = false, data = true)
    private String lastBuildDate;

    @Element(required = false, data = true)
    private String image;

    @Element(required = false, data = true)
    private String copyright;

    @Element(required = false, data = true)
    private String language;


    @ElementList(entry = "item", inline = true)
    private List<Item> items;


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

    public String getLastBuildDate() {
        return lastBuildDate;
    }

    public void setLastBuildDate(String lastBuildDate) {
        this.lastBuildDate = lastBuildDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }


    @Override
    public String toString() {
        return "Channel{" +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", link='" + link + '\'' +
                ", lastBuildDate='" + lastBuildDate + '\'' +
                ", language='" + language + '\'' +
                ", items=" + items +
                '}';
    }
}
