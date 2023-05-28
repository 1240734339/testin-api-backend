package cn.testin.pojo.vo.request.element.assertions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

@EqualsAndHashCode(callSuper = true)
@Data
public class TestinAssertionRegex extends TestinAssertionType {
    private String subject;
    private String expression;
    private String description;
    private boolean assumeSuccess;
    private int testType = 2;

    public TestinAssertionRegex() {
        setType(TestinAssertionType.REGEX);
    }

    public boolean isValid() {
        return StringUtils.isNotBlank(subject) && StringUtils.isNotBlank(expression) && isEnable();
    }
}
