package com.ten.aditum.mocker;

import com.ten.aditum.mocker.config.CommunityMetaInit;
import com.ten.aditum.mocker.execute.CommunityTablesHolder;
import com.ten.aditum.mocker.execute.ThreadPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@SpringBootApplication
public class AditumMockerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AditumMockerApplication.class, args);

        new CommunityMetaInit().init();

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ThreadPoolConfig.class);
        CommunityTablesHolder service = context.getBean(CommunityTablesHolder.class);

        for (int i = 0; i < 1; i++) {
            service.run(); // 执行异步任务
        }
        context.close();
    }

}
