//package com.zq.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
///**
// * @Description:
// * @Author: zhangqi
// * @CreateTime: 2021/1/1120:16
// * @Company: MGL
// */
//@Configuration
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        String password = bCryptPasswordEncoder.encode("123");
//        auth.inMemoryAuthentication().withUser("zhangqi").password(password).roles("admin");
//    }
//
//    @Bean
//    PasswordEncoder password() {
//        return new BCryptPasswordEncoder();
//    }
//}
