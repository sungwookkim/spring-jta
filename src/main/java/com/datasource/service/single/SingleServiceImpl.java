package com.datasource.service.single;

import com.datasource.repo.mybatis.single.singleA.SingleAMapper;
import com.datasource.repo.mybatis.single.singleB.SingleBMapper;
import com.datasource.spring.config.annotaion.SingleATransactional;
import com.datasource.spring.config.annotaion.SingleBTransactional;
import org.springframework.stereotype.Service;

/**
 * <pre>
 *     Mybatis를 활용한 JTA 트랜잭션이 아닌 단일 트랜잭션을 확인하기 위핸 테스트 클래스.
 * </pre>
 */
@Service
public class SingleServiceImpl {
    private final SingleAMapper singleAMapper;
    private final SingleBMapper singleBMapper;

    public SingleServiceImpl(SingleAMapper singleAMapper, SingleBMapper singleBMapper) {
        this.singleAMapper = singleAMapper;
        this.singleBMapper = singleBMapper;
    }

    /**********************/
    /*Single A 트랜잭션 영역*/
    /**********************/
    /**
     * <pre>
     *     SingleA 단일 트랜잭션 정상 저장을 확인하기 위한 메서드.
     * </pre>
     */
    @SingleATransactional(rollbackFor = {Exception.class})
    public long singleASaveTest(String testText) {
        return this.singleAMapper.saveTest(testText);
    }

    /**
     * <pre>
     *     SingleA 단일 트랜잭션 정상 조회를 확인하기 위한 메서드.
     * </pre>
     */
    @SingleATransactional
    public String singleAFindTestText(String testText) {
        return this.singleAMapper.findTestText(testText);
    }

    /**********************/
    /*Single B 트랜잭션 영역*/
    /**********************/
    /**
     * <pre>
     *     SingleB 단일 트랜잭션 정상 저장을 확인하기 위한 메서드.
     * </pre>
     */
    @SingleBTransactional(rollbackFor = {Exception.class})
    public long singleBSaveTest(String testText) {
        return this.singleBMapper.saveTest(testText);
    }

    /**
     * <pre>
     *     SingleB 단일 트랜잭션 정상 조회를 확인하기 위한 메서드.
     * </pre>
     */
    @SingleBTransactional
    public String singleBFindTestText(String testText) {
        return this.singleBMapper.findTestText(testText);
    }
}