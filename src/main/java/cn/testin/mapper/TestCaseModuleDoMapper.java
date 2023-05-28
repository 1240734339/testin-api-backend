package cn.testin.mapper;

import cn.testin.pojo.domain.TestCaseModuleDo;
import cn.testin.pojo.domain.TestCaseModuleDoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TestCaseModuleDoMapper {
    long countByExample(TestCaseModuleDoExample example);

    int deleteByExample(TestCaseModuleDoExample example);

    int deleteByPrimaryKey(String id);

    int insert(TestCaseModuleDo record);

    int insertSelective(TestCaseModuleDo record);

    List<TestCaseModuleDo> selectByExample(TestCaseModuleDoExample example);

    TestCaseModuleDo selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TestCaseModuleDo record, @Param("example") TestCaseModuleDoExample example);

    int updateByExample(@Param("record") TestCaseModuleDo record, @Param("example") TestCaseModuleDoExample example);

    int updateByPrimaryKeySelective(TestCaseModuleDo record);

    int updateByPrimaryKey(TestCaseModuleDo record);
}