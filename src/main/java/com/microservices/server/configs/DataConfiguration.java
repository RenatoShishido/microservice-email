package com.microservices.server.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;

@Configuration
@Profile("dev")
public class DataConfiguration {
    
    @Value("${spring.datasource.url}")
    public String url;
    
    @Value("${spring.datasource.driverClassName}")
    public String driverClassName;
    
    @Value("${spring.jpa.database-platform}")
    public String dataBaseDialect;
    
    @Value("${spring.datasource.username}")
    public String username;
    
    @Value("${spring.datasource.password}")
    public String password;
    
    @Value("${spring.jpa.hibernate.ddl-auto}")
    public String ddlUpdate;
    
    @Value("${spring.jpa.show-sql}")
    public boolean showSql; 
    
    @Value("${spring.jpa.database}")
    public Database database;
    
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(driverClassName);
        driverManagerDataSource.setUrl(url);
        driverManagerDataSource.setUsername(username);
        driverManagerDataSource.setPassword(password);
        return driverManagerDataSource;
    }
    
    @Bean
    public HibernateJpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setDatabase(database);
        hibernateJpaVendorAdapter.setShowSql(showSql);
        hibernateJpaVendorAdapter.setDatabasePlatform(dataBaseDialect);
        hibernateJpaVendorAdapter.setGenerateDdl(true);
        hibernateJpaVendorAdapter.setPrepareConnection(true);
        return hibernateJpaVendorAdapter;
    }
}
