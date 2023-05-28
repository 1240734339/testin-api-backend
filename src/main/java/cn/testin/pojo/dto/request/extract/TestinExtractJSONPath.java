package cn.testin.pojo.dto.request.extract;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TestinExtractJSONPath extends TestinExtractCommon {
    public TestinExtractJSONPath() {
        setType(TestinExtractType.JSON_PATH);
    }
}
