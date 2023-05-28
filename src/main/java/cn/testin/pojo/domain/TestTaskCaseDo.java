package cn.testin.pojo.domain;

import java.io.Serializable;
import lombok.Data;

@Data
public class TestTaskCaseDo implements Serializable {
    private String projectId;

    private String taskId;

    private String caseId;

    private static final long serialVersionUID = 1L;
}