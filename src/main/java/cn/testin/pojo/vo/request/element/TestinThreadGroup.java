package cn.testin.pojo.vo.request.element;


import cn.testin.commons.constants.RunningParamKeys;
import cn.testin.pojo.vo.request.base.TestinElement;
import cn.testin.pojo.dto.environment.ParameterConfig;
import com.alibaba.fastjson.annotation.JSONType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.protocol.http.control.CookieManager;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jorphan.collections.HashTree;

import java.util.List;

/**
 * @author WangG
 * @title: TestinThreadGroup
 * @description:
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JSONType(typeName = "ThreadGroup")
public class TestinThreadGroup extends TestinElement {

    private String type = "ThreadGroup";
    private String clazzName = TestinThreadGroup.class.getCanonicalName();

    private boolean enableCookieShare;
    private Boolean onSampleError;

    @Override
    public void toHashTree(HashTree tree, List<TestinElement> hashTree, ParameterConfig config) {
        final HashTree groupTree = tree.add(getThreadGroup());
        if ((config != null && config.isEnableCookieShare()) || enableCookieShare) {
            CookieManager cookieManager = new CookieManager();
            cookieManager.setProperty(TestElement.TEST_CLASS, CookieManager.class.getName());
            cookieManager.setProperty(TestElement.GUI_CLASS, SaveService.aliasToClass("CookiePanel"));
            cookieManager.setEnabled(true);
            cookieManager.setName("CookieManager");
            cookieManager.setClearEachIteration(false);
            cookieManager.setControlledByThread(false);
            groupTree.add(cookieManager);
        }

        if (CollectionUtils.isNotEmpty(hashTree)) {
            for (TestinElement el : hashTree) {
                el.toHashTree(groupTree, el.getHashTree(), config);
            }
            if (!config.isOperating()) {
                TestinDebugSampler el = new TestinDebugSampler();
                el.setName(RunningParamKeys.RUNNING_DEBUG_SAMPLER_NAME);
                el.toHashTree(groupTree, el.getHashTree(), config);
            }
        }
    }

    public ThreadGroup getThreadGroup() {
        LoopController loopController = new LoopController();
        loopController.setName("LoopController");
        loopController.setProperty(TestElement.TEST_CLASS, LoopController.class.getName());
        loopController.setProperty(TestElement.GUI_CLASS, SaveService.aliasToClass("LoopControlPanel"));
        loopController.setEnabled(this.isEnable());
        loopController.setLoops(1);

        ThreadGroup threadGroup = new ThreadGroup();
        threadGroup.setEnabled(this.isEnable());
        threadGroup.setName(this.getName());
        threadGroup.setProperty(TestElement.TEST_CLASS, ThreadGroup.class.getName());
        threadGroup.setProperty(TestElement.GUI_CLASS, SaveService.aliasToClass("ThreadGroupGui"));
        threadGroup.setNumThreads(1);
        threadGroup.setRampUp(1);
        threadGroup.setDelay(0);
        threadGroup.setDuration(0);
        threadGroup.setProperty(ThreadGroup.ON_SAMPLE_ERROR, ThreadGroup.ON_SAMPLE_ERROR_CONTINUE);
        threadGroup.setScheduler(false);
        if (onSampleError != null && !onSampleError) {
            threadGroup.setProperty("ThreadGroup.on_sample_error", "stoptest");
        }
        threadGroup.setSamplerController(loopController);
        return threadGroup;
    }
}
