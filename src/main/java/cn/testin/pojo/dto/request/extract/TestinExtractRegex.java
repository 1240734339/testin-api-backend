package cn.testin.pojo.dto.request.extract;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TestinExtractRegex extends TestinExtractCommon {
    private String useHeaders;

    public TestinExtractRegex() {
        setType(TestinExtractType.REGEX);
    }
}
