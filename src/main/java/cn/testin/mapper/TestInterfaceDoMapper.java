package cn.testin.mapper;

import cn.testin.pojo.domain.TestInterfaceDo;
import cn.testin.pojo.domain.TestInterfaceDoExample;
import cn.testin.pojo.domain.TestInterfaceDoWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TestInterfaceDoMapper {
    long countByExample(TestInterfaceDoExample example);

    int deleteByExample(TestInterfaceDoExample example);

    int deleteByPrimaryKey(String id);

    int insert(TestInterfaceDoWithBLOBs record);

    int insertSelective(TestInterfaceDoWithBLOBs record);

    List<TestInterfaceDoWithBLOBs> selectByExampleWithBLOBs(TestInterfaceDoExample example);

    List<TestInterfaceDo> selectByExample(TestInterfaceDoExample example);

    TestInterfaceDoWithBLOBs selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TestInterfaceDoWithBLOBs record, @Param("example") TestInterfaceDoExample example);

    int updateByExampleWithBLOBs(@Param("record") TestInterfaceDoWithBLOBs record, @Param("example") TestInterfaceDoExample example);

    int updateByExample(@Param("record") TestInterfaceDo record, @Param("example") TestInterfaceDoExample example);

    int updateByPrimaryKeySelective(TestInterfaceDoWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(TestInterfaceDoWithBLOBs record);

    int updateByPrimaryKey(TestInterfaceDo record);
}