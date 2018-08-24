package com.abc.demo.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories({"com.abc.demo.jpa.repository"})
@EnableTransactionManagement
public class DemoApplicationConfig {

  @Bean
  public DataSource dataSource() {
    String jdbcUrl = "jdbc:h2:mem:abc;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE";
    String jdbcDriver = "org.h2.Driver";
    String jdbcUsername = "sa";
    String jdbcPassword = "";

    HikariDataSource dataSource = new HikariDataSource();
    dataSource.setDriverClassName(jdbcDriver);
    dataSource.setJdbcUrl(jdbcUrl);
    dataSource.setUsername(jdbcUsername);
    dataSource.setPassword(jdbcPassword);
    dataSource.setMaximumPoolSize(20);

    return dataSource;
  }

  @Bean(name = "entityManagerFactory")
  public EntityManagerFactory entityManagerFactory() {
    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean =
        new LocalContainerEntityManagerFactoryBean();
    entityManagerFactoryBean.setDataSource(dataSource());
    entityManagerFactoryBean.setPackagesToScan("com.abc.demo.jpa.model");
    entityManagerFactoryBean.setPersistenceUnitName("pu");

    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    vendorAdapter.setShowSql(false);
    vendorAdapter.setGenerateDdl(true);
    vendorAdapter.setDatabase(Database.H2);

    entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
    entityManagerFactoryBean.afterPropertiesSet();

    return entityManagerFactoryBean.getObject();
  }

  @Bean(name = "transactionManager")
  public PlatformTransactionManager transactionManager() {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManagerFactory());

    return transactionManager;
  }
}
