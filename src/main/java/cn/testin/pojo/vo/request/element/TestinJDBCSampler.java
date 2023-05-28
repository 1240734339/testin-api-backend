package cn.testin.pojo.vo.request.element;

import cn.testin.pojo.dto.environment.DatabaseConfig;
import cn.testin.pojo.dto.request.KeyValue;
import cn.testin.pojo.vo.request.base.TestinElement;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @title: TestinJDBCSampler
 * @description:
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JSONType(typeName = "JDBCSampler")
public class TestinJDBCSampler extends TestinElement {

    // type 必须放最前面，以便能够转换正确的类
    private String type = "JDBCSampler";
    private String clazzName = TestinJDBCSampler.class.getCanonicalName();

    @JSONField(ordinal = 20)
    private DatabaseConfig dataSource;
    @JSONField(ordinal = 21)
    private String query;
    @JSONField(ordinal = 22)
    private long queryTimeout;
    @JSONField(ordinal = 23)
    private String resultVariable;
    @JSONField(ordinal = 24)
    private String variableNames;
    @JSONField(ordinal = 25)
    private List<KeyValue> variables;
    @JSONField(ordinal = 26)
    private String environmentId;
    @JSONField(ordinal = 28)
    private String dataSourceId;
    @JSONField(ordinal = 29)
    private String protocol = "SQL";

    @JSONField(ordinal = 30)
    private String useEnvironment;

    @JSONField(ordinal = 31)
    private boolean customizeReq;

    @JSONField(ordinal = 32)
    private Boolean isRefEnvironment;
}
