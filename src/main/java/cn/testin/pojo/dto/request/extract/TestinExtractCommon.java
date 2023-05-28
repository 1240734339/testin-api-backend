package cn.testin.pojo.dto.request.extract;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

@EqualsAndHashCode(callSuper = true)
@Data
public class TestinExtractCommon extends TestinExtractType {
    private String variable;
    private String value;
    private String expression;
    private String description;
    private boolean multipleMatching;

    public boolean isValid() {
        return StringUtils.isNotBlank(variable) && StringUtils.isNotBlank(expression);
    }
}
