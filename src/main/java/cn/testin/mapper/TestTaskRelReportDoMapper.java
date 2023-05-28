package cn.testin.mapper;

import cn.testin.pojo.domain.TestTaskRelReportDo;
import cn.testin.pojo.domain.TestTaskRelReportDoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TestTaskRelReportDoMapper {
    long countByExample(TestTaskRelReportDoExample example);

    int deleteByExample(TestTaskRelReportDoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TestTaskRelReportDo record);

    int insertSelective(TestTaskRelReportDo record);

    List<TestTaskRelReportDo> selectByExample(TestTaskRelReportDoExample example);

    TestTaskRelReportDo selectByPrimaryKey(Integer id);

    TestTaskRelReportDo selectByReportId(@Param("reportId") String reportId);

    int updateByExampleSelective(@Param("record") TestTaskRelReportDo record, @Param("example") TestTaskRelReportDoExample example);

    int updateByExample(@Param("record") TestTaskRelReportDo record, @Param("example") TestTaskRelReportDoExample example);

    int updateByPrimaryKeySelective(TestTaskRelReportDo record);

    int updateByPrimaryKey(TestTaskRelReportDo record);
}