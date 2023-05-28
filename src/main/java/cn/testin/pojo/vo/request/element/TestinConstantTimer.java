package cn.testin.pojo.vo.request.element;

import cn.testin.pojo.vo.request.base.TestinElement;
import cn.testin.pojo.dto.environment.ParameterConfig;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.timers.ConstantTimer;
import org.apache.jorphan.collections.HashTree;

import java.util.List;

/**
 * @title: TestinConstantTimer
 * @description:
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JSONType(typeName = "ConstantTimer")
public class TestinConstantTimer extends TestinElement {

    private String type = "ConstantTimer";
    private String clazzName = TestinConstantTimer.class.getCanonicalName();


    @JSONField(ordinal = 20)
    private String id;
    @JSONField(ordinal = 21)
    private String delay;

    @Override
    public void toHashTree(HashTree tree, List<TestinElement> hashTree, ParameterConfig config) {
        // 非导出操作，且不是启用状态则跳过执行
        if (!config.isOperating() && !this.isEnable()) {
            return;
        }
        final HashTree groupTree = tree.add(constantTimer());
        if (CollectionUtils.isNotEmpty(hashTree)) {
            hashTree.forEach(el -> {
                el.toHashTree(groupTree, el.getHashTree(), config);
            });
        }
    }

    private ConstantTimer constantTimer() {
        ConstantTimer constantTimer = new ConstantTimer();
        constantTimer.setEnabled(this.isEnable());
        constantTimer.setName(this.getDelay() + " ms");
        constantTimer.setProperty(TestElement.TEST_CLASS, ConstantTimer.class.getName());
        constantTimer.setProperty(TestElement.GUI_CLASS, SaveService.aliasToClass("ConstantTimerGui"));
        constantTimer.setDelay(this.getDelay());
        return constantTimer;
    }
}
