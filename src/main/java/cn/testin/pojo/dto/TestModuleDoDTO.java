package cn.testin.pojo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestModuleDoDTO extends TreeNodeDTO<TestModuleDoDTO>{
    private String protocol;

    private String path;
}
