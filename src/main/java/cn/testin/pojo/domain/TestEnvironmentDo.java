package cn.testin.pojo.domain;

import java.io.Serializable;
import lombok.Data;

@Data
public class TestEnvironmentDo implements Serializable {
    private String id;

    private String name;

    private String projectId;

    private Long createTime;

    private Long updateTime;

    private String description;

    private String domain;

    private String config;

    private static final long serialVersionUID = 1L;
}