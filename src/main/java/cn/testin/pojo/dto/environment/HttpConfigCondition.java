package cn.testin.pojo.dto.environment;

import cn.testin.pojo.dto.request.KeyValue;
import lombok.Data;

import java.util.List;

@Data
public class HttpConfigCondition {
    private String type;
    private List<KeyValue> details;
    private String protocol;
    private String socket;
    private String domain;
    private int port;
    private List<KeyValue> headers;

    private List<String> moduleIds;
}
