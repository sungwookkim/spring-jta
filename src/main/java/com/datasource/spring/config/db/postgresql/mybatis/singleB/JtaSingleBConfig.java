package com.datasource.spring.config.db.postgresql.mybatis.singleB;

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
@MapperScan(basePackages = {"com.datasource.repo.mybatis.jta.singleB"}, sqlSessionFactoryRef = "jtaSingleBSqlSessionFactory")
public class JtaSingleBConfig {
    private final String driverClassName;
    private final String jdbcUrl;
    private final String username;
    private final String password;

    public JtaSingleBConfig(@Value("${spring.datasource.jta.singleB.driver-class-name}") String driverClassName
            , @Value("${spring.datasource.jta.singleB.jdbc-url}") String jdbcUrl
            , @Value("${spring.datasource.jta.singleB.username}") String username
            , @Value("${spring.datasource.jta.singleB.password}") String password) {
        this.driverClassName = driverClassName;
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
    }

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

    @Bean
    public SqlSessionFactory jtaSingleBSqlSessionFactory(DataSource jtaSingleBDataSource) throws Exception {
        final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(jtaSingleBDataSource);
        sqlSessionFactoryBean.setTypeAliasesPackage("com.datasource.repo.mybatis.jta.singleB");

        return sqlSessionFactoryBean.getObject();
    }
}
