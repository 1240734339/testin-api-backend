package cn.testin.pojo.dto.request.http;

import cn.testin.commons.utils.BeanUtils;
import cn.testin.pojo.dto.request.KeyValue;
import cn.testin.pojo.dto.environment.GlobalScriptConfig;
import cn.testin.pojo.vo.request.element.TestinAssertions;
import cn.testin.pojo.vo.request.element.TestinJSR223PostProcessor;
import cn.testin.pojo.vo.request.element.TestinJSR223PreProcessor;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @title: HttpConfig
 * @description:
 */
@Data
public class HttpConfig {
    private String apiEnvironmentid;
    private String socket;
    private String domain;
    private String protocol = "https";
    private int port;
    private boolean isMock;
    private List<HttpConfigCondition> conditions;
    private List<KeyValue> headers;
    private TestinJSR223PreProcessor preProcessor;
    private TestinJSR223PostProcessor postProcessor;
    private GlobalScriptConfig globalScriptConfig;
//    private ApiModuleMapper apiModuleMapper;
    private List<TestinAssertions> assertions;
    private List<TestinAssertions> errorReportAssertions;
    private String description;


    public HttpConfig initHttpConfig(HttpConfigCondition configCondition) {
        HttpConfig config = new HttpConfig();
        config.isMock = this.isMock;
        BeanUtils.copyBean(config, configCondition);
        config.setHeaders(configCondition.getHeaders());
        return config;
    }

    public HttpConfig getPathCondition(String path, HttpConfigCondition configCondition) {
        if (CollectionUtils.isNotEmpty(configCondition.getDetails())) {
            List<KeyValue> details = configCondition.getDetails().stream().filter(detail -> (detail.getValue().equals("contains") && StringUtils.contains(path, detail.getName())) || (detail.getValue().equals("equals") && StringUtils.equals(path, detail.getName()))).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(details)) {
                return initHttpConfig(configCondition);
            }
        }
        return null;
    }

    public HttpConfig getModuleCondition(String moduleId, HttpConfigCondition configCondition) {
        if (CollectionUtils.isNotEmpty(configCondition.getDetails())) {
            List<KeyValue> details = configCondition.getDetails().stream().filter(detail -> StringUtils.contains(detail.getValue(), moduleId)).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(details)) {
                return initHttpConfig(configCondition);
            }
        }
        return null;
    }
}
