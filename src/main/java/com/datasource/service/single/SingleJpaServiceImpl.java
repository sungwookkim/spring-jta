package com.datasource.service.single;

import com.datasource.domain.singleA.SingleAJpa;
import com.datasource.domain.singleB.SingleBJpa;
import com.datasource.repo.jpa.single.singleA.SingleARepository;
import com.datasource.repo.jpa.single.singleB.SingleBRepository;
import com.datasource.spring.config.annotaion.SingleAJpaTransactional;
import com.datasource.spring.config.annotaion.SingleBJpaTransactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SingleJpaServiceImpl {
    private final SingleARepository singleARepository;
    private final SingleBRepository singleBRepository;

    public SingleJpaServiceImpl(SingleARepository singleARepository, SingleBRepository singleBRepository) {
        this.singleARepository = singleARepository;
        this.singleBRepository = singleBRepository;
    }

    /*
    Single A 트랜잭션 영역
    */
    @SingleAJpaTransactional(rollbackFor = {Exception.class})
    public void singleASaveTest(SingleAJpa singleAJpa) {
        this.singleARepository.save(singleAJpa);
    }

    @SingleAJpaTransactional
    public Optional<SingleAJpa> singleAFindTestText(Long seq) {
        return this.singleARepository.findById(seq);
    }

    /*
    Single B 트랜잭션 영역
    */
    @SingleBJpaTransactional(rollbackFor = {Exception.class})
    public void singleBSaveTest(SingleBJpa singleBJpa) {
        this.singleBRepository.save(singleBJpa);
    }

    @SingleBJpaTransactional
    public Optional<SingleBJpa> singleBFindTestText(Long seq) {
        return this.singleBRepository.findById(seq);
    }
}
