package com.lawlie8.gutenbergreader.resourceHelpers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawlie8.gutenbergreader.DTOs.dailyRssDtos.DailyRssBookDto;
import com.lawlie8.gutenbergreader.DTOs.dailyRssDtos.channel.Channel;
import com.lawlie8.gutenbergreader.DTOs.dailyRssDtos.channel.item.Item;
import com.lawlie8.gutenbergreader.config.security.CustomUserDetails;
import com.lawlie8.gutenbergreader.entities.Books;
import com.lawlie8.gutenbergreader.repositories.BooksRepo;
import com.lawlie8.gutenbergreader.util.XMLReaderUtilService;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.spi.LoggerContextFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class GutenbergResourceService {

    @Autowired
    @Qualifier("sessionRegistry")
    private SessionRegistry sessionRegistry;

    @Autowired
    private BooksRepo booksRepo;


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

    public List<Books> fetchDailyBookTitlesFromDb(){
        List<Books>  books = booksRepo.getAllBooksForToday(new Date());
        return books;
    }

    public List<String> getAllUsers(){
        List<String> allUsers = new ArrayList<>();
        List<Object> principals = sessionRegistry.getAllPrincipals();
        for (Object principal: principals) {
            List<SessionInformation> session = sessionRegistry.getAllSessions(principal,false);
            session.get(0).expireNow();
            if (principal instanceof CustomUserDetails) {
                allUsers.add(((CustomUserDetails) principal).getUsername());
            }
        }
        return allUsers;
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
