package cn.testin.pojo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestInterfaceModuleDoDTO extends TreeNodeDTO<TestInterfaceModuleDoDTO> {
    private String protocol;

    private String path;
}
