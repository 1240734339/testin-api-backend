package cn.testin.mapper;

import cn.testin.pojo.domain.TestTaskCaseDo;
import cn.testin.pojo.domain.TestTaskCaseDoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TestTaskCaseDoMapper {
    long countByExample(TestTaskCaseDoExample example);

    int deleteByExample(TestTaskCaseDoExample example);

    int insert(TestTaskCaseDo record);

    int insertSelective(TestTaskCaseDo record);

    List<TestTaskCaseDo> selectByExample(TestTaskCaseDoExample example);

    int updateByExampleSelective(@Param("record") TestTaskCaseDo record, @Param("example") TestTaskCaseDoExample example);

    int updateByExample(@Param("record") TestTaskCaseDo record, @Param("example") TestTaskCaseDoExample example);
}