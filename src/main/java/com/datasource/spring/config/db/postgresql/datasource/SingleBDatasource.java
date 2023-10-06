package com.datasource.spring.config.db.postgresql.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SingleBDatasource {
    private final String driverClassName;
    private final String jdbcUrl;
    private final String username;
    private final String password;

    public SingleBDatasource(@Value("${spring.datasource.postgres.singleB.driver-class-name}") String driverClassName
            , @Value("${spring.datasource.postgres.singleB.jdbc-url}") String jdbcUrl
            , @Value("${spring.datasource.postgres.singleB.username}") String username
            , @Value("${spring.datasource.postgres.singleB.password}") String password) {
        this.driverClassName = driverClassName;
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
    }

    /**
     * <pre>
     * Single-B DB datasource 반환 메서드
     * </pre>
     *
     * @return Single-B db 접속 datasource 객체
     */
    @Bean
    public DataSource postgresqlSingleBDataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setPoolName("pg-single-b");
        hikariDataSource.setDriverClassName(this.driverClassName);
        hikariDataSource.setUsername(this.username);
        hikariDataSource.setJdbcUrl(this.jdbcUrl);
        hikariDataSource.setPassword(this.password);

        return hikariDataSource;
    }

}
