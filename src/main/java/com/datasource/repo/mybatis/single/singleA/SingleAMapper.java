package com.datasource.repo.mybatis.single.singleA;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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