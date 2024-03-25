package com.ssafy.matchup.global.config.database;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
@Slf4j
@PropertySource("classpath:application.yml")
public class JpaConfig implements EnvironmentAware {

    @Value("${ip.db.user}")
    String userDB;

    @Value("${ip.db-repl.user}")
    String userDBRepl;

    @Value("${id.db}")
    String DBId;

    @Value("${id.db-repl}")
    String DBReplId;

    @Value("${password.db}")
    String DBPassword;

    private Environment env;

    @Override
    public void setEnvironment(final Environment environment) {
        this.env = environment;
    }

    @Bean // 원본 DB와 연결된 DataSource
    @Qualifier("userDataSource")
    public DataSource userDataSource() {
        HikariDataSource dataSource = DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://" + userDB + ":3308/matchup_user_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Seoul&characterEncoding=UTF-8")
                .username(DBId)
                .password(DBPassword)
                .type(HikariDataSource.class)
                .build();
        dataSource.setMaximumPoolSize(5);
        return dataSource;
    }

    @Bean // Repl DB와 연결된 DataSource
    @Qualifier("userReplDataSource")
    public DataSource userReplDataSource() {
        return DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://" + userDBRepl + ":3309/matchup_user_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Seoul&characterEncoding=UTF-8")
                .username(DBReplId)
                .password(DBPassword)
                .type(HikariDataSource.class)
                .build();
    }

    @Bean // DataSource 종류에 따른 DataSource 라우팅(변경)
    public DataSource routingDataSource(@Qualifier("userDataSource") DataSource userDataSource,
                                        @Qualifier("userReplDataSource") DataSource userReplDataSource) {
        ReplicationRoutingDataSource routingDataSource = new ReplicationRoutingDataSource();

        // DataSource 라우팅
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put("original", userDataSource);
        dataSourceMap.put("replication", userReplDataSource);

        // 기본 DataSource 및 ReadOnly 여부에 따른 DataSource 설정
        routingDataSource.setDefaultTargetDataSource(userDataSource);
        routingDataSource.setTargetDataSources(dataSourceMap);

        return routingDataSource;
    }

    @Bean  // Transaction 실행 시점에 DataSource를 결정하기 위한 Proxy
    public DataSource routingLazyDataSource(@Qualifier("routingDataSource") DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

    @Bean
        // Entity를 관리하기 위한 JPA Manager 설정
    LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Qualifier("routingLazyDataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setJpaVendorAdapter(jpaVendorAdapter());
        emf.setPackagesToScan("com.ssafy.matchup.*");
        emf.setJpaProperties(customProperties());
        return emf;
    }

    private JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setShowSql(true);
        jpaVendorAdapter.setGenerateDdl(true);
        return jpaVendorAdapter;
    }

    private Properties customProperties() {
        Properties properties = new Properties();
        properties.setProperty("spring.jpa.hibernate.ddl-auto", env.getProperty("ddl-auto"));
        properties.setProperty("spring.jpa.show-sql", env.getProperty("show-sql"));
        properties.setProperty("spring.jpa.properties.hibernate.format_sql", env.getProperty("format_sql"));
        properties.setProperty("spring.jpa.properties.hibernate.dialect", env.getProperty("dialect"));
        return properties;
    }

    @Bean  // 트랜잭션 매니저 설정
    public PlatformTransactionManager transactionManager(
            EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
        return jpaTransactionManager;
    }

}
