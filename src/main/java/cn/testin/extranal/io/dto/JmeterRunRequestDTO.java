package cn.testin.extranal.io.dto;


import cn.testin.extranal.io.constants.RunModeConstants;
import cn.testin.extranal.io.vo.BooleanPool;
import lombok.Data;
import org.apache.jorphan.collections.HashTree;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class JmeterRunRequestDTO {

    /**
     * 脚本/用例/任务Id
     */
    private String testId;

    /**
     * 脚本/用例/任务名称
     */
    private String name;

    /**
     * 执行模式/API/SCENARIO/PLAN_API等
     */
    private String runMode;

    /**
     * 报告ID
     */
    private String reportId;


    /**
     * 执行脚本
     */
    private HashTree hashTree;

    /**
     * 扩展参数
     */
    private Map<String,Object> extendParams = new LinkedHashMap();


    public JmeterRunRequestDTO() {
    }


    public JmeterRunRequestDTO(String testId,String reportId, HashTree hashTree, String name,String runMode) {
        this.testId = testId;
        this.reportId = reportId;
        this.hashTree = hashTree;
        this.name = name;
        this.runMode = runMode;
    }
}
