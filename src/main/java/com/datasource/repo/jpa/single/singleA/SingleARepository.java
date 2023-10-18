package com.datasource.repo.jpa.single.singleA;

import com.datasource.domain.singleA.SingleAJpa;
import com.datasource.repo.jpa.jta.singleA.JtaSingleARepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <pre>
 *     기존 SingleARepository
 *
 *     해당 인터페이스는 JTA 활용을 위해 {@link JtaSingleARepository}의 부모인터페이스가 된다.
 * </pre>
 */
@Repository
public interface SingleARepository extends JpaRepository<SingleAJpa, Long> {
}
