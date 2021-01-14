package com.zq.service.aclservice.service.impl;

import com.zq.security.entity.SecurityUser;
import com.zq.service.aclservice.entity.User;
import com.zq.service.aclservice.service.PermissionService;
import com.zq.service.aclservice.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: zhangqi
 * @CreateTime: 2021/1/14--->0:07
 * @Company: MGL
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //根据用户名查询数据
        User user = userService.selectByUsername(username);

        //判断
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }

        com.zq.security.entity.User curUser = new com.zq.security.entity.User();

        BeanUtils.copyProperties(user,curUser);

        //根据用户信息查询出权限列表
        List<String> permissionValueList = permissionService.selectPermissionValueByUserId(user.getId());

        SecurityUser securityUser = new SecurityUser();
        securityUser.setCurrentUserInfo(curUser);
        securityUser.setPermissionValueList(permissionValueList);
        return securityUser;
    }
}
