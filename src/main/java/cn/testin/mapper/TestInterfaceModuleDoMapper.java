package cn.testin.mapper;

import cn.testin.pojo.domain.TestInterfaceModuleDo;
import cn.testin.pojo.domain.TestInterfaceModuleDoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TestInterfaceModuleDoMapper {
    long countByExample(TestInterfaceModuleDoExample example);

    int deleteByExample(TestInterfaceModuleDoExample example);

    int deleteByPrimaryKey(String id);

    int insert(TestInterfaceModuleDo record);

    int insertSelective(TestInterfaceModuleDo record);

    List<TestInterfaceModuleDo> selectByExample(TestInterfaceModuleDoExample example);

    TestInterfaceModuleDo selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TestInterfaceModuleDo record, @Param("example") TestInterfaceModuleDoExample example);

    int updateByExample(@Param("record") TestInterfaceModuleDo record, @Param("example") TestInterfaceModuleDoExample example);

    int updateByPrimaryKeySelective(TestInterfaceModuleDo record);

    int updateByPrimaryKey(TestInterfaceModuleDo record);
}