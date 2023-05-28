package cn.testin.mapper;

import cn.testin.pojo.domain.TestDataSetDetailDo;
import cn.testin.pojo.domain.TestDataSetDetailDoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TestDataSetDetailDoMapper {
    long countByExample(TestDataSetDetailDoExample example);

    int deleteByExample(TestDataSetDetailDoExample example);

    int deleteByPrimaryKey(String id);

    int insert(TestDataSetDetailDo record);

    int insertSelective(TestDataSetDetailDo record);

    List<TestDataSetDetailDo> selectByExampleWithBLOBs(TestDataSetDetailDoExample example);

    List<TestDataSetDetailDo> selectByExample(TestDataSetDetailDoExample example);

    TestDataSetDetailDo selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TestDataSetDetailDo record, @Param("example") TestDataSetDetailDoExample example);

    int updateByExampleWithBLOBs(@Param("record") TestDataSetDetailDo record, @Param("example") TestDataSetDetailDoExample example);

    int updateByExample(@Param("record") TestDataSetDetailDo record, @Param("example") TestDataSetDetailDoExample example);

    int updateByPrimaryKeySelective(TestDataSetDetailDo record);

    int updateByPrimaryKeyWithBLOBs(TestDataSetDetailDo record);

    int updateByPrimaryKey(TestDataSetDetailDo record);
}