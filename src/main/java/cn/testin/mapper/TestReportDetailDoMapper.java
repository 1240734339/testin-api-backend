package cn.testin.mapper;

import cn.testin.pojo.domain.TestReportDetailDo;
import cn.testin.pojo.domain.TestReportDetailDoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TestReportDetailDoMapper {
    long countByExample(TestReportDetailDoExample example);

    int deleteByExample(TestReportDetailDoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TestReportDetailDo record);

    int insertSelective(TestReportDetailDo record);

    List<TestReportDetailDo> selectByExampleWithBLOBs(TestReportDetailDoExample example);

    List<TestReportDetailDo> selectByExample(TestReportDetailDoExample example);

    TestReportDetailDo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TestReportDetailDo record, @Param("example") TestReportDetailDoExample example);

    int updateByExampleWithBLOBs(@Param("record") TestReportDetailDo record, @Param("example") TestReportDetailDoExample example);

    int updateByExample(@Param("record") TestReportDetailDo record, @Param("example") TestReportDetailDoExample example);

    int updateByPrimaryKeySelective(TestReportDetailDo record);

    int updateByPrimaryKeyWithBLOBs(TestReportDetailDo record);

    int updateByPrimaryKey(TestReportDetailDo record);

    /**
     * @param repoDetailList
     * @return int
     * @Description 批量新增
     **/
    int insertBatchSelective(@Param("repoDetailList") List<TestReportDetailDo> repoDetailList);
}