package cn.testin.service;

import cn.testin.commons.exception.TIException;
import cn.testin.commons.utils.JacksonUtil;
import cn.testin.commons.utils.LogUtil;
import cn.testin.mapper.TestReportDetailDoMapper;
import cn.testin.pojo.domain.TestReportDetailDo;
import cn.testin.pojo.domain.TestReportDetailDoExample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author WangG
 * @title: TestReportDetailService
 * @description: 报告详情
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class TestReportDetailService {

    @Resource
    private TestReportDetailDoMapper reportDetailMapper;

    /**
     * @param repoDetailList
     * @return boolean
     * @Description 批量新增
     **/
    public void addBatchSelective(List<TestReportDetailDo> repoDetailList) {
        int count = reportDetailMapper.insertBatchSelective(repoDetailList);
        if (count == 0) {
            log.error("报告详情批量新增失败, repoDetailList:" + JacksonUtil.toJSONString(repoDetailList));
            throw new TIException("reportDetail insertBatchSelective fail...");
        }
        return;
    }

    /**
     * @param testReportDetailDo
     * @return void
     * @Description 新增单条数据
     **/
    public void addSingleSelective(TestReportDetailDo testReportDetailDo) {
        int count = reportDetailMapper.insertSelective(testReportDetailDo);
        if (count != 1) {
            log.error("报告详情单条新增失败, repoDetailList:" + JacksonUtil.toJSONString(testReportDetailDo));
            throw new TIException("reportDetail addSingleSelective fail...");
        }

    }

    /**
     * @param testReportDetailDo
     * @param reportDetailExample
     * @return void
     * @Description 通过条件更新
     **/
    public void editReportDetailByExample(TestReportDetailDo testReportDetailDo, TestReportDetailDoExample reportDetailExample) {
        int count = reportDetailMapper.updateByExampleSelective(testReportDetailDo, reportDetailExample);
        if (count == 0){
            throw new TIException("通过条件：更新reportReportDetail失败");
        }
        LogUtil.info("更新reportReportDetail成功");
    }
}
