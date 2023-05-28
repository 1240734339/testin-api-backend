package cn.testin.mapper;

import cn.testin.pojo.domain.TestScriptDo;
import cn.testin.pojo.domain.TestScriptDoExample;
import cn.testin.pojo.domain.TestScriptDoWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TestScriptDoMapper {
    long countByExample(TestScriptDoExample example);

    int deleteByExample(TestScriptDoExample example);

    int deleteByPrimaryKey(String id);

    int insert(TestScriptDoWithBLOBs record);

    int insertSelective(TestScriptDoWithBLOBs record);

    List<TestScriptDoWithBLOBs> selectByExampleWithBLOBs(TestScriptDoExample example);

    List<TestScriptDo> selectByExample(TestScriptDoExample example);

    TestScriptDoWithBLOBs selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TestScriptDoWithBLOBs record, @Param("example") TestScriptDoExample example);

    int updateByExampleWithBLOBs(@Param("record") TestScriptDoWithBLOBs record, @Param("example") TestScriptDoExample example);

    int updateByExample(@Param("record") TestScriptDo record, @Param("example") TestScriptDoExample example);

    int updateByPrimaryKeySelective(TestScriptDoWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(TestScriptDoWithBLOBs record);

    int updateByPrimaryKey(TestScriptDo record);
}