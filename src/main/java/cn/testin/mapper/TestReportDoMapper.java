package cn.testin.mapper;

import cn.testin.pojo.domain.TestReportDo;
import cn.testin.pojo.domain.TestReportDoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TestReportDoMapper {
    long countByExample(TestReportDoExample example);

    int deleteByExample(TestReportDoExample example);

    int deleteByPrimaryKey(String id);

    int insert(TestReportDo record);

    int insertSelective(TestReportDo record);

    List<TestReportDo> selectByExample(TestReportDoExample example);

    TestReportDo selectByPrimaryKey(String id);


    int updateByExampleSelective(@Param("record") TestReportDo record, @Param("example") TestReportDoExample example);

    int updateByExample(@Param("record") TestReportDo record, @Param("example") TestReportDoExample example);

    int updateByPrimaryKeySelective(TestReportDo record);

    int updateByPrimaryKey(TestReportDo record);
}