package cn.testin.pojo.domain;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TestCaseDoWithBLOBs extends TestCaseDo implements Serializable {
    private String caseStep;

    private String description;

    private static final long serialVersionUID = 1L;
}