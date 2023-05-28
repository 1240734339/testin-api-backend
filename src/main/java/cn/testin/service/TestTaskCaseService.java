package cn.testin.service;

import cn.testin.mapper.TestTaskCaseDoMapper;
import cn.testin.pojo.domain.TestTaskCaseDo;
import cn.testin.pojo.domain.TestTaskCaseDoExample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author WangG
 * @title: TestTaskCaseService
 * @description: 任务用例服务
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class TestTaskCaseService {

    @Resource
    private TestTaskCaseDoMapper taskCaseDoMapper;

    /**
     * @param taskId
     * @return java.util.List<cn.testin.pojo.domain.TestTaskCaseDo>
     * @Description 根据任务id查询用例列表
     **/
    public List<TestTaskCaseDo> getTaskCaseList(String taskId) {
        TestTaskCaseDoExample example = new TestTaskCaseDoExample();
        example.createCriteria().andTaskIdEqualTo(taskId);
        List<TestTaskCaseDo> caseList = taskCaseDoMapper.selectByExample(example);
        return caseList;
    }
}
