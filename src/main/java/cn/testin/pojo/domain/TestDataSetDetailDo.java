package cn.testin.pojo.domain;

import java.io.Serializable;
import lombok.Data;

@Data
public class TestDataSetDetailDo implements Serializable {
    private String id;

    private String projectId;

    private String datasetId;

    private Integer dataOrder;

    private String data;

    private static final long serialVersionUID = 1L;
}