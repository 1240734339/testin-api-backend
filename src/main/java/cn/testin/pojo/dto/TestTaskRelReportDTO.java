package cn.testin.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TestTaskRelReportDTO implements Serializable {

    private String taskId;

    private String reportId;

    private static final long serialVersionUID = 1L;
}