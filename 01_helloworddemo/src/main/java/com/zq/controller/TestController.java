package com.zq.controller;

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
@RequestMapping
public class TestController {

    @GetMapping
    public String hello() {
        return "hello security";
    }
}
