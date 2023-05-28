package cn.testin.pojo.domain;

import java.io.Serializable;
import lombok.Data;

@Data
public class TestCaseStepDo implements Serializable {
    private String id;

    private String projectId;

    private String createUserId;

    private Long createTime;

    private Long updateTime;

    private String name;

    private String caseId;

    private String scriptId;

    private String datasetId;

    private String datasetDetails;

    private Integer stepOrder;

    private byte[] updateUserId;

    private static final long serialVersionUID = 1L;
}