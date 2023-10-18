package com.datasource.service.single;

import com.datasource.domain.singleA.SingleAJpa;
import com.datasource.domain.singleB.SingleBJpa;
import com.datasource.repo.jpa.single.singleA.SingleARepository;
import com.datasource.repo.jpa.single.singleB.SingleBRepository;
import com.datasource.spring.config.annotaion.SingleAJpaTransactional;
import com.datasource.spring.config.annotaion.SingleBJpaTransactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * <pre>
 *     JPA를 활용한 JTA 트랜잭션이 아닌 단일 트랜잭션을 확인하기 위핸 테스트 클래스.
 * </pre>
 */
@Service
public class SingleJpaServiceImpl {
    private final SingleARepository singleARepository;
    private final SingleBRepository singleBRepository;

    public SingleJpaServiceImpl(SingleARepository singleARepository, SingleBRepository singleBRepository) {
        this.singleARepository = singleARepository;
        this.singleBRepository = singleBRepository;
    }

    /**********************/
    /*Single A 트랜잭션 영역*/
    /**********************/
    /**
     * <pre>
     *     SingleA 단일 트랜잭션 정상 저장을 확인하기 위한 메서드.
     * </pre>
     */
    @SingleAJpaTransactional(rollbackFor = {Exception.class})
    public void singleASaveTest(SingleAJpa singleAJpa) {
        this.singleARepository.save(singleAJpa);
    }

    /**
     * <pre>
     *     SingleA 단일 트랜잭션 정상 조회를 확인하기 위한 메서드.
     * </pre>
     */
    @SingleAJpaTransactional
    public Optional<SingleAJpa> singleAFindTestText(Long seq) {
        return this.singleARepository.findById(seq);
    }

    /**********************/
    /*Single B 트랜잭션 영역*/
    /**********************/
    /**
     * <pre>
     *     SingleB 단일 트랜잭션 정상 저장을 확인하기 위한 메서드.
     * </pre>
     */
    @SingleBJpaTransactional(rollbackFor = {Exception.class})
    public void singleBSaveTest(SingleBJpa singleBJpa) {
        this.singleBRepository.save(singleBJpa);
    }

    /**
     * <pre>
     *     SingleB 단일 트랜잭션 정상 조회를 확인하기 위한 메서드.
     * </pre>
     */
    @SingleBJpaTransactional
    public Optional<SingleBJpa> singleBFindTestText(Long seq) {
        return this.singleBRepository.findById(seq);
    }
}