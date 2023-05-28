package cn.testin.mapper;

import cn.testin.pojo.domain.TestDataSetDo;
import cn.testin.pojo.domain.TestDataSetDoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TestDataSetDoMapper {
    long countByExample(TestDataSetDoExample example);

    int deleteByExample(TestDataSetDoExample example);

    int deleteByPrimaryKey(String id);

    int insert(TestDataSetDo record);

    int insertSelective(TestDataSetDo record);

    List<TestDataSetDo> selectByExampleWithBLOBs(TestDataSetDoExample example);

    List<TestDataSetDo> selectByExample(TestDataSetDoExample example);

    TestDataSetDo selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TestDataSetDo record, @Param("example") TestDataSetDoExample example);

    int updateByExampleWithBLOBs(@Param("record") TestDataSetDo record, @Param("example") TestDataSetDoExample example);

    int updateByExample(@Param("record") TestDataSetDo record, @Param("example") TestDataSetDoExample example);

    int updateByPrimaryKeySelective(TestDataSetDo record);

    int updateByPrimaryKeyWithBLOBs(TestDataSetDo record);

    int updateByPrimaryKey(TestDataSetDo record);
}