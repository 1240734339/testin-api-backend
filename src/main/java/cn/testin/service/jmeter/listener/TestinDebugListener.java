package cn.testin.service.jmeter.listener;

import cn.testin.commons.constants.TestReportConstants;
import cn.testin.commons.utils.CommonBeanFactory;
import cn.testin.commons.utils.LogUtil;
import cn.testin.config.exec.PoolExecBlockingQueueUtil;
import cn.testin.extranal.io.dto.RequestResult;
import cn.testin.extranal.io.dto.ResponseResult;
import cn.testin.extranal.io.jmeter.JMeterBase;
import cn.testin.extranal.io.utils.JMeterVars;
import cn.testin.extranal.io.utils.ListenerUtil;
import cn.testin.pojo.domain.TestReportDetailDo;
import cn.testin.pojo.domain.TestReportDetailDoExample;
import cn.testin.pojo.domain.TestReportDo;
import cn.testin.service.TestReportDetailService;
import cn.testin.service.TestReportService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.jmeter.engine.util.NoThreadClone;
import org.apache.jmeter.protocol.jdbc.AbstractJDBCTestElement;
import org.apache.jmeter.reporters.AbstractListenerElement;
import org.apache.jmeter.samplers.*;
import org.apache.jmeter.testelement.TestStateListener;
import org.apache.jmeter.testelement.property.BooleanProperty;
import org.apache.jmeter.threads.JMeterVariables;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @title: TestinDebugListener
 * @description: 调试结果监听
 */
