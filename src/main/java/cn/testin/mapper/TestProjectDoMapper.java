package cn.testin.mapper;

import cn.testin.pojo.domain.TestProjectDo;
import cn.testin.pojo.domain.TestProjectDoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TestProjectDoMapper {
    long countByExample(TestProjectDoExample example);

    int deleteByExample(TestProjectDoExample example);

    int deleteByPrimaryKey(String id);

    int insert(TestProjectDo record);

    int insertSelective(TestProjectDo record);

    List<TestProjectDo> selectByExample(TestProjectDoExample example);

    TestProjectDo selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TestProjectDo record, @Param("example") TestProjectDoExample example);

    int updateByExample(@Param("record") TestProjectDo record, @Param("example") TestProjectDoExample example);

    int updateByPrimaryKeySelective(TestProjectDo record);

    int updateByPrimaryKey(TestProjectDo record);
}