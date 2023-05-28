package cn.testin.service;

import cn.testin.pojo.vo.RunDefinitionRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * @title: ApiAutomationService
 * @description: 接口自动化服务类
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ApiAutomationService {

    @Resource
    private ApiScenarioExecuteService apiScenarioExecuteService;

    /**
     * @param request
     * @return java.lang.String
     * @Description 场景调试执行
     **/
    public String debugRun(RunDefinitionRequest request) {
        return apiScenarioExecuteService.debug(request);
    }
}
