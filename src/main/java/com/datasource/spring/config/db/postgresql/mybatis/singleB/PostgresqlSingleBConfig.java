package com.datasource.spring.config.db.postgresql.mybatis.singleB;

import com.datasource.spring.config.db.postgresql.datasource.SingleBDatasource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * <pre>
 *     JTA가 아닌 단일트랜잭션 활용을 위한 JPA 설정
 *
 *     주의점
 *     - {@link MapperScan}의 basePackages 속성외 패키지 경로를 지정해야 하는 경우 JTA 트랜잭션을 활용하는 basePackages와 동일하면 안된다.
 * </pre>
 */
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = {"com.datasource.repo.mybatis.single.singleB"}, sqlSessionFactoryRef = "postgresqlSingleBSessionFactory")
public class PostgresqlSingleBConfig {

	/**
	 * <pre>
	 *     {@link SqlSessionFactory} 구현체에서 사용되는 datasource는  {@link SingleBDatasource#postgresqlSingleBDataSource()}를 사용.
	 * </pre>
	 */
	@Bean
	public SqlSessionFactory postgresqlSingleBSessionFactory(DataSource postgresqlSingleBDataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(postgresqlSingleBDataSource);
		sqlSessionFactoryBean.setTypeAliasesPackage("com.datasource.repo.mybatis.single.singleB");

		sqlSessionFactoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);

		return sqlSessionFactoryBean.getObject();
	}

	@Bean
	public SqlSessionTemplate postgresSingleBSessionTemplate(SqlSessionFactory postgresqlSingleBSessionFactory) {
		return new SqlSessionTemplate(postgresqlSingleBSessionFactory);
	}

	/**
	 * <pre>
	 *     Single-B를 활용한 Mubatis 단일트랜잭션 반환.
	 *
	 *     사용되는 datasource는  {@link SingleBDatasource#postgresqlSingleBDataSource()}를 사용.
	 * </pre>
	 */
	@Bean
	public PlatformTransactionManager singleBTransactionManager(DataSource postgresqlSingleBDataSource) {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
		transactionManager.setDataSource(postgresqlSingleBDataSource);

		return transactionManager;
	}
}
