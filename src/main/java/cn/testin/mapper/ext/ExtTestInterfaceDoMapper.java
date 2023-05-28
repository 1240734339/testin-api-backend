package cn.testin.mapper.ext;

import cn.testin.pojo.domain.TestInterfaceDo;
import cn.testin.pojo.dto.request.TestInterfaceRequest;
import cn.testin.pojo.vo.result.TestInterfaceResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExtTestInterfaceDoMapper {

    TestInterfaceDo getNextNum(@Param("projectId") String projectId);

    List<TestInterfaceResult> list(@Param("request") TestInterfaceRequest request);
}
