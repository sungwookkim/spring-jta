package com.datasource.spring.config.db.postgresql.jpa.singleA;

import com.datasource.spring.config.db.postgresql.datasource.JtaSingleADatasource;
import org.hibernate.engine.transaction.jta.platform.internal.AtomikosJtaPlatform;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * <pre>
 *     JTA 활용을 위한 JPA 설정
 *
 *     주의점
 *     - {@link EnableJpaRepositories}의 basePackages 속성이 단일트랜잭션을 활용하는 basePackages와 동일하면 안된다.
 *     - {@link LocalContainerEntityManagerFactoryBean#setPackagesToScan(String...)}의 패키지는 동일해야 한다.
 * </pre>
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.datasource.repo.jpa.jta.singleA"
		, entityManagerFactoryRef = "singleAJtaEntityManagerFactory"
		, transactionManagerRef = "jtaSingleTransactionManager")
public class JtaJpaSingleAConfig {

	/**
	 * <pre>
	 *     {@link LocalContainerEntityManagerFactoryBean} 구현체에서 사용되는 datasource는 {@link JtaSingleADatasource#jtaSingleADataSource()}를 사용.
	 * </pre>
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean singleAJtaEntityManagerFactory(DataSource jtaSingleADataSource) {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(jtaSingleADataSource);
		em.setPackagesToScan("com.datasource.domain.singleA");

		Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL95Dialect");

		// JPA에서 JTA 활용을 위한 설정.
		properties.setProperty("hibernate.transaction.jta.platform", AtomikosJtaPlatform.class.getName());
		properties.setProperty("javax.persistence.transactionType", "JTA");

		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(properties);

		return em;
	}
}
