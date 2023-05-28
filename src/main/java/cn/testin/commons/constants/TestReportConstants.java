package cn.testin.commons.constants;


/**
 * @author WangG
 * @title: TestReportConstants
 * @description: 报告使用的常量
 */

public enum TestReportConstants {
    // 执行状态
    NOT_STARTED(0,"未开始"),
    EXECUTING(1,"执行中"),
    COMPLETE(2,"已完成"),

    // 报告类型
    SCRIPT(0,"script","脚本"),
    CASE(1,"case","测试用例"),
    TASK(2,"task","测试任务")
    ;
    private Integer num;

    private String type;

    private String desc;

    TestReportConstants(Integer num, String desc) {
        this.num = num;
        this.desc = desc;
    }

    TestReportConstants(Integer num, String type , String desc) {
        this.num = num;
        this.type = type;
        this.desc = desc;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.desc = type;
    }
}
