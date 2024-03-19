package com.lawlie8.gutenbergreader.resourceHelpers;

import com.fasterxml.jackson.databind.JsonNode;
import com.lawlie8.gutenbergreader.DTOs.dailyRssDtos.DailyRssBookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/app")
public class GutenbergReaderController {

    @Autowired
    GutenbergResourceService gutenbergResourceService;

    @RequestMapping(path="/rss/book/daily",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDailyRssBooks() {
        DailyRssBookDto dailyRssBookDto = gutenbergResourceService.fetchDailyRssBookTitles();
        return new ResponseEntity<>(dailyRssBookDto, HttpStatus.OK);
    }
}
