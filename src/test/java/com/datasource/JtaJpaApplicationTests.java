package com.datasource;

import com.datasource.domain.singleA.SingleAJpa;
import com.datasource.domain.singleB.SingleBJpa;
import com.datasource.service.jta.JpaJtaServiceImpl;
import com.datasource.service.single.SingleJpaServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JtaJpaApplicationTests {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	SingleJpaServiceImpl singleJpaServiceImpl;

	@Autowired
	JpaJtaServiceImpl jpaJtaServiceImpl;

	@Test
	void single_jpa_datasource_insert_test() {
		String saveAndFindValue = "test_jta";
		SingleAJpa singleAJpa = new SingleAJpa(saveAndFindValue);
		SingleBJpa singleBJpa  = new SingleBJpa(saveAndFindValue);

		this.singleJpaServiceImpl.singleASaveTest(singleAJpa);
		this.singleJpaServiceImpl.singleBSaveTest(singleBJpa);

		long singleASeq = singleAJpa.getSeq();
		long singleBSeq = singleBJpa.getSeq();

		logger.info("singleASeq : {}, singleBSeq : {}", singleASeq, singleBSeq);

		Assertions.assertTrue(singleASeq > 0 && singleBSeq > 0);
	}

	@Test
	void jta_jpa_datasource_insert_test() {
		String saveAndFindValue = "test_jta";
		SingleAJpa singleAJpa = new SingleAJpa(saveAndFindValue);
		SingleBJpa singleBJpa  = new SingleBJpa(saveAndFindValue);

		this.jpaJtaServiceImpl.saveTest(singleAJpa, singleBJpa);

		long singleASeq = singleAJpa.getSeq();
		long singleBSeq = singleBJpa.getSeq();

		logger.info("singleASeq : {}, singleBSeq : {}", singleASeq, singleBSeq);

		// JTA 트랜잭션을 통해 정상적으로 저장되었는지 확인하기 위해 JTA 트랜잭션이 아닌 일반 트랜잭션으로 조회.
		Assertions.assertTrue(saveAndFindValue.equals(this.singleJpaServiceImpl.singleAFindTestText(singleASeq).get().getTestText())
				&& saveAndFindValue.equals(this.singleJpaServiceImpl.singleBFindTestText(singleBSeq).get().getTestText()));
	}

	@Test
	void jta_jpa_rollback_test() {
		String saveAndFindValue = "test_jta";

		Assertions.assertThrowsExactly(RuntimeException.class, () -> {
			SingleAJpa singleAJpa = new SingleAJpa(saveAndFindValue);
			SingleBJpa singleBJpa  = new SingleBJpa(saveAndFindValue);

			this.jpaJtaServiceImpl.saveRollbackTest(singleAJpa, singleBJpa);
		});
	}
}
