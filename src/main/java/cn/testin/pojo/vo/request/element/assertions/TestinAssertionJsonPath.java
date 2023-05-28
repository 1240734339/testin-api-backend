package cn.testin.pojo.vo.request.element.assertions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

@EqualsAndHashCode(callSuper = true)
@Data
public class TestinAssertionJsonPath extends TestinAssertionType {
    private String expect;
    private String expression;
    private String description;
    private String option = "REGEX";

    public TestinAssertionJsonPath() {
        setType(TestinAssertionType.JSON_PATH);
    }

    public boolean isValid() {
        return StringUtils.isNotBlank(expression) && isEnable();
    }
}
