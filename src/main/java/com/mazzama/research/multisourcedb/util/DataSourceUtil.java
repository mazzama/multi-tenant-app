package com.mazzama.research.multisourcedb.util;

import com.mazzama.research.multisourcedb.master.domain.MasterTenant;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

public class DataSourceUtil {

    private static final Logger LOG = LoggerFactory.getLogger(DataSourceUtil.class);

    public static DataSource createAndConfigureDataSource(MasterTenant masterTenant) {
        String tenantId = masterTenant.getTenantId();
        String tenantConnectionPoolName = tenantId + "-connection-pool";

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(masterTenant.getUrl());
        dataSource.setUsername(masterTenant.getUsername());
        dataSource.setPassword(masterTenant.getPassword());
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");

        dataSource.setConnectionTimeout(20000);
        dataSource.setMinimumIdle(10);
        dataSource.setMaximumPoolSize(20);
        dataSource.setIdleTimeout(300000);
        dataSource.setConnectionTimeout(20000);
        dataSource.setPoolName(tenantConnectionPoolName);

        LOG.info("Configured datasource:" + masterTenant.getTenantId()
                + ". Connection poolname:" + tenantConnectionPoolName);
        return dataSource;
    }
}
