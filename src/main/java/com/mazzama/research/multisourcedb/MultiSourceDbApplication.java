package com.mazzama.research.multisourcedb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MultiSourceDbApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultiSourceDbApplication.class, args);
    }

}
