package cn.testin.pojo.vo.request.element.assertions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

@EqualsAndHashCode(callSuper = true)
@Data
public class TestinAssertionJSR223 extends TestinAssertionType {
    private String variable;
    private String operator;
    private String value;
    private String desc;
    private String name;
    private String script;
    private String scriptLanguage;

    public TestinAssertionJSR223() {
        setType(TestinAssertionType.JSR223);
    }

    public boolean isValid() {
        return StringUtils.isNotBlank(script) && StringUtils.isNotBlank(scriptLanguage) && isEnable();
    }
}
