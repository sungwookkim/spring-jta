package com.datasource.service.jta;

import com.datasource.domain.singleA.SingleAJpa;
import com.datasource.domain.singleB.SingleBJpa;
import com.datasource.repo.jpa.jta.singleA.JtaSingleARepository;
import com.datasource.repo.jpa.jta.singleB.JtaSingleBRepository;
import com.datasource.spring.config.annotaion.JtaTransactional;
import org.springframework.stereotype.Service;

/**
 * <pre>
 *     JPA JTA 트랜잭션 확인을 위한 테스트 클래스.
 * </pre>
 */
@Service
public class JpaJtaServiceImpl {
    private final JtaSingleARepository jtaSingleARepository;
    private final JtaSingleBRepository jtaSingleBRepository;

    public JpaJtaServiceImpl(JtaSingleARepository jtaSingleARepository, JtaSingleBRepository jtaSingleBRepository) {
        this.jtaSingleARepository = jtaSingleARepository;
        this.jtaSingleBRepository = jtaSingleBRepository;
    }

    /**
     * <pre>
     *     이기종 간(SingleA, SingelB)의 정상 트랜잭션을 확인 하기 위한 메서드.
     * </pre>
     */
    @JtaTransactional(rollbackFor = {Exception.class})
    public void saveTest(SingleAJpa singleAJpa, SingleBJpa singleBJpa) {
        this.jtaSingleARepository.save(singleAJpa);
        this.jtaSingleBRepository.save(singleBJpa);
    }

    /**
     * <pre>
     *     이기종 간(SingleA, SingleB)의 트랜잭션 롤백을 확인 하기 위한 메서드.
     * </pre>
     */
    @JtaTransactional(rollbackFor = {Exception.class})
    public void saveRollbackTest(SingleAJpa singleAJpa, SingleBJpa singleBJpa) {
        this.jtaSingleARepository.save(singleAJpa);
        this.jtaSingleBRepository.save(singleBJpa);

        if(true) {
            throw new RuntimeException("Rollback Test Exception");
        }
    }
}