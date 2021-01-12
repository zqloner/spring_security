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
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

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

    @Autowired
    private DataSource dataSource;

    //配置jdbcTokenRepository对象
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
//        jdbcTokenRepository.setCreateTableOnStartup(true); //自动创建表
        return jdbcTokenRepository;
    }

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
        //退出
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/test/hello").permitAll();

        //配置没有权限访问的页面
        http.exceptionHandling().accessDeniedPage("/unAuth.html");
        http.formLogin()   //自定义自己编写的登陆页面
                .loginPage("/login.html")  //登陆页面设置
                .loginProcessingUrl("/user/login") //登陆访问的路径
                .defaultSuccessUrl("/successful.html").permitAll()  //登陆成功之后跳转路径
                .and().authorizeRequests()
                .antMatchers("/", "/test/hello", "/user/login").permitAll()  //设置哪些路径可以不需要认证也可以访问

//                hasAuthority("")  针对某个权限
//                .antMatchers("/test/index").hasAuthority("admins")
//               hasAnyAuthority()  针对多个权限
//                .antMatchers("/test/index").hasAnyAuthority("manager,admins")

//                .hasRole()        针对某个角色
//                .antMatchers("/test/index").hasRole("sale")
//                .hasAnyRole()        针对多个个角色
                .antMatchers("/test/index").hasAnyRole("unAuth")
                .anyRequest().authenticated()

                //配置自动登陆
                .and().rememberMe().tokenRepository(persistentTokenRepository())     //设置操作对象
                .tokenValiditySeconds(60)  //设置多久过期,秒计时
                .userDetailsService(userDetailsService);


//                .and().csrf().disable();    //关闭csrf防护
    }
}
