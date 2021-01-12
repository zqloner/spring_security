package com.zq.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zq.entity.Users;
import com.zq.mapper.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: zhangqi
 * @CreateTime: 2021/1/1120:56
 * @Company: MGL
 */
@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UsersMapper usersMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //调用usersMapper的方法根据用户名查询数据库。
        QueryWrapper<Users> wrapper= new QueryWrapper<>();
        wrapper.eq("username", s);
        Users users = usersMapper.selectOne(wrapper);
        //判断
        if (users == null) { //数据库没有这个用户名,认证失败
            throw new UsernameNotFoundException("用户名不存在");
        }
        List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_sale,ROLE_manager");

        //重数据库查询的Users中得到用户名和密码,组装成security的User返回
        return new User(users.getUsername(),new BCryptPasswordEncoder().encode(users.getPassword()),auths);
    }
}
