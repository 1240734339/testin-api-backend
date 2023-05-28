package cn.testin.mapper.ext;

import cn.testin.pojo.dto.TestInterfaceModuleDoDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExtTestInterfaceModuleDoMapper {

    List<TestInterfaceModuleDoDTO> getByProjectAndProtocol(@Param("projectId") String projectId, @Param("protocol") String protocol);

}
