package cn.testin.pojo.vo.request.element.assertions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

@EqualsAndHashCode(callSuper = true)
@Data
public class TestinAssertionXPath2 extends TestinAssertionType {
    private String expression;

    public TestinAssertionXPath2() {
        setType(TestinAssertionType.XPATH2);
    }

    public boolean isValid() {
        return StringUtils.isNotBlank(expression) && isEnable();
    }
}
