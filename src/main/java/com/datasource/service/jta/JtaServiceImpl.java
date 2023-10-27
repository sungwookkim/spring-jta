package com.datasource.service.jta;

import com.datasource.repo.mybatis.jta.singleA.JtaSingleAMapper;
import com.datasource.repo.mybatis.jta.singleB.JtaSingleBMapper;
import com.datasource.spring.config.annotaion.JtaTransactional;
import org.springframework.stereotype.Service;

/**
 * <pre>
 *     Mybatis JTA 트랜잭션 확인을 위한 테스트 클래스.
 * </pre>
 */
@Service
public class JtaServiceImpl {
    private final JtaSingleAMapper jtaSingleAMapper;
    private final JtaSingleBMapper jtaSingleBMapper;

    public JtaServiceImpl(JtaSingleAMapper jtaSingleAMapper, JtaSingleBMapper jtaSingleBMapper) {
        this.jtaSingleAMapper = jtaSingleAMapper;
        this.jtaSingleBMapper = jtaSingleBMapper;
    }

    /**
     * <pre>
     *     이기종 간(SingleA, SingelB)의 정상 트랜잭션을 확인 하기 위한 메서드.
     * </pre>
     */
    @JtaTransactional(rollbackFor = {Exception.class})
    public long saveTest(String testText) {
        return this.jtaSingleAMapper.saveTest(testText) + this.jtaSingleBMapper.saveTest(testText);
    }

    /**
     * <pre>
     *     이기종 간(SingleA, SingleB)의 트랜잭션 롤백을 확인 하기 위한 메서드.
     * </pre>
     */
    @JtaTransactional(rollbackFor = {Exception.class})
    public void saveRollbackTest(String testText) {
        this.jtaSingleAMapper.saveTest(testText);
        this.jtaSingleBMapper.saveTest(testText);

        if(true) {
            throw new RuntimeException("Rollback Test Exception");
        }
    }
}
