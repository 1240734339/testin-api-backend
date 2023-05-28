package cn.testin.pojo.dto.request.loop;

import lombok.Data;

@Data
public class TestinWhileController {
    private String variable;
    private String operator;
    private String value;
    private long timeout;
    private Object requestResult;
}
