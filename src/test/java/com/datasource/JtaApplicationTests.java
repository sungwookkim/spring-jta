package com.datasource;

import com.datasource.service.jta.JtaServiceImpl;
import com.datasource.service.single.SingleServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class JtaApplicationTests {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	SingleServiceImpl singleServiceImpl;

	@Autowired
	JtaServiceImpl jtaServiceImpl;

	@Autowired
	DataSource postgresqlSingleADataSource;

	@Autowired
	DataSource postgresqlSingleBDataSource;

	@BeforeEach
	public void init() {
		List<String> executes = Arrays.asList("DropSchema.sql"
				, "CreateSchema.sql");

		for(String execute : executes) {
			Resource initSchema = new ClassPathResource(execute);
			DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initSchema);

			DatabasePopulatorUtils.execute(databasePopulator, postgresqlSingleADataSource);
			DatabasePopulatorUtils.execute(databasePopulator, postgresqlSingleBDataSource);
		}
	}

	@AfterEach
	public void destory() {
		List<String> executes = Arrays.asList("DropSchema.sql");

		for(String execute : executes) {
			Resource initSchema = new ClassPathResource(execute);
			DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initSchema);

			DatabasePopulatorUtils.execute(databasePopulator, postgresqlSingleADataSource);
			DatabasePopulatorUtils.execute(databasePopulator, postgresqlSingleBDataSource);
		}
	}

	@Test
	void single_datasource_insert_test() {
		Assertions.assertEquals(1, this.singleServiceImpl.singleASaveTest("test_A"));
		Assertions.assertEquals(1, this.singleServiceImpl.singleBSaveTest("test_B"));
	}

	@Test
	void jta_datasource_insert_test() {
		String saveAndFindValue = "test_jta";

		logger.info("jta insert count : {}", this.jtaServiceImpl.saveTest(saveAndFindValue));

		Assertions.assertEquals(saveAndFindValue
				, this.singleServiceImpl.singleAFindTestText(saveAndFindValue));

		Assertions.assertEquals(saveAndFindValue
				, this.singleServiceImpl.singleBFindTestText(saveAndFindValue));
	}

	@Test
	void jta_rollback_test() {
		String saveAndFindValue = "test_A";
		try {
			this.jtaServiceImpl.saveRollbackTest(saveAndFindValue);
		} catch (Exception e) {

		}

		Assertions.assertEquals(null, this.singleServiceImpl.singleAFindTestText(saveAndFindValue));
		Assertions.assertEquals(null, this.singleServiceImpl.singleBFindTestText(saveAndFindValue));
	}
}
