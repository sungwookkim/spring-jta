package com.datasource.service.jta;

import com.datasource.domain.singleA.SingleAJpa;
import com.datasource.domain.singleB.SingleBJpa;
import com.datasource.repo.jpa.jta.singleA.JtaSingleARepository;
import com.datasource.repo.jpa.jta.singleB.JtaSingleBRepository;
import com.datasource.spring.config.annotaion.JtaTransactional;
import org.springframework.stereotype.Service;

@Service
public class JpaJtaServiceImpl {
    private final JtaSingleARepository jtaSingleARepository;
    private final JtaSingleBRepository jtaSingleBRepository;

    public JpaJtaServiceImpl(JtaSingleARepository jtaSingleARepository, JtaSingleBRepository jtaSingleBRepository) {
        this.jtaSingleARepository = jtaSingleARepository;
        this.jtaSingleBRepository = jtaSingleBRepository;
    }

    @JtaTransactional(rollbackFor = {Exception.class})
    public void saveTest(SingleAJpa singleAJpa, SingleBJpa singleBJpa) {
        this.jtaSingleARepository.save(singleAJpa);
        this.jtaSingleBRepository.save(singleBJpa);
    }

    @JtaTransactional(rollbackFor = {Exception.class})
    public void saveRollbackTest(SingleAJpa singleAJpa, SingleBJpa singleBJpa) {
        this.jtaSingleARepository.save(singleAJpa);
        this.jtaSingleBRepository.save(singleBJpa);

        if(true) {
            throw new RuntimeException("Rollback Test Exception");
        }
    }
}
