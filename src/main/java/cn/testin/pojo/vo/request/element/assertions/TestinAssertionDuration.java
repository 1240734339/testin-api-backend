package cn.testin.pojo.vo.request.element.assertions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TestinAssertionDuration extends TestinAssertionType {
    private long value;

    public TestinAssertionDuration() {
        setType(TestinAssertionType.DURATION);
    }

    public boolean isValid() {
        return value > 0 && isEnable();
    }
}
