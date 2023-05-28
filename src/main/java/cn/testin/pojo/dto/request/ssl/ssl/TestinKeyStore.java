package cn.testin.pojo.dto.request.ssl.ssl;

import lombok.Data;

@Data
public class TestinKeyStore {
    private String id;
    private String password;
    private String path;

    public TestinKeyStore() {
    }

    public TestinKeyStore(String path, String password) {
        this.password = password;
        this.path = path;
    }
}
