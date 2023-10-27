package com.datasource.spring.config.db.postgresql.mybatis.singleA;

import com.datasource.spring.config.db.postgresql.datasource.SingleADatasource;
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
 *     JTA가 아닌 단일트랜잭션 활용을 위한 Mybatis 설정
 *
 *     주의점
 *     - {@link MapperScan}의 basePackages 속성외 패키지 경로를 지정해야 하는 경우 JTA 트랜잭션을 활용하는 basePackages와 동일하면 안된다.
 * </pre>
 */
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = {"com.datasource.repo.mybatis.single.singleA"}, sqlSessionFactoryRef = "postgresqlSingleASessionFactory")
public class PostgresqlSingleAConfig {

	/**
	 * <pre>
	 *     {@link SqlSessionFactory} 구현체에서 사용되는 datasource는  {@link SingleADatasource#postgresqlSingleADataSource()}를 사용.
	 * </pre>
	 */
	@Bean
	public SqlSessionFactory postgresqlSingleASessionFactory(DataSource postgresqlSingleADataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(postgresqlSingleADataSource);
		sqlSessionFactoryBean.setTypeAliasesPackage("com.datasource.repo.mybatis.single.singleA");

		sqlSessionFactoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);

		return sqlSessionFactoryBean.getObject();
	}

	@Bean
	public SqlSessionTemplate postgresSingleASessionTemplate(SqlSessionFactory postgresqlSingleASessionFactory) {
		return new SqlSessionTemplate(postgresqlSingleASessionFactory);
	}

	/**
	 * <pre>
	 *     Single-A를 활용한 Mubatis 단일트랜잭션 반환.
	 *
	 *     사용되는 datasource는  {@link SingleADatasource#postgresqlSingleADataSource()}를 사용.
	 * </pre>
	 */
	@Bean
	public PlatformTransactionManager singleATransactionManager(DataSource postgresqlSingleADataSource) {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
		transactionManager.setDataSource(postgresqlSingleADataSource);

		return transactionManager;
	}
}
