package cn.testin.pojo.domain;

import java.io.Serializable;
import lombok.Data;

@Data
public class TestReportDetailDo implements Serializable {
    private Integer id;

    private String reportId;

    private String caseId;

    private Long createTime;

    private Long updateTime;

    private String content;

    private static final long serialVersionUID = 1L;
}