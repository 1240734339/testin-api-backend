package cn.testin.pojo.vo.request.element;

import cn.testin.commons.utils.FileUtils;
import cn.testin.pojo.dto.request.KeyValue;
import cn.testin.pojo.dto.request.variable.ScenarioVariable;
import cn.testin.pojo.vo.request.ElementUtil;
import cn.testin.pojo.vo.request.base.TestinElement;
import cn.testin.pojo.dto.environment.ParameterConfig;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.jmeter.config.Arguments;
import org.apache.jorphan.collections.HashTree;

import java.util.List;
import java.util.Map;

/**
 * @title: TestinScenario
 * @description:
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JSONType(typeName = "scenario")
public class TestinScenario extends TestinElement {

    private String type = "scenario";
    private String clazzName = TestinScenario.class.getCanonicalName();

    @JSONField(ordinal = 21)
    private String referenced;

    @JSONField(ordinal = 22)
    private String environmentId;

    @JSONField(ordinal = 23)
    private List<ScenarioVariable> variables;

    @JSONField(ordinal = 24)
    private boolean enableCookieShare;

    @JSONField(ordinal = 26)
    private List<KeyValue> headers;

    @JSONField(ordinal = 27)
    private Map<String, String> environmentMap;

    @JSONField(ordinal = 28)
    private Boolean onSampleError;

    @JSONField(ordinal = 29)
    private boolean environmentEnable;

    @JSONField(ordinal = 30)
    private Boolean variableEnable;

    private static final String BODY_FILE_DIR = FileUtils.BODY_FILE_DIR;

    public TestinScenario() {
    }

    public TestinScenario(String name) {
        if (StringUtils.isEmpty(name)) {
            this.setName("Scenario");
        }
        this.setName(name);
    }


    @Override
    public void toHashTree(HashTree tree, List<TestinElement> hashTree, ParameterConfig config) {
// 非导出操作，且不是启用状态则跳过执行
//        if (!config.isOperating() && !this.isEnable()) {
//            return;
//        }
//        if (this.getReferenced() != null && this.getReferenced().equals(TestElementConstants.Deleted.name())) {
//            return;
//        } else if (this.getReferenced() != null && TestElementConstants.REF.name().equals(this.getReferenced())
//                && !this.setRefScenario(hashTree)) {
//            return;
//        }
        // 设置共享cookie
        config.setEnableCookieShare(enableCookieShare);
//        Map<String, EnvironmentConfig> envConfig = new HashMap<>(16);
////        if (config.getConfig() == null) {
////            // 兼容历史数据
////            if (this.environmentMap == null || this.environmentMap.isEmpty()) {
////                this.environmentMap = new HashMap<>(16);
////                if (StringUtils.isNotBlank(environmentId)) {
////                    // 兼容1.8之前 没有environmentMap但有environmentId的数据
////                    this.environmentMap.put(RunModeConstants.HIS_PRO_ID.toString(), environmentId);
////                }
////            }
////            if (this.environmentMap != null && !this.environmentMap.isEmpty()) {
////                this.setEnv(this.environmentMap, envConfig);
////                config.setConfig(envConfig);
////            }
////        } else {
////            Map<String, EnvironmentConfig> map = config.getConfig();
////            for (EnvironmentConfig evnConfig : map.values()) {
////                if (evnConfig.getHttpConfig() != null) {
////                    this.setMockEnvironment(evnConfig.getHttpConfig().isMock());
////                }
////            }
////        }
        if (CollectionUtils.isNotEmpty(this.getVariables()) && (this.variableEnable == null || this.variableEnable)) {
            config.setVariables(this.variables);
        }
        HashTree scenarioTree = tree;
        // 取出自身场景环境
        ParameterConfig newConfig = new ParameterConfig();
//        if (this.isEnvironmentEnable()) {
//            this.setNewConfig(envConfig, newConfig);
//        }

        if (config != null && StringUtils.equals(this.getId(), config.getCaseId())) {
            config.setTransferVariables(this.variables);
        }

        if (config != null && !config.getExcludeScenarioIds().contains(this.getId())) {
            scenarioTree = TestinCriticalSectionController.createHashTree(tree, this.getName(), this.isEnable());
        }
        // 环境变量
        Arguments arguments = ElementUtil.getConfigArguments(this.isEnvironmentEnable() ? newConfig : config, this.getName(), this.getProjectId(), this.getVariables());

        if (arguments != null && (this.variableEnable == null || this.variableEnable)) {
            Arguments valueSupposeMock = ParameterConfig.valueSupposeMock(arguments);
            // 这里加入自定义变量解决ForEach循环控制器取值问题，循环控制器无法从vars中取值
            if (this.variableEnable != null && this.variableEnable) {
                scenarioTree.add(ElementUtil.argumentsToUserParameters(valueSupposeMock));
            } else {
                scenarioTree.add(valueSupposeMock);
            }
        }
        if (this.variableEnable == null || this.variableEnable) {
//            ElementUtil.addCsvDataSet(scenarioTree, variables, this.isEnvironmentEnable() ? newConfig : config, "shareMode.group");
            ElementUtil.addCounter(scenarioTree, variables, false);
            ElementUtil.addRandom(scenarioTree, variables);
            if (CollectionUtils.isNotEmpty(this.headers)) {
                if (this.isEnvironmentEnable()) {
                    newConfig.setHeaders(this.headers);
                } else {
                    config.setHeaders(this.headers);
                }
            }
        }
        // 添加全局前置
//        this.setGlobProcessor(this.isEnvironmentEnable() ? newConfig : config, scenarioTree, true);

        if (CollectionUtils.isNotEmpty(hashTree)) {
            for (TestinElement el : hashTree) {
                if (el != null) {
                    el.setParent(this);
                    el.setMockEnvironment(this.isMockEnvironment());
                    if (this.isEnvironmentEnable()) {
                        newConfig.setCaseId(config.getCaseId());
                        newConfig.setReportType(config.getReportType());
                        el.toHashTree(scenarioTree, el.getHashTree(), newConfig);
                    } else {
                        el.toHashTree(scenarioTree, el.getHashTree(), config);
                    }
                }
            }
        }
        // 添加全局后置
//        this.setGlobProcessor(this.isEnvironmentEnable() ? newConfig : config, scenarioTree, false);
    }


}
