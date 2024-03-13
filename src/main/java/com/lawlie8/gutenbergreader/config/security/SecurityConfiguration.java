package com.lawlie8.gutenbergreader.config.security;

import com.lawlie8.gutenbergreader.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Bean
    public DaoAuthenticationProvider userDetailsService(CustomUserDetailService customUserDetailService, PasswordEncoder passwordEncoder){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider ;
    }

    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.requestMatchers(AntPathRequestMatcher.antMatcher("/login")).permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/signup")).permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/web/**")).permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/app/**")).authenticated())
                .httpBasic(Customizer.withDefaults())
                .formLogin(login -> login.loginPage("/login").permitAll())
                .logout(logout->logout.logoutUrl("/logout").permitAll())
                .build();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
