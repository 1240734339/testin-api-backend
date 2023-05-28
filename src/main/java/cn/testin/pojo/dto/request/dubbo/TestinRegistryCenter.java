package cn.testin.pojo.dto.request.dubbo;

import lombok.Data;

@Data
public class TestinRegistryCenter {
    private String protocol;
    private String group;
    private String username;
    private String address;
    private String password;
    private String timeout;
}
