package cn.testin.mapper;

import cn.testin.pojo.domain.TestUserDo;
import cn.testin.pojo.domain.TestUserDoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TestUserDoMapper {
    long countByExample(TestUserDoExample example);

    int deleteByExample(TestUserDoExample example);

    int deleteByPrimaryKey(String id);

    int insert(TestUserDo record);

    int insertSelective(TestUserDo record);

    List<TestUserDo> selectByExample(TestUserDoExample example);

    TestUserDo selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TestUserDo record, @Param("example") TestUserDoExample example);

    int updateByExample(@Param("record") TestUserDo record, @Param("example") TestUserDoExample example);

    int updateByPrimaryKeySelective(TestUserDo record);

    int updateByPrimaryKey(TestUserDo record);
}