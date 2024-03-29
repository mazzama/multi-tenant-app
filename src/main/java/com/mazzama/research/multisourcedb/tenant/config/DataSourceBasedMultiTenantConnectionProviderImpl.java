package com.mazzama.research.multisourcedb.tenant.config;

import com.mazzama.research.multisourcedb.master.domain.MasterTenant;
import com.mazzama.research.multisourcedb.master.repository.MasterTenantRepository;
import com.mazzama.research.multisourcedb.tenant.model.Product;
import com.mazzama.research.multisourcedb.tenant.repository.ProductRepository;
import com.mazzama.research.multisourcedb.util.DataSourceUtil;
import com.mazzama.research.multisourcedb.util.TenantContextHolder;
import org.hibernate.cfg.Environment;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

@Configuration
public class DataSourceBasedMultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

    private static final Logger LOG = LoggerFactory.getLogger(DataSourceBasedMultiTenantConnectionProviderImpl.class);
    private static final long serialVersionUID = 1L;

    @Autowired
    private MasterTenantRepository tenantRepository;

    private Map<String, DataSource> dataSourceMap = new TreeMap<>();

    @Override
    protected DataSource selectAnyDataSource() {
        if (dataSourceMap.isEmpty()) {
            List<MasterTenant> masterTenants = tenantRepository.findAll();
            LOG.info(">>>> selectAnyDataSource() -- Total tenants: {}", masterTenants.size());

            for (MasterTenant masterTenant : masterTenants) {
                DataSource dataSource = DataSourceUtil.createAndConfigureDataSource(masterTenant);
                generateSchema(dataSource);
                dataSourceMap.put(masterTenant.getTenantId(), dataSource);
            }
        }
        return this.dataSourceMap.values().iterator().next();
    }

    @Override
    protected DataSource selectDataSource(String tenantIdentifier) {

        LOG.info("Select Tenant : {}", tenantIdentifier);
        tenantIdentifier = initializeTenantIfLost(tenantIdentifier);
        if (!this.dataSourceMap.containsKey(tenantIdentifier)) {
            List<MasterTenant> masterTenants = tenantRepository.findAll();

            for (MasterTenant masterTenant : masterTenants) {
                DataSource dataSource = DataSourceUtil.createAndConfigureDataSource(masterTenant);
                generateSchema(dataSource);
                dataSourceMap.put(masterTenant.getTenantId(), dataSource);
            }
        }
        return this.dataSourceMap.get(tenantIdentifier);
    }

    private String initializeTenantIfLost(String tenantIdentifier) {

        LOG.info("Tenant Context Holder: {}", TenantContextHolder.getTenant());
        if (TenantContextHolder.getTenant() == null) {

            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                String tenantId = attributes.getRequest().getHeader("X-TenantId");
                tenantIdentifier = tenantId;
            } else {
                tenantIdentifier = "tenant_1";
            }
        }

        // TODO: implement filter to get current tenant
        /*
        if (!tenantIdentifier.equals(TenantContextHolder.getTenant())) {
            tenantIdentifier = TenantContextHolder.getTenant();
        }
         */

        LOG.info("Initialize Tenant : {}", tenantIdentifier);
        return tenantIdentifier;
    }

    private void generateSchema(DataSource dataSource) {
        LOG.info("Start the configuration of entityManagerFactory for data source : {}", dataSource);

        LocalContainerEntityManagerFactoryBean emfBean = new LocalContainerEntityManagerFactoryBean();
        emfBean.setDataSource(dataSource);
        emfBean.setPackagesToScan(new String[]{Product.class.getPackage().getName(), ProductRepository.class.getPackage().getName()});
        emfBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        emfBean.setPersistenceUnitName(dataSource.toString());

        Properties properties = new Properties();
        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
        properties.put(Environment.SHOW_SQL, true);
        properties.put(Environment.FORMAT_SQL, true);
        properties.put(Environment.HBM2DDL_AUTO, "update");
        emfBean.setJpaProperties(properties);

        LOG.info("End pf entityManagerFactory {} setup", dataSource);
        emfBean.afterPropertiesSet();
        emfBean.destroy();
    }
}
