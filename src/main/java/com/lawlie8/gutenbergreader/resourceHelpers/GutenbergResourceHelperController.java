package com.lawlie8.gutenbergreader.resourceHelpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GutenbergResourceHelperController {

    @Autowired
    private GutenbergResourceService gutenbergResourceService;

    @GetMapping(path="/users/")
    public ResponseEntity<?> getUserByUsername() {
        gutenbergResourceService.fetchDailyRDFFiles();

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
