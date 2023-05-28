package cn.testin.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TestReportDetailDTO implements Serializable {

    private String reportId;

    private String caseId;

    private String content;

    private static final long serialVersionUID = 1L;
}