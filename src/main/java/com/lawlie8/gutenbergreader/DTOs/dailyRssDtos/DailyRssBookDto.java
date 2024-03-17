package com.lawlie8.gutenbergreader.DTOs.dailyRssDtos;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.lawlie8.gutenbergreader.DTOs.dailyRssDtos.channel.Channel;

import java.util.Date;

@JacksonXmlRootElement(localName = "rss")
public class DailyRssBookDto {

    @JacksonXmlProperty(localName = "channel")
    @JacksonXmlElementWrapper(useWrapping = false)
    private Channel[] channel;

    public Channel[] getChannel() {
        return channel;
    }

    public void setChannel(Channel[] channel) {
        this.channel = channel;
    }
}
