package cn.testin.pojo.dto.request.loop;

import lombok.Data;

@Data
public class CountController {
    private String loops;
    private long interval;
    private boolean proceed;
    private Object requestResult;

}
