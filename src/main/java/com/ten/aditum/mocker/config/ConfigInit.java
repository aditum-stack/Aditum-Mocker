package com.ten.aditum.mocker.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigInit {
//
//    @Value("${spring.datasource.name}")
//    private String name;

    @Value("${datasource.url}")
    private String url;
//
//    @Value("${spring.datasource.username}")
//    private String username;
//
//    @Value("${spring.datasource.password}")
//    private String password;

    @RequestMapping(value = "/hi")
    public String show() {
        return this.url;
    }

}
