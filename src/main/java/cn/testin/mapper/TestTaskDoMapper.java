package cn.testin.mapper;

import cn.testin.pojo.domain.TestTaskDo;
import cn.testin.pojo.domain.TestTaskDoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TestTaskDoMapper {
    long countByExample(TestTaskDoExample example);

    int deleteByExample(TestTaskDoExample example);

    int deleteByPrimaryKey(String id);

    int insert(TestTaskDo record);

    int insertSelective(TestTaskDo record);

    List<TestTaskDo> selectByExample(TestTaskDoExample example);

    TestTaskDo selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TestTaskDo record, @Param("example") TestTaskDoExample example);

    int updateByExample(@Param("record") TestTaskDo record, @Param("example") TestTaskDoExample example);

    int updateByPrimaryKeySelective(TestTaskDo record);

    int updateByPrimaryKey(TestTaskDo record);
}