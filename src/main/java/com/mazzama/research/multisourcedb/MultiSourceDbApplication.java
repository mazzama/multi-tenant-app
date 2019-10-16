package com.mazzama.research.multisourcedb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@Slf4j
public class MultiSourceDbApplication {

    private static ApplicationContext context;

    public static void main(String[] args) {
        context = SpringApplication.run(MultiSourceDbApplication.class, args);
//        displayAllBeans();
    }

    private static void displayAllBeans() {
        String[] beans = context.getBeanDefinitionNames();
        log.info("total beans : {}", context.getBeanDefinitionCount());
        for (String bean: beans) {
            log.info("bean : {}", bean);
        }
    }

}
