package cn.testin.pojo.domain;

import java.io.Serializable;
import lombok.Data;

@Data
public class TestScriptDo implements Serializable {
    private String id;

    private String projectId;

    private String name;

    private String interfaceId;

    private String protocol;

    private String status;

    private String path;

    private String method;

    private String createUserId;

    private String updateUserId;

    private Long createTime;

    private Long updateTime;

    private String tags;

    private Integer scriptNumber;

    private static final long serialVersionUID = 1L;
}