package com.ssafy.matchup_statistics.global.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.dialect.Dialect;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@Slf4j
@PropertySources(@PropertySource("classpath:application.yml"))
public class JpaConfig {

    @Value("${ip.db-repl.user}")
    String userDBRepl;

    @Value("${id.db-repl}")
    String DBReplId;

    @Value("${password.db}")
    String DBPassword;

    @Value("${physical-strategy}")
    String PHYSICAL_STRATEGY;

    @Value("${ddl-auto}")
    String DDL_AUTO;

    @Value("${format_sql}")
    String FORMAT_SQL;

    @Value("${dialect}")
    String DIALECT;

    @Value("${default_batch_fetch_size}")
    String DEFAULT_BATCH_FETCH_SIZE;

    @Bean // Repl DB와 연결된 DataSource
    @Qualifier("userReplDataSource")
    public DataSource userReplDataSource() {
        HikariDataSource dataSource = DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://" + userDBRepl + ":3309/matchup_user_db?allowPublicKeyRetrieval=true&serverTimezone=Asia/Seoul&characterEncoding=UTF-8")
                .username(DBReplId)
                .password(DBPassword)
                .type(HikariDataSource.class)
                .build();
        dataSource.setMaximumPoolSize(30);
        return dataSource;
    }

    @Bean // Entity를 관리하기 위한 JPA Manager 설정
    LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(userReplDataSource());
        emf.setJpaVendorAdapter(jpaVendorAdapter());
        emf.setPackagesToScan("com.ssafy.matchup_statistics.*");
        emf.setJpaProperties(customProperties());
        return emf;
    }

    @Bean  // 트랜잭션 매니저 설정
    public PlatformTransactionManager transactionManager(
            EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
        return jpaTransactionManager;
    }

    private JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setShowSql(true);
        jpaVendorAdapter.setGenerateDdl(true);
        return jpaVendorAdapter;
    }

    private Properties customProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", DIALECT);
        properties.setProperty("hibernate.default_batch_fetch_size", DEFAULT_BATCH_FETCH_SIZE);
        return properties;
    }

}
