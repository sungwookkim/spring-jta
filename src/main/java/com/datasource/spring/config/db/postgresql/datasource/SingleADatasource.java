package com.datasource.spring.config.db.postgresql.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * <pre>
 *     Single-A DB 데이터 소스 설정 클래스.
 * </pre>
 */
@Configuration
public class SingleADatasource {
    private final String driverClassName;
    private final String jdbcUrl;
    private final String username;
    private final String password;

    public SingleADatasource(@Value("${spring.datasource.postgres.singleA.driver-class-name}") String driverClassName
            , @Value("${spring.datasource.postgres.singleA.jdbc-url}") String jdbcUrl
            , @Value("${spring.datasource.postgres.singleA.username}") String username
            , @Value("${spring.datasource.postgres.singleA.password}") String password) {
        this.driverClassName = driverClassName;
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
    }

    /**
     * <pre>
     *     단일트랜잭션 데이터소스는 {@link HikariDataSource} 구현체를 사용.
     * </pre>
     */
    @Bean
    public DataSource postgresqlSingleADataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setPoolName("pg-single-a");
        hikariDataSource.setDriverClassName(this.driverClassName);
        hikariDataSource.setUsername(this.username);
        hikariDataSource.setJdbcUrl(this.jdbcUrl);
        hikariDataSource.setPassword(this.password);

        return hikariDataSource;
    }

}
