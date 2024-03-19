package com.lawlie8.gutenbergreader.resourceHelpers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawlie8.gutenbergreader.DTOs.dailyRssDtos.DailyRssBookDto;
import com.lawlie8.gutenbergreader.DTOs.dailyRssDtos.channel.Channel;
import com.lawlie8.gutenbergreader.DTOs.dailyRssDtos.channel.item.Item;
import com.lawlie8.gutenbergreader.util.XMLReaderUtilService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GutenbergResourceService {

    public boolean checkIfDailyRssFileExists(){
        return true;
    }

    public boolean downloadDailyRssFile(){
        return true;
    }

    public DailyRssBookDto fetchDailyRssBookTitles() {
        if (checkIfDailyRssFileExists()) {
            try {
                JsonNode jsonNode = XMLReaderUtilService.buildDailyRssBookDataFromXML();

                return convertJsonNodetoDTO(jsonNode);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return null;
        }
        return null;
    }

    private DailyRssBookDto convertJsonNodetoDTO(JsonNode node) throws Exception{
        DailyRssBookDto dailyRssBookDto = new DailyRssBookDto();
        Channel channel = new Channel();
        channel.setTitle(node.get("channel").get("title").toString());
        channel.setDescription(node.get("channel").get("description").toString());
        channel.setLink(node.get("channel").get("link").toString());
        channel.setLanguage(node.get("channel").get("language").toString());
        channel.setPubDate(node.get("channel").get("pubDate").toString());
        channel.setItem(convertJsonNodeToItemClass(node.get("channel").get("item")));
        dailyRssBookDto.setChannel(channel);
        return dailyRssBookDto;
    }

    private List<Item> convertJsonNodeToItemClass(JsonNode itemNode){
        List<Item> itemList = new ArrayList<>();
        for(int i = 0;i<itemNode.size();i++){
            Item item = new Item();
            item.setTitle(itemNode.get(i).get("title").toString());
            item.setDescription(itemNode.get(i).get("description").toString());
            item.setLink(itemNode.get(i).get("link").toString());
            itemList.add(item);
        }
        return itemList;
    }

}
