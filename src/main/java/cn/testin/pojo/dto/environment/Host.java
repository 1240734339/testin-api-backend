package cn.testin.pojo.dto.environment;

import lombok.Data;

@Data
public class Host {
    private String ip;
    private String domain;
    private String status;
    private String annotation;
    private String uuid;
}
