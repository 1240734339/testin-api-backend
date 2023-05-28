package cn.testin.pojo.dto.request.dubbo;

import lombok.Data;

@Data
public class TestinConfigCenter {
    private String protocol;
    private String group;
    private String namespace;
    private String username;
    private String address;
    private String password;
    private String timeout;
}
