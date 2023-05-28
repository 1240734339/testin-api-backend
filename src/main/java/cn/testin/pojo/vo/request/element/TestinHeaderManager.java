package cn.testin.pojo.vo.request.element;

import cn.testin.pojo.dto.request.KeyValue;
import cn.testin.pojo.vo.request.base.TestinElement;
import cn.testin.pojo.dto.environment.ParameterConfig;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.jmeter.protocol.http.control.Header;
import org.apache.jmeter.protocol.http.control.HeaderManager;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jorphan.collections.HashTree;

import java.util.List;

/**
 * @title: TestinHeaderManager
 * @description:
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JSONType(typeName = "HeaderManager")
public class TestinHeaderManager extends TestinElement {


    private String type = "HeaderManager";
    private String clazzName = TestinHeaderManager.class.getCanonicalName();

    @JSONField(ordinal = 20)
    private List<KeyValue> headers;

    @Override
    public void toHashTree(HashTree tree, List<TestinElement> hashTree, ParameterConfig config) {
        HeaderManager headerManager = new HeaderManager();
        headerManager.setEnabled(this.isEnable());
        headerManager.setName(StringUtils.isNotEmpty(this.getName()) ? this.getName() + "HeaderManager" : "HeaderManager");
        headerManager.setProperty(TestElement.TEST_CLASS, HeaderManager.class.getName());
        headerManager.setProperty(TestElement.GUI_CLASS, SaveService.aliasToClass("HeaderPanel"));
        headers.stream().filter(KeyValue::isValid).filter(KeyValue::isEnable).forEach(keyValue ->
                headerManager.add(new Header(keyValue.getName(), keyValue.getValue()))
        );
        final HashTree headersTree = tree.add(headerManager);
        if (CollectionUtils.isNotEmpty(hashTree)) {
            hashTree.forEach(el -> {
                el.toHashTree(headersTree, el.getHashTree(), config);
            });
        }
    }
}
