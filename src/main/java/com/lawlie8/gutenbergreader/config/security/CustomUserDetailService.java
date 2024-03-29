package com.lawlie8.gutenbergreader.config.security;

import com.lawlie8.gutenbergreader.entities.Users;
import com.lawlie8.gutenbergreader.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Users users = userRepo.findByUserNameNative(userName);
        if(users == null){
            throw new  UsernameNotFoundException("User Not Found");
        }
        return new CustomUserDetails(users);
    }
}
