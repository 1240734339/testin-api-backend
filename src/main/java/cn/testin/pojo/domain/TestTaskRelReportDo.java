package cn.testin.pojo.domain;

import java.io.Serializable;
import lombok.Data;

@Data
public class TestTaskRelReportDo implements Serializable {
    private Integer id;

    private String taskId;

    private String reportId;

    private static final long serialVersionUID = 1L;
}