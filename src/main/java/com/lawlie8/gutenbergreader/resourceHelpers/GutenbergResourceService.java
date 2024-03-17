package com.lawlie8.gutenbergreader.resourceHelpers;

import com.fasterxml.jackson.databind.JsonNode;
import com.lawlie8.gutenbergreader.DTOs.dailyRssDtos.DailyRssBookDto;
import com.lawlie8.gutenbergreader.util.XMLReaderUtilService;
import org.springframework.stereotype.Service;

@Service
public class GutenbergResourceService {

    public boolean checkIfDailyRssFileExists(){
        return true;
    }

    public boolean downloadDailyRssFile(){
        return true;
    }

    public JsonNode fetchDailyRssBookTitles() {
        if (checkIfDailyRssFileExists()) {
            try {
                JsonNode dailyRssBookDto = XMLReaderUtilService.buildDailyRssBookDataFromXML();
                return dailyRssBookDto;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return null;
        }
        return null;
    }
}
