package cn.testin.mapper.ext;

import cn.testin.pojo.domain.TestScriptDo;
import cn.testin.pojo.dto.TestScriptDTO;
import cn.testin.pojo.dto.request.SaveScriptRequest;
import cn.testin.pojo.dto.request.TestScriptRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExtTestScriptDoMapper {

    List<TestScriptDo> checkName(@Param("request") SaveScriptRequest request);

    TestScriptDo getNextNum(@Param("interfaceId") String interfaceId);

    List<TestScriptDTO> listSimple(@Param("request") TestScriptRequest request);
}

