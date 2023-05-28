package cn.testin.service;

import cn.testin.commons.constants.TestReportConstants;
import cn.testin.commons.exception.TIException;
import cn.testin.commons.utils.BeanUtils;
import cn.testin.commons.utils.UUidUitl;
import cn.testin.mapper.TestReportDoMapper;
import cn.testin.pojo.domain.*;
import cn.testin.pojo.dto.TestReportDTO;
import cn.testin.pojo.dto.TestReportDetailDTO;
import cn.testin.pojo.dto.TestTaskRelReportDTO;
import cn.testin.pojo.vo.RunDefinitionRequest;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author WangG
 * @title: TestReportService
 * @description: 报告服务
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class TestReportService {

    @Resource
    private TestReportDoMapper reportDoMapper;

    @Resource
    private TestReportDetailService reportDetailService;

    @Resource
    private TestTaskRelReportService taskRelReportService;

    @Resource
    private TestTaskCaseService taskCaseService;


    /**
     * @param request
     * @return
     * @Description 初始化报告记录
     **/
    public String initReportInfo(RunDefinitionRequest request) {
        //todo 调试只在脚本里调试(需要改动一下逻辑)
        // 新增报告表
        String reportId = initReport(request);
        List<TestTaskCaseDo> taskCaseList = new ArrayList<>();
        if (TestReportConstants.TASK.getType().equals(request.getType())) {
            //初始化任务和报告关系表
            initTaskRelReport(request, reportId);
            //todo 思考caseId是从前台传入还是自己查询
            taskCaseList = taskCaseService.getTaskCaseList(request.getId());
            if (CollectionUtils.isEmpty(taskCaseList)) {
                log.error("taskId:{}对应的用例列表为空~", request.getId());
                throw new TIException("任务用例列表为空，请先增加用例");
            }
            List<TestReportDetailDo> repoDetailList = taskCaseList.stream()
                    .map(taskCase -> buildTestReportDetailDo(taskCase.getCaseId(), reportId))
                    .collect(Collectors.toList());
            //新增报告详情表
            reportDetailService.addBatchSelective(repoDetailList);
        }

        if (TestReportConstants.CASE.getType().equals(request.getType())) {
            TestReportDetailDo testReportDetailDo = buildTestReportDetailDo(request.getId(), reportId);
            //新增报告详情表
            reportDetailService.addSingleSelective(testReportDetailDo);
        }
        if (TestReportConstants.SCRIPT.getType().equals(request.getType())) {
            // 步骤调试暂定报告左侧展示的名称是步骤名称,脚本调试还没有和用例进行关联
            TestReportDetailDo testReportDetailDo = buildTestReportDetailDo(request.getId(), reportId);
            //新增报告详情表
            reportDetailService.addSingleSelective(testReportDetailDo);
        }

        return reportId;
    }

    /**
     * @param request
     * @return void
     * @Description 初始化任务和报告关系表
     **/
    private void initTaskRelReport(RunDefinitionRequest request, String reportId) {
        if (StringUtils.isBlank(request.getId())) {
            throw new TIException("类型为task时，taskId不能为空");
        }
        TestTaskRelReportDTO dto = new TestTaskRelReportDTO();
        dto.setReportId(reportId);
        dto.setTaskId(request.getId());
        TestTaskRelReportDo testTaskRelReportDo = new TestTaskRelReportDo();
        BeanUtils.copyBean(testTaskRelReportDo, dto);
        taskRelReportService.addTaskRelReport(testTaskRelReportDo);
    }


    /**
     * @param request
     * @return java.lang.String
     * @Description 新增报告表
     **/
    @NotNull
    private String initReport(RunDefinitionRequest request) {
        TestReportDTO testReportDTO = new TestReportDTO();
        String reportId = UUidUitl.randomUUID();
        testReportDTO.setId(reportId);
        testReportDTO.setName(request.getName());
        testReportDTO.setProjectId(request.getProjectId());
        testReportDTO.setCaseFailCount(0);
        testReportDTO.setCaseTotalCount(1);
        testReportDTO.setCaseSuccessCount(0);
        testReportDTO.setStatus(TestReportConstants.NOT_STARTED.getNum());
        testReportDTO.setTakeTime(0L);
        testReportDTO.setPassRate("0%");
        if (TestReportConstants.CASE.getType().equals(request.getType())) {
            testReportDTO.setType(TestReportConstants.CASE.getNum());
        } else if (TestReportConstants.TASK.getType().equals(request.getType())) {
            testReportDTO.setType(TestReportConstants.TASK.getNum());
        } else if (TestReportConstants.SCRIPT.getType().equals(request.getType())) {
            testReportDTO.setType(TestReportConstants.SCRIPT.getNum());
        } else {
            throw new TIException("暂未处理的报告类型：" + request.getType());
        }
        TestReportDo testReportDo = new TestReportDo();
        BeanUtils.copyBean(testReportDo, testReportDTO);
        testReportDo.setCreateTime(System.currentTimeMillis());
        testReportDo.setUpdateTime(System.currentTimeMillis());
        // 新增报告表
        int count = reportDoMapper.insertSelective(testReportDo);
        if (count != 1) {
            throw new TIException("报告记录表新增失败，新增数据信息:" + new Gson().toJson(testReportDo));
        }
        return reportId;
    }

    private TestReportDetailDo buildTestReportDetailDo(String caseId, String reportId) {
        TestReportDetailDTO testReportDetailDTO = new TestReportDetailDTO();
        testReportDetailDTO.setCaseId(caseId);
        testReportDetailDTO.setReportId(reportId);
        testReportDetailDTO.setContent("");
        TestReportDetailDo testReportDetailDo = new TestReportDetailDo();
        BeanUtils.copyBean(testReportDetailDo, testReportDetailDTO);
        testReportDetailDo.setCreateTime(System.currentTimeMillis());
        testReportDetailDo.setUpdateTime(System.currentTimeMillis());
        return testReportDetailDo;
    }


    /**
     * @param reportId
     * @return void
     * @Description
     **/
    public TestReportDo findByReportId(String reportId) {
        TestReportDo testReportDo = reportDoMapper.selectByPrimaryKey(reportId);
        return testReportDo;
    }


    public void editReportByReportId(TestReportDo reportDo) {
        int count = reportDoMapper.updateByPrimaryKeySelective(reportDo);
        if (count == 0){
            throw new TIException("更新报告表失败~,reportId:"+reportDo.getId());
        }
    }
}
