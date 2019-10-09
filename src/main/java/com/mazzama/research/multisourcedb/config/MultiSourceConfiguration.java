package com.mazzama.research.multisourcedb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories("com.mazzama.research.multisourcedb")
@EnableTransactionManagement
public class MultiSourceConfiguration {

    @Autowired
    private MultiTenantProperties tenantProperties;

    @Bean
    public DataSource dataSource() {
        CustomRoutingDataSource customRoutingDataSource = new CustomRoutingDataSource();
        Map<Object, Object> dataSources = new HashMap<>();
        for (MultiTenantProperties.DataSourceProperties properties: tenantProperties.getDataSources()) {
            DataSourceBuilder factory = DataSourceBuilder.create()
                    .url(properties.getUrl())
                    .driverClassName(properties.getDriverClassName())
                    .username(properties.getUsername())
                    .password(properties.getPassword());
            dataSources.put(properties.getTenantId(), factory.build());

        }
        customRoutingDataSource.setTargetDataSources(dataSources);
        return customRoutingDataSource;
    }
}
