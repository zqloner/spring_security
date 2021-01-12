package com.zq.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Description:
 * @Author: zhangqi
 * @CreateTime: 2021/1/1120:16
 * @Company: MGL
 */
@Configuration
public class SecurityConfigTest extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(password());
    }

    @Bean
    PasswordEncoder password() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //配置没有权限访问的页面
        http.exceptionHandling().accessDeniedPage("/unAuth.html");
        http.formLogin()   //自定义自己编写的登陆页面
                .loginPage("/login.html")  //登陆页面设置
                .loginProcessingUrl("/user/login") //登陆访问的路径
                .defaultSuccessUrl("/test/index").permitAll()  //登陆成功之后跳转路径
                .and().authorizeRequests()
                .antMatchers("/","/test/hello","/user/login").permitAll()  //设置哪些路径可以不需要认证也可以访问

//                hasAuthority("")  针对某个权限
//                .antMatchers("/test/index").hasAuthority("admins")
//               hasAnyAuthority()  针对多个权限
//                .antMatchers("/test/index").hasAnyAuthority("manager,admins")

//                .hasRole()        针对某个角色
//                .antMatchers("/test/index").hasRole("sale")
//                .hasAnyRole()        针对多个个角色
                .antMatchers("/test/index").hasAnyRole("unAuth")
                .anyRequest().authenticated()
                .and().csrf().disable();    //关闭csrf防护
    }
}
