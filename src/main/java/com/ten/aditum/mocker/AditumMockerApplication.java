package com.ten.aditum.mocker;

import com.ten.aditum.mocker.config.CommunityMetaInit;
import com.ten.aditum.mocker.execute.CommunityTablesHolder;
import com.ten.aditum.mocker.execute.ThreadPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@SpringBootApplication
public class AditumMockerApplication {

    @Autowired
    private static ThreadPoolConfig threadPoolConfig;

    @Autowired
    private static CommunityMetaInit communityMetaInit;

    @Autowired
    private static CommunityTablesHolder communityTablesHolder;

    public static void main(String[] args) {
        SpringApplication.run(AditumMockerApplication.class, args);

        communityMetaInit.init();

        threadPoolConfig.taskExecutor();

        for (int i = 0; i < 10; i++) {
            communityTablesHolder.run();
        }
    }

}
