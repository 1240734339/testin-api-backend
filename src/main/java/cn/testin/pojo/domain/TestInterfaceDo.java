package cn.testin.pojo.domain;

import java.io.Serializable;
import lombok.Data;

@Data
public class TestInterfaceDo implements Serializable {
    private String id;

    private String projectId;

    private String name;

    private String method;

    private String protocol;

    private String path;

    private String environmentId;

    private String status;

    private String modulePath;

    private String moduleId;

    private String createUserId;

    private String updateUserId;

    private String tags;

    private Long createTime;

    private Long updateTime;

    private Integer interfaceNumber;

    private static final long serialVersionUID = 1L;
}