package cn.testin.pojo.domain;

import java.io.Serializable;
import lombok.Data;

@Data
public class TestDataSetDo implements Serializable {
    private String id;

    private String projectId;

    private String name;

    private String createUserId;

    private String updateUserId;

    private Long createTime;

    private Long updateTime;

    private String caseModuleId;

    private String description;

    private static final long serialVersionUID = 1L;
}