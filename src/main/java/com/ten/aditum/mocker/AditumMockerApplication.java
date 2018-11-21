package com.ten.aditum.mocker;

import com.ten.aditum.mocker.config.ConfigInit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AditumMockerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AditumMockerApplication.class, args);

        ConfigInit configInit = new ConfigInit();
        configInit.run();
    }
}
