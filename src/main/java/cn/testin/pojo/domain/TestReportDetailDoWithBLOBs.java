package cn.testin.pojo.domain;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TestReportDetailDoWithBLOBs extends TestReportDetailDo implements Serializable {
    private String inputData;

    private String content;

    private String outputData;

    private static final long serialVersionUID = 1L;
}