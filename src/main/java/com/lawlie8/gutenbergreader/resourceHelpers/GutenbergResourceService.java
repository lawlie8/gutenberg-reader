package com.lawlie8.gutenbergreader.resourceHelpers;

import com.fasterxml.jackson.databind.JsonNode;
import com.lawlie8.gutenbergreader.DTOs.dailyRssDtos.BooksDTO;
import com.lawlie8.gutenbergreader.DTOs.dailyRssDtos.DailyRssBookDto;
import com.lawlie8.gutenbergreader.DTOs.dailyRssDtos.channel.Channel;
import com.lawlie8.gutenbergreader.DTOs.dailyRssDtos.channel.item.Item;
import com.lawlie8.gutenbergreader.config.security.CustomUserDetails;
import com.lawlie8.gutenbergreader.entities.Books;
import com.lawlie8.gutenbergreader.repositories.BlobObjectsRepo;
import com.lawlie8.gutenbergreader.repositories.BooksRepo;
import com.lawlie8.gutenbergreader.util.XMLReaderUtilService;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
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

    @Autowired
    private BlobObjectsRepo blobObjectsRepo;

    Logger log = LoggerContext.getContext().getLogger(this.getClass().getName());


    public boolean checkIfDailyRssFileExists() {
        return true;
    }

    public boolean downloadDailyRssFile() {
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

    /**
     * Returns Latest Books From Database
     * if no books exists on current day
     * Recursively decrements day by one till it finds some data
    * */
    public List<Books> fetchDailyBookTitlesFromDb(Date dt) {

        try {
            Date date = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy").parse(String.valueOf(dt));
            String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
            List<Books> books = booksRepo.getAllBooksForToday(formattedDate);

            if (books.isEmpty()) {
                dt = DateUtils.addDays(dt, -1);
                return fetchDailyBookTitlesFromDb(dt);
            }
            return books;
        } catch (Exception e) {
            log.error("Exception Occurred While Fetching Latest Books : " + e);
            return null;
        }
    }

    public List<BooksDTO> searchBooksFromDatabase(String searchElement) {
        List<Books> books = new ArrayList<>();
        List<BooksDTO> booksDTOList = new ArrayList<>();
        try {
            books = booksRepo.searchBooksByName("%" + searchElement + "%");
            for (Books books1 : books){
                BooksDTO booksDTO = new BooksDTO();
                booksDTO.setBookId(books1.getBookId());
                booksDTO.setTitle(books1.getTitle());
                booksDTO.setImageData(blobObjectsRepo.fetchBlobByIdAndType(books1.getBookId(),"picture"));
                booksDTOList.add(booksDTO);
            }
        } catch (Exception e) {
            log.error("Exception Occurred While Searching Book with name : " + searchElement);
        }
        return booksDTOList;
    }


    public List<Books> fetchAllBooksPageble(Integer size, Integer page) {
        List<Books> books = new ArrayList<>();
        try {
            Integer low = page * size;
            books = booksRepo.findAllByPage(low, size);
        } catch (Exception e) {
            log.error("Exception Occurred While Fetching Books for size {} : page {} : {}", size, page, e);
        }
        return books;
    }

    public List<String> getAllUsers() {
        List<String> allUsers = new ArrayList<>();
        List<Object> principals = sessionRegistry.getAllPrincipals();
        for (Object principal : principals) {
            List<SessionInformation> session = sessionRegistry.getAllSessions(principal, false);
            session.get(0).expireNow();
            if (principal instanceof CustomUserDetails) {
                allUsers.add(((CustomUserDetails) principal).getUsername());
            }
        }
        return allUsers;
    }

    private DailyRssBookDto convertJsonNodetoDTO(JsonNode node) throws Exception {
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

    private List<Item> convertJsonNodeToItemClass(JsonNode itemNode) {
        List<Item> itemList = new ArrayList<>();
        for (int i = 0; i < itemNode.size(); i++) {
            Item item = new Item();
            item.setTitle(itemNode.get(i).get("title").toString());
            item.setDescription(itemNode.get(i).get("description").toString());
            item.setLink(itemNode.get(i).get("link").toString());
            itemList.add(item);
        }
        return itemList;
    }

}
