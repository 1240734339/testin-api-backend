package cn.testin.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TestReportDTO implements Serializable {
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

    private static final long serialVersionUID = 1L;

}