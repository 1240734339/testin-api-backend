package cn.testin.pojo.vo;

import cn.testin.extranal.io.dto.RunModeConfigDTO;
import cn.testin.pojo.vo.request.base.TestinElement;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @title: RunDefinitionRequest
 * @description: 接口运行请求体
 */
@Data
public class RunDefinitionRequest {

    private String id;

    private String name;

    private String type;

    private String runMode;

    private TestinElement testElement;

    private String projectId;

    private String environmentId;

}
