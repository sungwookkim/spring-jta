package com.datasource;

import com.datasource.service.jta.JtaServiceImpl;
import com.datasource.service.single.SingleServiceImpl;
import org.junit.jupiter.api.*;
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
	@DisplayName("단일 트랜잭션 정상 저장 테스트")
	void single_datasource_insert_test() {
		String saveAndFindValue = "single_test_value";

		long singleASeq = this.singleServiceImpl.singleASaveTest(saveAndFindValue);
		long singleBSeq = this.singleServiceImpl.singleBSaveTest(saveAndFindValue);

		logger.info("singleASeq : {}, singleBSeq : {}", singleASeq, singleBSeq);

		Assertions.assertTrue(singleASeq > 0 && singleBSeq > 0);
	}

	@Test
	@DisplayName("단일 트랜잭션 정상 롤백 테스트")
	void single_datasource_rollback_test() {
		String saveAndFindValue = "single_rollback_test_value";

		Assertions.assertThrowsExactly(RuntimeException.class, () -> {
			this.singleServiceImpl.singleASaveRollbackTest(saveAndFindValue);
		});

		Assertions.assertThrowsExactly(RuntimeException.class, () -> {
			this.singleServiceImpl.singleBSaveRollbackTest(saveAndFindValue);
		});

		Assertions.assertTrue(null == this.singleServiceImpl.singleAFindTestText(saveAndFindValue)
				&& null == this.singleServiceImpl.singleBFindTestText(saveAndFindValue));
	}

	@Test
	@DisplayName("JTA 트랜잭션 정상 저장 테스트")
	void jta_datasource_insert_test() {
		String saveAndFindValue = "jta_test_value";

		logger.info("jta insert count : {}", this.jtaServiceImpl.saveTest(saveAndFindValue));

		// JTA 트랜잭션을 통해 정상적으로 저장되었는지 확인하기 위해 JTA 트랜잭션이 아닌 일반 트랜잭션으로 조회.
		Assertions.assertTrue(saveAndFindValue.equals(this.singleServiceImpl.singleAFindTestText(saveAndFindValue))
				&& saveAndFindValue.equals(this.singleServiceImpl.singleBFindTestText(saveAndFindValue)));
	}

	@Test
	@DisplayName("JTA 트랜잭션 정상 롤백 테스트")
	void jta_rollback_test() {
		String saveAndFindValue = "jta_rollback_test_value";

		Assertions.assertThrowsExactly(RuntimeException.class, () -> {
			this.jtaServiceImpl.saveRollbackTest(saveAndFindValue);
		});

		Assertions.assertTrue(null == this.singleServiceImpl.singleAFindTestText(saveAndFindValue)
				&& null == this.singleServiceImpl.singleBFindTestText(saveAndFindValue));
	}
}