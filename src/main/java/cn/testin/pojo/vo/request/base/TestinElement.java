package cn.testin.pojo.vo.request.base;

import cn.testin.pojo.dto.environment.ParameterConfig;
import cn.testin.commons.utils.LogUtil;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.jmeter.save.SaveService;
import org.apache.jorphan.collections.HashTree;
import org.apache.jorphan.collections.ListedHashTree;

import java.io.ByteArrayOutputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @title: TestinElement
 * @description:
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "clazzName"
)
@JsonIgnoreProperties(
        ignoreUnknown = true
)
@Getter
@Setter
public abstract class TestinElement {

    private String type;
    private String clazzName = "cn.testin.pojo.vo.request.base.TestinElement";
    @JSONField(
            ordinal = 1
    )
    private String id;
    @JSONField(
            ordinal = 2
    )
    private String name;
    @JSONField(
            ordinal = 3
    )
    private String label;
    @JSONField(
            ordinal = 4
    )
    private String resourceId;
    @JSONField(
            ordinal = 5
    )
    private String referenced;
    @JSONField(
            ordinal = 6
    )
    private boolean active;
    @JSONField(
            ordinal = 7
    )
    private String index;
    @JSONField(
            ordinal = 8
    )
    private boolean enable = true;
    @JSONField(
            ordinal = 9
    )
    private String refType;
    @JSONField(
            ordinal = 10
    )
    private LinkedList<TestinElement> hashTree;
    @JSONField(
            ordinal = 12
    )
    private String projectId;
    @JSONField(
            ordinal = 13
    )
    private boolean isMockEnvironment;
    @JSONField(
            ordinal = 14
    )
    private String environmentId;
    @JSONField(
            ordinal = 15
    )
    private String pluginId;
    @JSONField(
            ordinal = 16
    )
    private String stepName;
    private TestinElement parent;

    public TestinElement(){}

    public void toHashTree(HashTree tree, List<TestinElement> hashTree, ParameterConfig config) {
        if (CollectionUtils.isNotEmpty(hashTree)) {
            Iterator var4 = hashTree.iterator();

            while(var4.hasNext()) {
                TestinElement el = (TestinElement)var4.next();
                el.toHashTree(tree, el.hashTree, config);
            }
        }

    }

    public String getJmx(HashTree hashTree) {
        try {
            ByteArrayOutputStream bas = new ByteArrayOutputStream();
            Throwable var3 = null;

            String var4;
            try {
                SaveService.saveTree(hashTree, bas);
                var4 = bas.toString();
            } catch (Throwable var14) {
                var3 = var14;
                throw var14;
            } finally {
                if (bas != null) {
                    if (var3 != null) {
                        try {
                            bas.close();
                        } catch (Throwable var13) {
                            var3.addSuppressed(var13);
                        }
                    } else {
                        bas.close();
                    }
                }

            }

            return var4;
        } catch (Exception var16) {
            var16.printStackTrace();
            LogUtil.warn("HashTree error, can't log jmx scenarioDefinition");
            return null;
        }
    }
    public HashTree generateHashTree(ParameterConfig config) {
        HashTree jmeterTestPlanHashTree = new ListedHashTree();
        this.toHashTree(jmeterTestPlanHashTree, this.hashTree, config);
        return jmeterTestPlanHashTree;
    }
}
