package com.datasource.spring.config.db.postgresql.jpa.singleB;

import com.datasource.spring.config.db.postgresql.datasource.SingleBDatasource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * <pre>
 *     JTA가 아닌 단일트랜잭션 활용을 위한 JPA 설정
 *
 *     주의점
 *     - {@link EnableJpaRepositories}의 basePackages 속성이 JTA를 활용하는 basePackages와 동일하면 안된다.
 * </pre>
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.datasource.repo.jpa.single.singleB"
		, entityManagerFactoryRef = "singleBEntityManagerFactory"
		, transactionManagerRef = "singleBJpaTransactionManager")
public class PostgresqlSingleBJtaJpaConfig {

	/**
	 * <pre>
	 *     {@link LocalContainerEntityManagerFactoryBean} 구현체에서 사용되는 datasource는 {@link SingleBDatasource#postgresqlSingleBDataSource()}를 사용.
	 * </pre>
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean singleBEntityManagerFactory(DataSource postgresqlSingleBDataSource) {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(postgresqlSingleBDataSource);
		em.setPackagesToScan("com.datasource.domain.singleB");

		Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		properties.setProperty("hibernate.show_sql", "true");
		properties.setProperty("hibernate.format_sql", "true");

		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(properties);

		return em;
	}

	/**
	 * <pre>
	 *     Single-B를 활용한 JPA 단일트랜잭션 반환.
	 * </pre>
	 */
	@Bean
	public PlatformTransactionManager singleBJpaTransactionManager(@Qualifier("singleBEntityManagerFactory") LocalContainerEntityManagerFactoryBean singleBEntityManagerFactory) {
		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
		jpaTransactionManager.setEntityManagerFactory(singleBEntityManagerFactory.getObject());

		return jpaTransactionManager;
	}
}
