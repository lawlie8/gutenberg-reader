package com.lawlie8.gutenbergreader.resourceHelpers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/app")
public class GutenbergReaderController {

    @GetMapping(path="/users/")
    public ResponseEntity<?> getUserByUsername() {

        return new ResponseEntity<>("works", HttpStatus.OK);
    }
}
