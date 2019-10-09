package com.mazzama.research.multisourcedb.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties("multitenant")
public class MultiTenantProperties {

    private List<DataSourceProperties> dataSources;

    public List<DataSourceProperties> getDataSources() {
        return this.dataSources;
    }

    public void setDataSources(List<DataSourceProperties> dataSources) {
        this.dataSources = dataSources;
    }

    public static class DataSourceProperties extends org.springframework.boot.autoconfigure.jdbc.DataSourceProperties {

        private String tenantId;

        public String getTenantId() {
            return tenantId;
        }

        public void setTenantId(String tenantId) {
            this.tenantId = tenantId;
        }
    }
}
