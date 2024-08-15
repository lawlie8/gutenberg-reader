package com.lawlie8.gutenbergreader.resourceHelpers;

import com.fasterxml.jackson.databind.JsonNode;
import com.lawlie8.gutenbergreader.DTOs.dailyRssDtos.DailyRssBookDto;
import com.lawlie8.gutenbergreader.util.XMLReaderUtilService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/app")
public class GutenbergReaderController {

    @Autowired
    GutenbergResourceService gutenbergResourceService;

    @Autowired
    XMLReaderUtilService xmlReaderUtilService;

    @Autowired
    AssetObjectFileProcessorService assetObjectFileProcessorService;

    @RequestMapping(path = "/rss/book/daily", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDailyRssBooks() {
        DailyRssBookDto dailyRssBookDto = gutenbergResourceService.fetchDailyRssBookTitles();
        return new ResponseEntity<>(dailyRssBookDto, HttpStatus.OK);
    }

    @RequestMapping(path = "/user/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllUsers() {
        List<String> users = gutenbergResourceService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @RequestMapping(path = "/blob/image/asset/{bookId}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getBookCoverForId(@PathVariable(name = "bookId") Long bookId) {
        return new ResponseEntity<>(assetObjectFileProcessorService.getBookCoverForId(bookId), HttpStatus.OK);
    }

    @RequestMapping(path = "/blob/zip/asset/{assetId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> downloadZipForAssetId(@PathVariable(name = "assetId") Long assetId) {
        Resource resource = new ByteArrayResource(assetObjectFileProcessorService.getZipBlobforDownload(assetId));
        return ResponseEntity.ok().body(resource);
    }

    @RequestMapping(path = "/blob/epub/asset/{assetId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> downloadEpubForAssetId(@PathVariable(name = "assetId") Long assetId) {
        Resource resource = new ByteArrayResource(assetObjectFileProcessorService.getEpubBlobforDownload(assetId));
        return ResponseEntity.ok().body(resource);
    }

    @RequestMapping(path = "/rss/book/daily/sync", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDailyRssBooksFromSource() {
        try {
            xmlReaderUtilService.downLoadRssDailyBooks();
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }
}
