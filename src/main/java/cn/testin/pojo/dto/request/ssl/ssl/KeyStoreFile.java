package cn.testin.pojo.dto.request.ssl.ssl;

import cn.testin.pojo.dto.request.BodyFile;
import lombok.Data;

@Data
public class KeyStoreFile {
    private String id;
    private String name;
    private String type;
    private String updateTime;
    private String password;
    private BodyFile file;

}
