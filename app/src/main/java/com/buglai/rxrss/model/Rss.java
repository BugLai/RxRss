package com.buglai.rxrss.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Created by buglai on 16-5-11.
 */

@Root(name = "rss",strict = false)
public class Rss implements Serializable{

    @Element(name = "channel",required = false)
    private Channel channel;

    @Attribute(name = "version",required = false)
    private String version;


    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
