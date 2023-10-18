package com.datasource.spring.config.db.postgresql.datasource;

import com.atomikos.spring.AtomikosDataSourceBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import javax.sql.XADataSource;
import java.util.Properties;

/**
 * <pre>
 *     Single-B DB JTA 데이터 소스 설정 클래스.
 * </pre>
 */
@Configuration
public class JtaSingleBDatasource {
    private final String driverClassName;
    private final String jdbcUrl;
    private final String username;
    private final String password;

    /**
     * <pre>
     *     단일트랜잭션의 접속정보와 동일하나 접속 시 필요한 드라이버는 {@link XADataSource}의 구현체여야 한다.
     * </pre>
     */
    public JtaSingleBDatasource(@Value("${spring.datasource.jta.singleB.driver-class-name}") String driverClassName
            , @Value("${spring.datasource.jta.singleB.jdbc-url}") String jdbcUrl
            , @Value("${spring.datasource.jta.singleB.username}") String username
            , @Value("${spring.datasource.jta.singleB.password}") String password) {
        this.driverClassName = driverClassName;
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
    }

    /**
     * <pre>
     *     Postgresql {@link XADataSource} 구현체가 설정된 atomikos datasource 구현체 반환.
     * </pre>
     */
    @Bean(initMethod = "init", destroyMethod = "close")
    public DataSource jtaSingleBDataSource() {
        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        atomikosDataSourceBean.setXaDataSourceClassName(driverClassName);

        Properties p = new Properties();
        p.setProperty("user", username);
        p.setProperty("password", password);
        p.setProperty("url", jdbcUrl);

        atomikosDataSourceBean.setXaProperties (p);

        return atomikosDataSourceBean;
    }

}