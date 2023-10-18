package com.datasource.spring.config.db.postgresql.mybatis.singleA;

import com.datasource.spring.config.db.postgresql.datasource.JtaSingleBDatasource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

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
