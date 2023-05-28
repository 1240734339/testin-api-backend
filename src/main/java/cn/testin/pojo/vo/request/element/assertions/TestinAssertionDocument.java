package cn.testin.pojo.vo.request.element.assertions;

import lombok.Data;

@Data
public class TestinAssertionDocument {
    private boolean enable = true;
    private String type;
    private Document data;
}
