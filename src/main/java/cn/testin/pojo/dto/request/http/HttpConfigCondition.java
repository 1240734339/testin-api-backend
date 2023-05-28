package cn.testin.pojo.dto.request.http;

import cn.testin.pojo.dto.request.KeyValue;
import lombok.Data;

import java.util.List;

/**
 * @title: HttpConfigCondition
 * @description:
 */
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
