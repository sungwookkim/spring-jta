package com.datasource.spring.config.db.postgresql.mybatis.singleA;

import com.atomikos.spring.AtomikosDataSourceBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
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

    @Bean(initMethod = "init", destroyMethod = "close")
    public DataSource jtaSingleADataSource() {
        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        atomikosDataSourceBean.setXaDataSourceClassName(driverClassName);

        Properties p = new Properties();
        p.setProperty("user", username);
        p.setProperty("password", password);
        p.setProperty("url", jdbcUrl);

        atomikosDataSourceBean.setXaProperties (p);

        return atomikosDataSourceBean;
    }

    @Bean
    public SqlSessionFactory jtaSingleASqlSessionFactory(DataSource jtaSingleADataSource) throws Exception {
        final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(jtaSingleADataSource);
        sqlSessionFactoryBean.setTypeAliasesPackage("com.datasource.repo.mybatis.jta.singleA");

        return sqlSessionFactoryBean.getObject();
    }
}
