package com.lawlie8.gutenbergreader.resourceHelpers;

import com.fasterxml.jackson.databind.JsonNode;
import com.lawlie8.gutenbergreader.DTOs.dailyRssDtos.DailyRssBookDto;
import com.lawlie8.gutenbergreader.util.XMLReaderUtilService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/app")
public class GutenbergReaderController {

    @Autowired
    GutenbergResourceService gutenbergResourceService;

    @Autowired
    XMLReaderUtilService xmlReaderUtilService;

    @RequestMapping(path="/rss/book/daily",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDailyRssBooks() {
        DailyRssBookDto dailyRssBookDto = gutenbergResourceService.fetchDailyRssBookTitles();
        return new ResponseEntity<>(dailyRssBookDto, HttpStatus.OK);
    }

    @RequestMapping(path="/user/all",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllUsers() {
        List<String> users = gutenbergResourceService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(path="/rss/book/daily/sync",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDailyRssBooksFromSource() {
        try {
            xmlReaderUtilService.downLoadRssDailyBooks();
            return new ResponseEntity<>(true, HttpStatus.OK);
       }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }
}
