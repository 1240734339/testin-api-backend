package cn.testin.service;

import cn.testin.commons.constants.ApiRunMode;
import cn.testin.commons.utils.LogUtil;
import cn.testin.extranal.io.dto.JmeterRunRequestDTO;
import cn.testin.pojo.dto.environment.ParameterConfig;
import cn.testin.pojo.vo.RunDefinitionRequest;
import cn.testin.pojo.vo.request.element.TestinTestPlan;
import cn.testin.service.jmeter.JmeterService;
import org.apache.commons.lang3.StringUtils;
import org.apache.jorphan.collections.HashTree;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @title: ApiScenarioExecuteService
 * @description:
 */
@Service
public class ApiScenarioExecuteService {

    @Resource
    private JmeterService jmeterService;

    @Resource
    private TestReportService reportService;

    @Resource
    private TestReportDetailService reportDetailService;

    @Resource
    private TcpApiParamService tcpApiParamService;

    /**
     * @param request
     * @return java.lang.String
     * @Description 场景调试
     **/
    public String debug(RunDefinitionRequest request) {

        ParameterConfig config = new ParameterConfig();
//        config.setScenarioId(request.getScenarioId());
        String reportId = initReportInfo(request, config);

        // 调用执行方法
        LogUtil.info("调用调试方法，开始执行", reportId);

        this.testElement(request);
        // 加载自定义JAR
//        NewDriverManager.loadJar(request);
        HashTree hashTree = request.getTestElement().generateHashTree(config);
//        String runMode = StringUtils.isEmpty(request.getRunMode()) ? ApiRunMode.SCENARIO.name() : request.getRunMode();
        JmeterRunRequestDTO runRequest =
                new JmeterRunRequestDTO(request.getId(),reportId, hashTree,request.getName(),request.getRunMode());
        LogUtil.info(new TestinTestPlan().getJmx(hashTree));
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("projectId",request.getProjectId());
        map.put("environmentId",request.getEnvironmentId());
        //todo 还需要查询环境
        map.put("reportType",request.getType());
        runRequest.setExtendParams(map);
        jmeterService.run(runRequest);
        return reportId;
    }

    private void testElement(RunDefinitionRequest request) {
        if (request.getTestElement() != null) {
            tcpApiParamService.checkTestElement(request.getTestElement());
        }
    }

    /**
     * @param request
     * @param config
     * @return java.lang.String
     * @Description 初始化报告信息
     **/
    private String initReportInfo(RunDefinitionRequest request, ParameterConfig config) {
        return  reportService.initReportInfo(request);
    }
}
