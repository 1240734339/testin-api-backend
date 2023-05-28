package cn.testin.pojo.vo.request.element;

import cn.testin.commons.constants.RunningParamKeys;
import cn.testin.commons.filter.ScriptFilter;
import cn.testin.extranal.io.utils.JMeterVars;
import cn.testin.pojo.dto.environment.EnvironmentConfig;
import cn.testin.pojo.vo.request.base.TestinElement;
import cn.testin.pojo.dto.environment.ParameterConfig;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.jmeter.modifiers.JSR223PreProcessor;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jorphan.collections.HashTree;

import java.util.Collection;
import java.util.List;

/**
 * @title: TestinJSR223PreProcessor
 * @description:
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JSONType(typeName = "JSR223PreProcessor")
public class TestinJSR223PreProcessor extends TestinElement {

    private String type = "JSR223PreProcessor";
    private String clazzName = TestinJSR223PreProcessor.class.getCanonicalName();

    @JSONField(ordinal = 20)
    private String script;

    @JSONField(ordinal = 21)
    private String scriptLanguage;

    @Override
    public void toHashTree(HashTree tree, List<TestinElement> hashTree, ParameterConfig config) {
        // 非导出操作，且不是启用状态则跳过执行
        if (!config.isOperating() && !this.isEnable()) {
            return;
        }
        ScriptFilter.verify(this.getScriptLanguage(), this.getName(), script);
        if (StringUtils.isEmpty(this.getEnvironmentId())) {
            if (config.getConfig() != null) {
                if (config.getProjectId() != null) {
                    String evnId = config.getConfig().get(config.getProjectId()).getApiEnvironmentid();
                    this.setEnvironmentId(evnId);
                } else {
                    Collection<EnvironmentConfig> evnConfigList = config.getConfig().values();
                    if (evnConfigList != null && !evnConfigList.isEmpty()) {
                        for (EnvironmentConfig configItem : evnConfigList) {
                            String evnId = configItem.getApiEnvironmentid();
                            this.setEnvironmentId(evnId);
                            break;
                        }
                    }
                }
            }
        }
        //替换环境变量
        if (StringUtils.isNotEmpty(script)) {
            script = StringUtils.replace(script, RunningParamKeys.API_ENVIRONMENT_ID, "\"" + RunningParamKeys.RUNNING_PARAMS_PREFIX + this.getEnvironmentId() + ".\"");
        }
        if (config.isOperating()) {
            if (StringUtils.isNotEmpty(script) && script.startsWith(JMeterVars.class.getCanonicalName())) {
                return;
            }
        }

        final HashTree jsr223PreTree = tree.add(getJSR223PreProcessor());
        if (CollectionUtils.isNotEmpty(hashTree)) {
            hashTree.forEach(el -> {
                el.toHashTree(jsr223PreTree, el.getHashTree(), config);
            });
        }
    }

    public JSR223PreProcessor getJSR223PreProcessor() {

        JSR223PreProcessor processor = new JSR223PreProcessor();
        processor.setEnabled(this.isEnable());
        if (StringUtils.isNotEmpty(this.getName())) {
            processor.setName(this.getName());
        } else {
            processor.setName("JSR223PreProcessor");
        }
        processor.setProperty(TestElement.TEST_CLASS, JSR223PreProcessor.class.getName());
        processor.setProperty(TestElement.GUI_CLASS, SaveService.aliasToClass("TestBeanGUI"));
        /*processor.setProperty("cacheKey", "true");*/
        processor.setProperty("scriptLanguage", this.getScriptLanguage());
        if (StringUtils.isNotEmpty(this.getScriptLanguage()) && this.getScriptLanguage().equals("nashornScript")) {
            processor.setProperty("scriptLanguage", "nashorn");
        }
        if (StringUtils.isNotEmpty(this.getScriptLanguage()) && this.getScriptLanguage().equals("rhinoScript")) {
            processor.setProperty("scriptLanguage", "rhino");
        }
        if (StringUtils.isNotEmpty(this.getScriptLanguage()) && this.getScriptLanguage().equals("javascript")) {
            processor.setProperty("scriptLanguage", "rhino");
        }

        processor.setProperty("script", this.getScript());
        return processor;
    }
}
