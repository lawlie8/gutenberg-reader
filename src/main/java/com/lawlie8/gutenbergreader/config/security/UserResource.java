package com.lawlie8.gutenbergreader.config.security;

import com.lawlie8.gutenbergreader.DTOs.dailyRssDtos.NewUserDTO;
import com.lawlie8.gutenbergreader.entities.Books;
import com.lawlie8.gutenbergreader.entities.Users;
import com.lawlie8.gutenbergreader.repositories.UserRepo;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/web")
public class UserResource {
    Logger log = LoggerContext.getContext().getLogger(this.getClass().getName());

    @Autowired
    private UsersService usersService;

    @RequestMapping(path = "/signup", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDailyRssBooks(@RequestBody NewUserDTO newUserDTO) {
        try {
            boolean userAlreadyExists = usersService.checkIfUserAlreadyExists(newUserDTO.getUsername());
            if (userAlreadyExists) {
                return new ResponseEntity<>("user Already exists", HttpStatus.CONFLICT);
            } else {
                usersService.saveNewUser(newUserDTO);
                return new ResponseEntity<>("Created new User", HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("Exception Occurred While Creating New User", e);
            return new ResponseEntity<>("Could Not Create User", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "/user/check/{userName}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> checkIfUserIsValid(@PathVariable("userName") String userName){
        try {
            boolean userAlreadyExists = usersService.checkIfUserAlreadyExists(userName);
            return new ResponseEntity<>(userAlreadyExists, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}
