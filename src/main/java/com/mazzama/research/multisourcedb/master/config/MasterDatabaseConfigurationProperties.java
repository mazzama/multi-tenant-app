package com.mazzama.research.multisourcedb.master.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@ToString
@Configuration
@ConfigurationProperties("multitenant.master.datasource")
public class MasterDatabaseConfigurationProperties {

    private String url;
    private String username;
    private String password;
    private String driverClassName;
    private long connectionTimeOut;
    private int maxPoolSize;
    private long idleTimeout;
    private int minIdle;
    private String poolName;
}
