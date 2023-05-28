package cn.testin.pojo.domain;

import java.io.Serializable;
import lombok.Data;

@Data
public class TestTaskDo implements Serializable {
    private String id;

    private String projectId;

    private String createUserId;

    private String updateUserId;

    private Long createTime;

    private Long updateTime;

    private String name;

    private String state;

    private String lastResult;

    private Integer taskNumber;

    private static final long serialVersionUID = 1L;
}