package cn.testin.mapper;

import cn.testin.pojo.domain.TestTaskModuleDo;
import cn.testin.pojo.domain.TestTaskModuleDoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TestTaskModuleDoMapper {
    long countByExample(TestTaskModuleDoExample example);

    int deleteByExample(TestTaskModuleDoExample example);

    int deleteByPrimaryKey(String id);

    int insert(TestTaskModuleDo record);

    int insertSelective(TestTaskModuleDo record);

    List<TestTaskModuleDo> selectByExample(TestTaskModuleDoExample example);

    TestTaskModuleDo selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TestTaskModuleDo record, @Param("example") TestTaskModuleDoExample example);

    int updateByExample(@Param("record") TestTaskModuleDo record, @Param("example") TestTaskModuleDoExample example);

    int updateByPrimaryKeySelective(TestTaskModuleDo record);

    int updateByPrimaryKey(TestTaskModuleDo record);
}