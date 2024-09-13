package com.lawlie8.gutenbergreader.reader.epub;

import com.lawlie8.gutenbergreader.reader.DTO.EpubContentDTO;
import com.lawlie8.gutenbergreader.reader.DTO.EpubReaderRequestBodyClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/app")
public class EpubReaderController {

    @Autowired
    private EpubReaderService epubReaderService;

    @RequestMapping(path = "/read",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> readEpubByMethod(@RequestBody EpubReaderRequestBodyClass epubReaderRequestBodyClass) throws IOException {
        EpubContentDTO epubContentDTO = epubReaderService.getEpubContent(epubReaderRequestBodyClass);
        return new ResponseEntity<>(epubContentDTO,HttpStatus.OK);
    }

}
