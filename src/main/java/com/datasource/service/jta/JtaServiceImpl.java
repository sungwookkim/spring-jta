package com.datasource.service.jta;

import com.datasource.repo.mybatis.jta.singleA.JtaSingleAMapper;
import com.datasource.repo.mybatis.jta.singleB.JtaSingleBMapper;
import com.datasource.spring.config.annotaion.JtaTransactional;
import org.springframework.stereotype.Service;

@Service
public class JtaServiceImpl {
    private final JtaSingleAMapper jtaSingleAMapper;
    private final JtaSingleBMapper jtaSingleBMapper;

    public JtaServiceImpl(JtaSingleAMapper jtaSingleAMapper, JtaSingleBMapper jtaSingleBMapper) {
        this.jtaSingleAMapper = jtaSingleAMapper;
        this.jtaSingleBMapper = jtaSingleBMapper;
    }

    @JtaTransactional(rollbackFor = {Exception.class})
    public long saveTest(String testText) {
        return this.jtaSingleAMapper.saveTest(testText) + this.jtaSingleBMapper.saveTest(testText);
    }

    @JtaTransactional(rollbackFor = {Exception.class})
    public void saveRollbackTest(String testText) {
        this.jtaSingleAMapper.saveTest(testText);
        this.jtaSingleBMapper.saveTest(testText);

        if(true) {
            throw new RuntimeException("Rollback Test Exception");
        }
    }
}
