package com.datasource.repo.jpa.single.singleB;

import com.datasource.domain.singleB.SingleBJpa;
import com.datasource.repo.jpa.jta.singleB.JtaSingleBRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <pre>
 *     기존 SingleBRepository
 *
 *     해당 인터페이스는 JTA 활용을 위해 {@link JtaSingleBRepository}의 부모인터페이스가 된다.
 * </pre>
 */
@Repository
public interface SingleBRepository extends JpaRepository<SingleBJpa, Long> {
}