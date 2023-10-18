package com.datasource.repo.mybatis.single.singleA;

import com.datasource.repo.mybatis.jta.singleA.JtaSingleAMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * <pre>
 *     기존 SingleAMapper
 *
 *     해당 인터페이스는 JTA 활용을 위해 {@link JtaSingleAMapper}의 부모인터페이스가 된다.
 * </pre>
 */
@Mapper
public interface SingleAMapper {
    @Insert("""
            insert into test(
                test_text
            ) values (
                #{testText}
            )
    """)
    long saveTest(String testText);

    @Select("""
            select 
                test_text 
            from test 
            where test_text = #{testText}
    """)
    String findTestText(String testText);
}
