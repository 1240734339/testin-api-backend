package cn.testin.mapper;

import cn.testin.pojo.domain.TestEnvironmentDo;
import cn.testin.pojo.domain.TestEnvironmentDoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TestEnvironmentDoMapper {
    long countByExample(TestEnvironmentDoExample example);

    int deleteByExample(TestEnvironmentDoExample example);

    int deleteByPrimaryKey(String id);

    int insert(TestEnvironmentDo record);

    int insertSelective(TestEnvironmentDo record);

    List<TestEnvironmentDo> selectByExampleWithBLOBs(TestEnvironmentDoExample example);

    List<TestEnvironmentDo> selectByExample(TestEnvironmentDoExample example);

    TestEnvironmentDo selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TestEnvironmentDo record, @Param("example") TestEnvironmentDoExample example);

    int updateByExampleWithBLOBs(@Param("record") TestEnvironmentDo record, @Param("example") TestEnvironmentDoExample example);

    int updateByExample(@Param("record") TestEnvironmentDo record, @Param("example") TestEnvironmentDoExample example);

    int updateByPrimaryKeySelective(TestEnvironmentDo record);

    int updateByPrimaryKeyWithBLOBs(TestEnvironmentDo record);

    int updateByPrimaryKey(TestEnvironmentDo record);
}