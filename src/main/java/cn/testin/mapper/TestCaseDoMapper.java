package cn.testin.mapper;

import cn.testin.pojo.domain.TestCaseDo;
import cn.testin.pojo.domain.TestCaseDoExample;
import cn.testin.pojo.domain.TestCaseDoWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TestCaseDoMapper {
    long countByExample(TestCaseDoExample example);

    int deleteByExample(TestCaseDoExample example);

    int deleteByPrimaryKey(String id);

    int insert(TestCaseDoWithBLOBs record);

    int insertSelective(TestCaseDoWithBLOBs record);

    List<TestCaseDoWithBLOBs> selectByExampleWithBLOBs(TestCaseDoExample example);

    List<TestCaseDo> selectByExample(TestCaseDoExample example);

    TestCaseDoWithBLOBs selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TestCaseDoWithBLOBs record, @Param("example") TestCaseDoExample example);

    int updateByExampleWithBLOBs(@Param("record") TestCaseDoWithBLOBs record, @Param("example") TestCaseDoExample example);

    int updateByExample(@Param("record") TestCaseDo record, @Param("example") TestCaseDoExample example);

    int updateByPrimaryKeySelective(TestCaseDoWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(TestCaseDoWithBLOBs record);

    int updateByPrimaryKey(TestCaseDo record);
}