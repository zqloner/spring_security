package com.zq.controller;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: zhangqi
 * @CreateTime: 2021/1/109:46
 * @Company: MGL
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("hello")
    public String hello() {
        return "hello security";
    }

    @GetMapping("index")
    public String index() {
        return "hello index";
    }

    @GetMapping("update")
//    @Secured({"ROLE_sale","ROLE_manager"})
//    @PreAuthorize("hasAnyAuthority('adminsss')")
    @PostAuthorize("hasAnyAuthority('admins')")
    public String update() {
        System.out.println("update........");
        return "hello update";
    }

}
