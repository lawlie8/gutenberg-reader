package com.lawlie8.gutenbergreader.config.security;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SecurityHelperResource {

    /***
     * Used for client to handle spring-security redirects
     * @return
     */
    @RequestMapping(path ="/web/logreq",method= RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> processLoginRequest(){
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

}
