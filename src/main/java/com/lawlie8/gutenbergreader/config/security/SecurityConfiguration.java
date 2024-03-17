package com.lawlie8.gutenbergreader.config.security;

import com.lawlie8.gutenbergreader.repositories.UserRepo;
import org.apache.commons.logging.Log;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration{

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    SucessHandler sucessHandler;

    @Autowired
    FailuerHandler failureHandler;

    @Autowired
    LogOutSuccessHandler logOutSuccessHandler;

    @Bean
    public DaoAuthenticationProvider userDetailsService(CustomUserDetailService customUserDetailService, PasswordEncoder passwordEncoder){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider ;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain mainFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity.httpBasic((basic)-> basic.disable()).csrf((csrf) -> csrf.disable()).authorizeHttpRequests((auth) -> {
            auth.requestMatchers(AntPathRequestMatcher.antMatcher("/app/**")).authenticated();
            auth.requestMatchers(AntPathRequestMatcher.antMatcher("/web/**")).permitAll();
        }).formLogin(httpSecurityFormLoginConfigurer -> {
            httpSecurityFormLoginConfigurer
                    .successHandler(sucessHandler)
                    .failureHandler(failureHandler)
                    .loginProcessingUrl("/web/auth")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .permitAll();
        }).logout((logout)-> logout.logoutUrl("/web/logout").logoutSuccessHandler(logOutSuccessHandler).deleteCookies("JSESSIONID").permitAll()).build();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
