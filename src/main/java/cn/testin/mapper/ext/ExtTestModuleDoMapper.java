package cn.testin.mapper.ext;

import cn.testin.pojo.dto.TestModuleDoDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExtTestModuleDoMapper {
    List<TestModuleDoDTO> getByProjectAndProtocolAndType(@Param("projectId") String projectId, @Param("protocol") String protocol, @Param("type") String type);

    List<TestModuleDoDTO> listByParentId(@Param("parentId") String parentId);

}
