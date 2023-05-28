package cn.testin.mapper;

import cn.testin.pojo.domain.TestCaseStepDo;
import cn.testin.pojo.domain.TestCaseStepDoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TestCaseStepDoMapper {
    long countByExample(TestCaseStepDoExample example);

    int deleteByExample(TestCaseStepDoExample example);

    int deleteByPrimaryKey(String id);

    int insert(TestCaseStepDo record);

    int insertSelective(TestCaseStepDo record);

    List<TestCaseStepDo> selectByExampleWithBLOBs(TestCaseStepDoExample example);

    List<TestCaseStepDo> selectByExample(TestCaseStepDoExample example);

    TestCaseStepDo selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TestCaseStepDo record, @Param("example") TestCaseStepDoExample example);

    int updateByExampleWithBLOBs(@Param("record") TestCaseStepDo record, @Param("example") TestCaseStepDoExample example);

    int updateByExample(@Param("record") TestCaseStepDo record, @Param("example") TestCaseStepDoExample example);

    int updateByPrimaryKeySelective(TestCaseStepDo record);

    int updateByPrimaryKeyWithBLOBs(TestCaseStepDo record);

    int updateByPrimaryKey(TestCaseStepDo record);
}