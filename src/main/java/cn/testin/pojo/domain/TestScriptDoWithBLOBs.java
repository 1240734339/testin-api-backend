package cn.testin.pojo.domain;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TestScriptDoWithBLOBs extends TestScriptDo implements Serializable {
    private String description;

    private String request;

    private static final long serialVersionUID = 1L;
}