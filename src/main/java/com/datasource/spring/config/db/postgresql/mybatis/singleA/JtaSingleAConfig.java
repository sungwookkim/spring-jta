package com.datasource.spring.config.db.postgresql.mybatis.singleA;

import com.atomikos.spring.AtomikosDataSourceBean;
import com.datasource.spring.config.db.postgresql.datasource.JtaSingleBDatasource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * <pre>
 *     JTA 활용을 위한 Mybatis 설정
 *
 *     주의점
 *     - {@link MapperScan}의 basePackages 속성외 패키지 경로를 지정해야 하는 경우 단일트랜잭션을 활용하는 basePackages와 동일하면 안된다.
 * </pre>
 */
@Configuration
@MapperScan(basePackages = {"com.datasource.repo.mybatis.jta.singleA"}, sqlSessionFactoryRef = "jtaSingleASqlSessionFactory")
public class JtaSingleAConfig {
    private final String driverClassName;
    private final String jdbcUrl;
    private final String username;
    private final String password;

    public JtaSingleAConfig(@Value("${spring.datasource.jta.singleA.driver-class-name}") String driverClassName
            , @Value("${spring.datasource.jta.singleA.jdbc-url}") String jdbcUrl
            , @Value("${spring.datasource.jta.singleA.username}") String username
            , @Value("${spring.datasource.jta.singleA.password}") String password) {
        this.driverClassName = driverClassName;
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
    }

    /**
     * <pre>
     *     {@link SqlSessionFactory} 구현체에서 사용되는 datasource는  {@link JtaSingleBDatasource#jtaSingleBDataSource()}를 사용.
     * </pre>
     */
    @Bean
    public SqlSessionFactory jtaSingleASqlSessionFactory(DataSource jtaSingleADataSource) throws Exception {
        final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(jtaSingleADataSource);
        sqlSessionFactoryBean.setTypeAliasesPackage("com.datasource.repo.mybatis.jta.singleA");

        return sqlSessionFactoryBean.getObject();
    }
}
