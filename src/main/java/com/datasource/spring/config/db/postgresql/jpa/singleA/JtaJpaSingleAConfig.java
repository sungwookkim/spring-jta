package com.datasource.spring.config.db.postgresql.jpa.singleA;

import org.hibernate.engine.transaction.jta.platform.internal.AtomikosJtaPlatform;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
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
@EnableJpaRepositories(basePackages = "com.datasource.repo.jpa.jta.singleA"
		, entityManagerFactoryRef = "singleAJtaEntityManagerFactory"
		, transactionManagerRef = "jtaSingleTransactionManager")
public class JtaJpaSingleAConfig {

	@Bean
	public LocalContainerEntityManagerFactoryBean singleAJtaEntityManagerFactory(DataSource postgresqlSingleBDataSource) {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(postgresqlSingleBDataSource);
		em.setPackagesToScan("com.datasource.domain.singleA");

		Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		properties.setProperty("hibernate.transaction.jta.platform", AtomikosJtaPlatform.class.getName());
		properties.setProperty("javax.persistence.transactionType", "JTA");

		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(properties);

		return em;
	}
}
