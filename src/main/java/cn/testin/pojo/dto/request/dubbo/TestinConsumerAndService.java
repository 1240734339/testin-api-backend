package cn.testin.pojo.dto.request.dubbo;

import lombok.Data;

@Data
public class TestinConsumerAndService {
    private String timeout;
    private String version;
    private String retries;
    private String cluster;
    private String group;
    private String connections;
    private String async;
    private String loadBalance;
}
