package cn.testin.pojo.vo.request.element;

import cn.testin.commons.constants.ApiTestConstants;
import cn.testin.pojo.vo.request.base.TestinElement;
import cn.testin.pojo.dto.environment.ParameterConfig;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jorphan.collections.HashTree;

import java.util.List;

/**
 * @author WangG
 * @title: TestinTestPlan
 * @description:
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JSONType(typeName = "TestPlan")
public class TestinTestPlan extends TestinElement {

    private String type = "TestPlan";
    private String clazzName = TestinTestPlan.class.getCanonicalName();
    // 自定义JAR
    private List<String> jarPaths;

    private boolean serializeThreadGroups = false;

    @Override
    public void toHashTree(HashTree tree, List<TestinElement> hashTree, ParameterConfig config) {
        final HashTree testPlanTree = tree.add(getPlan());
        if (CollectionUtils.isNotEmpty(hashTree)) {
            hashTree.forEach(el -> {
                el.toHashTree(testPlanTree, el.getHashTree(), config);
            });
        }
    }

    public TestPlan getPlan() {
        TestPlan testPlan = new TestPlan(StringUtils.isEmpty(this.getName()) ? "TestPlan" : this.getName());
        testPlan.setProperty(TestElement.TEST_CLASS, TestPlan.class.getName());
        testPlan.setProperty(TestElement.GUI_CLASS, SaveService.aliasToClass("TestPlanGui"));
        testPlan.setEnabled(true);
        testPlan.setFunctionalMode(false);
        testPlan.setSerialized(serializeThreadGroups);
        testPlan.setTearDownOnShutdown(true);
        if (CollectionUtils.isNotEmpty(jarPaths)) {
            testPlan.setProperty(ApiTestConstants.JAR_PATH, JSON.toJSONString(jarPaths));
        }
        testPlan.setUserDefinedVariables(new Arguments());
        testPlan.setProperty("TestPlan.comments","");
        testPlan.setTestPlanClasspath("");
        return testPlan;
    }
}
