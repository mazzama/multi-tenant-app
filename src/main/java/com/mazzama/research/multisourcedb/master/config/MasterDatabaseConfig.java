package com.mazzama.research.multisourcedb.master.config;

import com.mazzama.research.multisourcedb.master.domain.MasterTenant;
import com.mazzama.research.multisourcedb.master.repository.MasterTenantRepository;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"com.mazzama.research.multisourcedb.master.domain", "com.mazzama.research.multisourcedb.master.repository"},
        entityManagerFactoryRef = "masterEntityManagerFactory",
        transactionManagerRef = "masterTransactionManager")
public class MasterDatabaseConfig {

    private static final Logger LOG = LoggerFactory.getLogger(MasterDatabaseConfig.class);

    @Autowired
    private MasterDatabaseConfigurationProperties databaseConfigProperties;

    @Bean(name = "masterDataSource")
    public DataSource masterDataSource() {
        LOG.info("Setting up masterDataSource with: {}", databaseConfigProperties);

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(databaseConfigProperties.getUrl());
        dataSource.setUsername(databaseConfigProperties.getUsername());
        dataSource.setPassword(databaseConfigProperties.getPassword());
        dataSource.setDriverClassName(databaseConfigProperties.getDriverClassName());
        dataSource.setPoolName(databaseConfigProperties.getPoolName());
        dataSource.setMaximumPoolSize(databaseConfigProperties.getMaxPoolSize());
        dataSource.setMinimumIdle(databaseConfigProperties.getMinIdle());
        dataSource.setIdleTimeout(databaseConfigProperties.getIdleTimeout());
        dataSource.setConnectionTimeout(databaseConfigProperties.getConnectionTimeOut());

        LOG.info("Setup of masterDataSource succeeded.");
        return dataSource;
    }

    @Primary
    @Bean(name = "masterEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean masterEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(masterDataSource());
        em.setPackagesToScan(MasterTenant.class.getPackage().getName(),
                        MasterTenantRepository.class.getPackage().getName());
        em.setPersistenceUnitName("masterdb-persistence-unit");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(hibernateProperties());
        LOG.info("Setup of masterEntityManagerFactory succeeded.");
        return em;
    }

    @Bean(name = "masterTransactionManager")
    public JpaTransactionManager masterTransactionManager(@Qualifier("masterEntityManagerFactory") EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }
    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put(org.hibernate.cfg.Environment.DIALECT,
                "org.hibernate.dialect.MySQL5Dialect");
        properties.put(org.hibernate.cfg.Environment.SHOW_SQL, true);
        properties.put(org.hibernate.cfg.Environment.FORMAT_SQL, true);
        properties.put(org.hibernate.cfg.Environment.HBM2DDL_AUTO, "update");
        return properties;
    }
}
