package com.datasource.service.single;

import com.datasource.repo.mybatis.single.singleA.SingleAMapper;
import com.datasource.repo.mybatis.single.singleB.SingleBMapper;
import com.datasource.spring.config.annotaion.SingleATransactional;
import com.datasource.spring.config.annotaion.SingleBTransactional;
import org.springframework.stereotype.Service;

@Service
public class SingleServiceImpl {
    private final SingleAMapper singleAMapper;
    private final SingleBMapper singleBMapper;

    public SingleServiceImpl(SingleAMapper singleAMapper, SingleBMapper singleBMapper) {
        this.singleAMapper = singleAMapper;
        this.singleBMapper = singleBMapper;
    }

    /*
    Single A 트랜잭션 영역
     */
    @SingleATransactional(rollbackFor = {Exception.class})
    public long singleASaveTest(String testText) {
        return this.singleAMapper.saveTest(testText);
    }

    @SingleATransactional
    public String singleAFindTestText(String testText) {
        return this.singleAMapper.findTestText(testText);
    }

    /*
    Single B 트랜잭션 영역
    */
    @SingleBTransactional(rollbackFor = {Exception.class})
    public long singleBSaveTest(String testText) {
        return this.singleBMapper.saveTest(testText);
    }

    @SingleBTransactional
    public String singleBFindTestText(String testText) {
        return this.singleBMapper.findTestText(testText);
    }
}
