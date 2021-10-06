package com.kkorchyts.config.hibernate;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class HibernateConfig {

    private String hibernateConnectionUserName;

    private String hibernateConnectionPassword;

    private String hibernateConnectionDriverClass;

    private String hibernateConnectionUrl;

    private String hibernateDialect;

    private Boolean showSql;

    private String hibernatePackageToScan;


    @Bean(name = "SessionFactory")
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(hibernatePackageToScan);
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    private Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty(
                "hibernate.dialect", hibernateDialect);
        hibernateProperties.setProperty(
                "show_sql", showSql.toString());
        return hibernateProperties;
    }

    @Bean
    public PlatformTransactionManager hibernateTransactionManager() {
        HibernateTransactionManager transactionManager
                = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(hibernateConnectionDriverClass);
        dataSource.setUrl(hibernateConnectionUrl);
        dataSource.setUsername(hibernateConnectionUserName);
        dataSource.setPassword(hibernateConnectionPassword);
        return dataSource;
    }

    @Value("${hibernate.connection.username}")
    public void setHibernateConnectionUserName(String hibernateConnectionUserName) {
        this.hibernateConnectionUserName = hibernateConnectionUserName;
    }

    @Value("${hibernate.connection.password}")
    public void setHibernateConnectionPassword(String hibernateConnectionPassword) {
        this.hibernateConnectionPassword = hibernateConnectionPassword;
    }

    @Value("${hibernate.connection.driver_class}")
    public void setHibernateConnectionDriverClass(String hibernateConnectionDriverClass) {
        this.hibernateConnectionDriverClass = hibernateConnectionDriverClass;
    }

    @Value("${hibernate.connection.url}")
    public void setHibernateConnectionUrl(String hibernateConnectionUrl) {
        this.hibernateConnectionUrl = hibernateConnectionUrl;
    }

    @Value("${hibernate.dialect}")
    public void setHibernateDialect(String hibernateDialect) {
        this.hibernateDialect = hibernateDialect;
    }

    @Value("${show_sql}")
    public void setShowSql(Boolean showSql) {
        this.showSql = showSql;
    }

    @Value("${hibernate.entitiespackage}")
    public void setHibernatePackageToScan(String hibernatePackageToScan) {
        this.hibernatePackageToScan = hibernatePackageToScan;
    }
}
