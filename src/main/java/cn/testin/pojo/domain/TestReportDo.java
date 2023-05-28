package cn.testin.pojo.domain;

import java.io.Serializable;
import lombok.Data;

@Data
public class TestReportDo implements Serializable {
    private String id;

    private String projectId;

    private String name;

    private Integer type;

    private Integer caseTotalCount;

    private Integer caseSuccessCount;

    private Integer caseFailCount;

    private Integer status;

    private Long takeTime;

    private String passRate;

    private Long createTime;

    private Long updateTime;

    private static final long serialVersionUID = 1L;
}