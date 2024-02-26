package com.lawlie8.gutenbergreader.resourceHelpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class GutenbergResourceHelperController {

    @Autowired
    private GutenbergResourceService gutenbergResourceService;

    @GetMapping(path="/users/")
    public ResponseEntity<?> getUserByUsername() {
        gutenbergResourceService.fetchDailyRDFFiles();

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/application/properties/version",method = RequestMethod.GET)
    public ResponseEntity<?> applicationVersion(){
        String version = "0.1";
        return new ResponseEntity<>(version,HttpStatus.OK);
    }

}
