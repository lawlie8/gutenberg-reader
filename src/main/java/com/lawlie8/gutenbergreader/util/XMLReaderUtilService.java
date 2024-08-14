package com.lawlie8.gutenbergreader.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.lawlie8.gutenbergreader.repositories.GutenbergConfigPropertiesRepo;
import com.lawlie8.gutenbergreader.resourceHelpers.AssetObjectFileProcessorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.lawlie8.gutenbergreader.util.Constants.RECENT_BOOKS_KEY;
import static com.lawlie8.gutenbergreader.util.Constants.RECENT_BOOKS_RSS_TITLE;

@Service
public class XMLReaderUtilService {
    Logger log = LoggerFactory.getLogger(XMLReaderUtilService.class);

    @Autowired
    GutenbergConfigPropertiesRepo gutenbergConfigPropertiesRepo;

    @Autowired
    AssetObjectFileProcessorService assetObjectFileProcessorServer;

    public static JsonNode buildDailyRssBookDataFromXML() throws Exception {
        File xmlFile = new File(getFormattedDate() + "-today.rss");
        XmlMapper xmlMapper = new XmlMapper();
        return xmlMapper.readTree(xmlFile);
    }


    @Scheduled(cron = "0 12 * * * *")
    @Async
    public void downLoadRssDailyBooks() {
        try {
            log.info("Downloading Daily Recent books Rss file");
            if (!recentBookRssFileExists()) {
                String recentBooksRssUrl = gutenbergConfigPropertiesRepo.getValueByKeyName(RECENT_BOOKS_KEY);
                URL website = new URL(recentBooksRssUrl);
                ReadableByteChannel rbc = Channels.newChannel(website.openStream());
                FileOutputStream fos = new FileOutputStream(getFormattedDate() + "-" + RECENT_BOOKS_RSS_TITLE);
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                assetObjectFileProcessorServer.processDailyDownloadDataIntoDatabase();
            } else {
                log.info("File Already Exists Skipping Download");
            }
        } catch (Exception e) {
            log.error("Exception Occurred while downloading File", e);
        }
    }

    private boolean recentBookRssFileExists() {
        try {
            return new File(getFormattedDate() + "-" + RECENT_BOOKS_RSS_TITLE).exists();
        } catch (Exception e) {
            return false;
        }
    }

    public static String getFormattedDate() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return today.format(formatter);
    }


}
