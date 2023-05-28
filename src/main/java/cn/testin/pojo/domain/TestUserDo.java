package cn.testin.pojo.domain;

import java.io.Serializable;
import lombok.Data;

@Data
public class TestUserDo implements Serializable {
    private String id;

    private String name;

    private String password;

    private String status;

    private Long createTime;

    private Long updateTime;

    private static final long serialVersionUID = 1L;
}