public class TestinDebugListener extends AbstractListenerElement implements SampleListener, Clearable, Serializable,
        TestStateListener, Remoteable, NoThreadClone  {

    private static final String ERROR_LOGGING = "TestinResultCollector.error_logging"; // $NON-NLS-1$

    private static final String SUCCESS_ONLY_LOGGING = "TestinResultCollector.success_only_logging"; // $NON-NLS-1$

    private static final String TEST_IS_LOCAL = "*local*"; // $NON-NLS-1$

    public static final String TEST_END = "Testin_TEST_END";

    @Override
    public Object clone() {
        TestinDebugListener clone = (TestinDebugListener) super.clone();
        return clone;
    }

    public boolean isErrorLogging() {
        return getPropertyAsBoolean(ERROR_LOGGING);
    }

    public final void setSuccessOnlyLogging(boolean value) {
        if (value) {
            setProperty(new BooleanProperty(SUCCESS_ONLY_LOGGING, true));
        } else {
            removeProperty(SUCCESS_ONLY_LOGGING);
        }
    }

    /**
     * Get the state of successful only logging
     *
     * @return Flag whether only successful samples should be logged
     */
    public boolean isSuccessOnlyLogging() {
        return getPropertyAsBoolean(SUCCESS_ONLY_LOGGING, false);
    }

    public boolean isSampleWanted(boolean success, SampleResult result) {
        boolean errorOnly = isErrorLogging();
        boolean successOnly = isSuccessOnlyLogging();
        return isSampleWanted(success, errorOnly, successOnly) && !StringUtils.containsIgnoreCase(result.getSampleLabel(), "TEST_CLEAR_LOOPS_VAR_");
    }

    public static boolean isSampleWanted(boolean success, boolean errorOnly,
                                         boolean successOnly) {
        return (!errorOnly && !successOnly) ||
                (success && successOnly) ||
                (!success && errorOnly);
    }

    @Override
    public void testEnded(String host) {
        LogUtil.info("Debug TestEnded " + this.getName());
        PoolExecBlockingQueueUtil.offer(this.getName());
    }

    @Override
    public void testStarted(String host) {
        LogUtil.debug("TestStarted " + this.getName());
    }

    @Override
    public void testEnded() {
        testEnded(TEST_IS_LOCAL);
    }

    @Override
    public void testStarted() {
        testStarted(TEST_IS_LOCAL);
    }

    @Override
    public void sampleStarted(SampleEvent e) {
    }

    @Override
    public void sampleStopped(SampleEvent e) {
        System.out.println("执行一次");
    }

    @Override
    public void sampleOccurred(SampleEvent event) {
        SampleResult result = event.getResult();
        this.setVars(result);
        RequestResult requestResult = JMeterBase.getRequestResult(result);
        // 要排除RUNNING_DEBUG_SAMPLER_NAME 否则会造成多次更新数据库的情况
        if (requestResult != null && !StringUtils.equalsAny(result.getLabel(), ListenerUtil.RUNNING_DEBUG_SAMPLER_NAME)) {
            List<String> logDetail = result.getLogDetail();
            //只有调试部分的逻辑走debug
            String reportId = this.getProperty("reportId").getStringValue();
            String scriptId = this.getProperty("testId").getStringValue();
            //查询报告表
            TestReportService testReportService = CommonBeanFactory.getBean(TestReportService.class);
            TestReportDo reportDo = new TestReportDo();
            // 响应的结果
            ResponseResult responseResult = requestResult.getResponseResult();

            // 更改的内容
            reportDo.setId(reportId);
            if (requestResult.isSuccess()) {
                reportDo.setCaseSuccessCount(1);
                reportDo.setCaseFailCount(0);
                reportDo.setPassRate("100%");
            }
            reportDo.setCaseSuccessCount(0);
            reportDo.setCaseFailCount(1);
            reportDo.setCaseTotalCount(1);
            reportDo.setStatus(TestReportConstants.COMPLETE.getNum());
            reportDo.setTakeTime(result.getElapsedTime());

            reportDo.setUpdateTime(System.currentTimeMillis());
            testReportService.editReportByReportId(reportDo);
            LogUtil.info("debug调试更新报告表成功~，reportId:" + reportId);
            // 查询报告详情表
            TestReportDetailService reportDetailService = CommonBeanFactory.getBean(TestReportDetailService.class);
            //构造content的具体信息
//            执行结果 耗时 输入数据 返回数据 期望结果 开始时间 结束时间 错误信息:1.响应 2.响应结果
            org.json.JSONObject content = new org.json.JSONObject();
            content.put("exeResult", requestResult.isSuccess() ? "成功" : "失败");
            content.put("takeTime", result.getElapsedTime() + "ms");
            content.put("inputData", StringUtils.isNotBlank(requestResult.getBody()) ? requestResult.getBody() : "");
            content.put("outputData", StringUtils.isNotBlank(responseResult.getBody()) ? responseResult.getBody() : "");
            String expectResult = "";
            if (responseResult.getAssertions().size() != 0) {
                expectResult = responseResult.getAssertions().stream()
                        .map(assertion ->
                                StringUtils.isBlank(assertion.getContent()) ? "" : assertion.getContent()
                        ).collect(Collectors.joining(","));
            }
            content.put("expectResult", expectResult);

            Date date = new Date();
            date.setTime(requestResult.getStartTime());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
            String startfmt = sdf.format(date);
            content.put("statTime", startfmt);

            date.setTime(requestResult.getEndTime());
            String endfmt = sdf.format(date);
            content.put("endTime", endfmt);

            String errorMsg = "";
            if (!result.isSuccess()){
                //判断断言
                if (requestResult.getTotalAssertions() != 0 &&
                        (requestResult.getPassAssertions() != requestResult.getTotalAssertions())){
                    errorMsg = responseResult.getAssertions().stream().filter(assertion -> !assertion.isPass())
                            .map(assertion -> StringUtils.isBlank(assertion.getMessage()) ? "" : assertion.getMessage())
                            .collect(Collectors.joining("|"));
                }
                if (!StringUtils.equals(responseResult.getResponseCode(),"200")){
                    errorMsg = responseResult.getResponseMessage();
                }
            }
            content.put("erroMsg", errorMsg);
            content.put("logDetail",logDetail);
            TestReportDetailDo testReportDetailDo = new TestReportDetailDo();
            testReportDetailDo.setUpdateTime(System.currentTimeMillis());
            testReportDetailDo.setContent(content.toString());
            TestReportDetailDoExample reportDetailExample = new TestReportDetailDoExample();
            // 单步骤调试直接caseId看作是脚本Id
            reportDetailExample.createCriteria().andReportIdEqualTo(reportId).andCaseIdEqualTo(scriptId);
            LogUtil.info("开始通过条件reportId:{},scriptId:{}更新报告详情表~",reportId,scriptId);
            reportDetailService.editReportDetailByExample(testReportDetailDo,reportDetailExample);
            LogUtil.info("DebugListener监听jmeter响应结果:\n" + JSONObject.toJSONString(requestResult.getResponseResult()));
        }

    }

    private void setVars(SampleResult result) {
        if (StringUtils.isNotEmpty(result.getSampleLabel()) && result.getSampleLabel().startsWith("Transaction=")) {
            for (int i = 0; i < result.getSubResults().length; i++) {
                SampleResult subResult = result.getSubResults()[i];
                this.setVars(subResult);
            }
        }
        JMeterVariables variables = JMeterVars.get(result.getResourceId());
        if (variables != null && CollectionUtils.isNotEmpty(variables.entrySet())) {
            StringBuilder builder = new StringBuilder();
            for (Map.Entry<String, Object> entry : variables.entrySet()) {
                builder.append(entry.getKey()).append("：").append(entry.getValue()).append("\n");
            }
            if (StringUtils.isNotEmpty(builder)) {
                result.setExtVars(builder.toString());
            }
        }
    }

    @Override
    public void clearData() {
    }
}
