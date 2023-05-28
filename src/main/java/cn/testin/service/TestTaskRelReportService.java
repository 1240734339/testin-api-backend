package cn.testin.service;

import cn.testin.commons.exception.TIException;
import cn.testin.mapper.TestTaskRelReportDoMapper;
import cn.testin.pojo.domain.TestTaskRelReportDo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author WangG
 * @title: TestReportRelTaskService
 * @description: 任务和报告的关系表
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class TestTaskRelReportService {

    @Resource
    private TestTaskRelReportDoMapper taskRelReportDoMapper;

    /**
     * @param
     * @return cn.testin.pojo.domain.TestTaskRelReportDo
     * @Description 根据报告Id查询任务
     **/
    public TestTaskRelReportDo findTaskRelReportDoByReportId(String reportId) {
        TestTaskRelReportDo testTaskRelReportDo = taskRelReportDoMapper.selectByReportId(reportId);
        return testTaskRelReportDo;

    }

    /**
     * @param testTaskRelReportDo
     * @return boolean
     * @Description 新增任务和报告中间关系记录
     **/
    public boolean addTaskRelReport(TestTaskRelReportDo testTaskRelReportDo) {
        int count = taskRelReportDoMapper.insert(testTaskRelReportDo);
        if (count != 1) {
            throw new TIException("新增任务报告关系表失败~,taskId:" + testTaskRelReportDo.getTaskId()+
                    "  reportId:"+ testTaskRelReportDo.getReportId());
        }
        return true;
    }
}
