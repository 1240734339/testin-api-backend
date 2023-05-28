package cn.testin.mapper;

import cn.testin.pojo.domain.TestModuleDo;
import cn.testin.pojo.domain.TestModuleDoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TestModuleDoMapper {
    long countByExample(TestModuleDoExample example);

    int deleteByExample(TestModuleDoExample example);

    int deleteByPrimaryKey(String id);

    int insert(TestModuleDo record);

    int insertSelective(TestModuleDo record);

    List<TestModuleDo> selectByExample(TestModuleDoExample example);

    TestModuleDo selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TestModuleDo record, @Param("example") TestModuleDoExample example);

    int updateByExample(@Param("record") TestModuleDo record, @Param("example") TestModuleDoExample example);

    int updateByPrimaryKeySelective(TestModuleDo record);

    int updateByPrimaryKey(TestModuleDo record);
}