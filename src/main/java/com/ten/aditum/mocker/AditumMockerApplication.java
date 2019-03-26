package com.ten.aditum.mocker;

import com.ten.aditum.mocker.config.CommunityMetaInit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AditumMockerApplication {

    static {
        new CommunityMetaInit().init();
    }

    public static void main(String[] args) {
        SpringApplication.run(AditumMockerApplication.class, args);
    }

}
