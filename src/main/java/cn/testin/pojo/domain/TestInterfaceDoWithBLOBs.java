package cn.testin.pojo.domain;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TestInterfaceDoWithBLOBs extends TestInterfaceDo implements Serializable {
    private String description;

    private String requestParam;

    private static final long serialVersionUID = 1L;
}