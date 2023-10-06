package com.datasource.spring.config.db.postgresql.mybatis.singleB;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * <pre>
 *     postgresql Single-B DB 접속 클래스
 * </pre>
 */
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = {"com.datasource.repo.mybatis.single.singleB"}, sqlSessionFactoryRef = "postgresqlSingleBSessionFactory")
public class PostgresqlSingleBConfig {

	/**
	 * <pre>
	 *     db와 mybatis를 연결하기 위한 SqlSessionFactory 객체 생성 메서드
	 * </pre>
	 *
	 * @return
	 * @throws Exception
	 */
	@Bean
	public SqlSessionFactory postgresqlSingleBSessionFactory(DataSource postgresqlSingleBDataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(postgresqlSingleBDataSource);
		sqlSessionFactoryBean.setTypeAliasesPackage("com.datasource.repo.mybatis.single.singleB");

		sqlSessionFactoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);

		return sqlSessionFactoryBean.getObject();
	}

	/**
	 * <pre>
	 * SqlSessionTemplate 객체를 반환하는 메서드
	 * </pre>
	 *
	 * @param postgresqlSingleBSessionFactory
	 * @return
	 * @throws Exception
	 */
	@Bean
	public SqlSessionTemplate postgresSingleBSessionTemplate(SqlSessionFactory postgresqlSingleBSessionFactory) throws Exception {
		return new SqlSessionTemplate(postgresqlSingleBSessionFactory);
	}

	/**
	 * <pre>
	 *     트랜잭션 객체 반환
	 * </pre>
	 *
	 * @return DataSourceTransactionManager 객체 반환
	 */
	@Bean
	public PlatformTransactionManager singleBTransactionManager(DataSource postgresqlSingleBDataSource) {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
		transactionManager.setDataSource(postgresqlSingleBDataSource);

		return transactionManager;
	}
}
