package cn.testin.pojo.domain;

import java.io.Serializable;
import lombok.Data;

@Data
public class TestCaseDo implements Serializable {
    private String id;

    private String projectId;

    private String caseModuleId;

    private String modulePath;

    private String name;

    private String tag;

    private String type;

    private String priority;

    private String status;

    private String createUserId;

    private String updateUserId;

    private Long createTime;

    private Long updateTime;

    private String lastResult;

    private Integer caseNumber;

    private static final long serialVersionUID = 1L;
}