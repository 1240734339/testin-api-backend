package cn.testin.pojo.vo.request.element;

import cn.testin.pojo.vo.request.base.TestinElement;
import cn.testin.pojo.dto.environment.ParameterConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.jmeter.control.CriticalSectionController;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jorphan.collections.HashTree;

import java.util.List;
import java.util.UUID;

/**
 * @author WangG
 * @description:
 */
public class TestinCriticalSectionController extends TestinElement{

    @Override
    public void toHashTree(HashTree tree, List<TestinElement> hashTree, ParameterConfig config) {

    }

    public static HashTree createHashTree(HashTree tree, String name, boolean enable) {
        CriticalSectionController criticalSectionController = new CriticalSectionController();
        criticalSectionController.setName(StringUtils.isNotEmpty(name) ? "Csc_" + name
                : "Scenario Critical Section Controller");
        criticalSectionController.setLockName("global_lock_" + getUUID(8));
        criticalSectionController.setEnabled(enable);
        criticalSectionController.setProperty(TestElement.TEST_CLASS, CriticalSectionController.class.getName());
        criticalSectionController.setProperty(TestElement.GUI_CLASS, "CriticalSectionControllerGui");
        return tree.add(criticalSectionController);
    }

    public static String getUUID(int len) {
        String uuid = UUID.randomUUID().toString();
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < len; i++) {
            str.append(uuid.charAt(i));
        }
        return str.toString();
    }
}
