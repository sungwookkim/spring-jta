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

		Assertions.assertTrue(0 < singleAJpa.getSeq() && 0 < singleBJpa.getSeq());
	}

	@Test
	void jta_jpa_datasource_insert_test() {
		String saveAndFindValue = "test_jta";
		SingleAJpa singleAJpa = new SingleAJpa(saveAndFindValue);
		SingleBJpa singleBJpa  = new SingleBJpa(saveAndFindValue);

		this.jpaJtaServiceImpl.saveTest(singleAJpa, singleBJpa);

		Assertions.assertEquals(saveAndFindValue
				, this.singleJpaServiceImpl.singleAFindTestText(singleAJpa.getSeq()).get().getTestText());

		Assertions.assertEquals(saveAndFindValue
				, this.singleJpaServiceImpl.singleBFindTestText(singleBJpa.getSeq()).get().getTestText());
	}

	@Test
	void jta_jpa_rollback_test() {
		Assertions.assertThrowsExactly(RuntimeException.class, () -> {
			String saveAndFindValue = "test_jta";
			SingleAJpa singleAJpa = new SingleAJpa(saveAndFindValue);
			SingleBJpa singleBJpa  = new SingleBJpa(saveAndFindValue);

			this.jpaJtaServiceImpl.saveRollbackTest(singleAJpa, singleBJpa);
		});
	}
}
