package com.datasource.spring.config.db.postgresql.jpa.singleB;

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
 *     postgresql Single-A DB 접속 클래스
 * </pre>
 *
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.datasource.repo.jpa.single.singleB"
		, entityManagerFactoryRef = "singleBEntityManagerFactory"
		, transactionManagerRef = "singleBJpaTransactionManager")
public class PostgresqlSingleBJtaJpaConfig {

	@Bean
	public LocalContainerEntityManagerFactoryBean singleBEntityManagerFactory(DataSource postgresqlSingleBDataSource) {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(postgresqlSingleBDataSource);
		em.setPackagesToScan("com.datasource.domain.singleB");

		Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL95Dialect");
		properties.setProperty("hibernate.show_sql", "true");
		properties.setProperty("hibernate.format_sql", "true");

		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(properties);

		return em;
	}

	/**
	 * <pre>
	 *     트랜잭션 객체 반환
	 * </pre>
	 *
	 * @return PlatformTransactionManager JpaTransactionManager 객체 반환
	 */
	@Bean
	public PlatformTransactionManager singleBJpaTransactionManager(@Qualifier("singleBEntityManagerFactory") LocalContainerEntityManagerFactoryBean singleBEntityManagerFactory) {
		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
		jpaTransactionManager.setEntityManagerFactory(singleBEntityManagerFactory.getObject());

		return jpaTransactionManager;
	}
}