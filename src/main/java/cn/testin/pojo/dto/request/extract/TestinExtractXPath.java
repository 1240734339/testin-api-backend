package cn.testin.pojo.dto.request.extract;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TestinExtractXPath extends TestinExtractCommon {
    public TestinExtractXPath() {
        setType(TestinExtractType.XPATH);
    }
}
