package com.datasource.repo.jpa.jta.singleA;

import com.datasource.repo.jpa.single.singleA.SingleARepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

/**
 * <pre>
 *     JTA SingleARepository
 *
 *     기존 사용 중인 {@link EnableJpaRepositories}의 basePackages 영역과 충돌 방지를 위해
 *     JTA용 {@link EnableJpaRepositories} basePackages 영역을 가진다.
 *
 *     기존 Repository 기능들을 그대로 사용 하기 위해 {@link SingleARepository}를 상속 받는다.
 * </pre>
 */
@Repository
public interface JtaSingleARepository extends SingleARepository {
}
