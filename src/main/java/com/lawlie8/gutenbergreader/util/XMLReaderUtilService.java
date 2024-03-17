package com.lawlie8.gutenbergreader.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.lawlie8.gutenbergreader.DTOs.dailyRssDtos.DailyRssBookDto;
import com.lawlie8.gutenbergreader.DTOs.dailyRssDtos.channel.item.Item;

import java.io.File;
import java.util.List;

public class XMLReaderUtilService {

    public static JsonNode buildDailyRssBookDataFromXML() throws Exception {
        File xmlFile = new File("C:\\Users\\lawlie8\\Downloads\\today.rss");
        XmlMapper xmlMapper = new XmlMapper();
        JsonNode jsonNode = xmlMapper.readTree(xmlFile);
        return jsonNode;
    }


}
