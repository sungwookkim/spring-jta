package com.datasource.repo.mybatis.jta.singleB;

import com.datasource.repo.mybatis.single.singleA.SingleAMapper;
import com.datasource.repo.mybatis.single.singleB.SingleBMapper;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

/**
 * <pre>
 *     JTA SingleBMapper
 *
 *     기존 사용 중인 {@link MapperScan}의 basePackages 영역과 충돌 방지를 위해
 *     JTA용 {@link MapperScan} basePackages 영역을 가진다.
 *
 *     기존 Mapper 기능들을 그대로 사용 하기 위해 {@link SingleBMapper}를 상속 받는다.
 * </pre>
 */
@Mapper
public interface JtaSingleBMapper extends SingleBMapper {
}
