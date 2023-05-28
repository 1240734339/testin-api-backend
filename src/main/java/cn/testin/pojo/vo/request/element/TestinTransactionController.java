package cn.testin.pojo.vo.request.element;

import cn.testin.pojo.vo.request.base.TestinElement;
import cn.testin.pojo.dto.environment.ParameterConfig;
import com.alibaba.fastjson.annotation.JSONType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.jmeter.control.TransactionController;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jorphan.collections.HashTree;

import java.util.List;

/**
 * @title: TestinTransactionController
 * @description:
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JSONType(typeName = "TransactionController")
public class TestinTransactionController extends TestinElement {

    private static final String NAME_CN = "事务控制器";
    private String type = "TransactionController";
    private String clazzName = TestinTransactionController.class.getCanonicalName();

    private String name;
    private boolean generateParentSample;
    private boolean includeTimers;

    @Override
    public void toHashTree(HashTree tree, List<TestinElement> hashTree, ParameterConfig config) {
        // 非导出操作，且不是启用状态则跳过执行
        if (!config.isOperating() && !this.isEnable()) {
            return;
        }

        TransactionController transactionController = transactionController();
        final HashTree groupTree = tree.add(transactionController);
        if (CollectionUtils.isNotEmpty(hashTree)) {
            hashTree.forEach(el -> {
                // 给所有孩子加一个父亲标志
                el.setParent(this);
                el.toHashTree(groupTree, el.getHashTree(), config);
            });
        }
    }

    private TransactionController transactionController() {
        TransactionController transactionController = new TransactionController();
        transactionController.setEnabled(this.isEnable());
        if (StringUtils.isEmpty(this.getName())) {
            this.setName(getLabelName());
        }
        transactionController.setName("Transaction=" + this.getName());
        transactionController.setProperty(TestElement.TEST_CLASS, TransactionController.class.getName());
        transactionController.setProperty(TestElement.GUI_CLASS, SaveService.aliasToClass("TransactionControllerGui"));
        transactionController.setGenerateParentSample(generateParentSample);
        transactionController.setIncludeTimers(includeTimers);
        return transactionController;
    }

    public boolean isValid() {
        return StringUtils.isNotBlank(name);
    }

    public String getLabelName() {
        if (isValid()) {
            return NAME_CN + " " + this.name;
        }
        return NAME_CN;
    }
}
