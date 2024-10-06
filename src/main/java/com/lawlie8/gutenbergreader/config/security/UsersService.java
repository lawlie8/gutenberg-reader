package com.lawlie8.gutenbergreader.config.security;

import com.lawlie8.gutenbergreader.DTOs.dailyRssDtos.NewUserDTO;
import com.lawlie8.gutenbergreader.entities.Users;
import com.lawlie8.gutenbergreader.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class UsersService {

    @Autowired
    UserRepo userRepo;



    public boolean checkIfUserAlreadyExists(String userName){
        return userRepo.checkIfUserAlreadyExists(userName) >=1 ? true : false;
    }

    public Users saveNewUser(NewUserDTO newUserDTO){
        Users users = new Users();
        users.setUserName(newUserDTO.getUsername());
        users.setPasswordHash(
                passwordEncoder()
                        .encode(new String(
                                Base64.getDecoder()
                                        .decode(newUserDTO.getPassword()))));
        users.setRole("USER_ROLE");
        return userRepo.save(users);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
