package cn.testin.service.jmeter;

import cn.testin.commons.constants.ApiRunMode;
import cn.testin.commons.utils.CommonBeanFactory;
import cn.testin.commons.utils.LogUtil;
import cn.testin.config.exec.ExecThreadPoolExecutor;
import cn.testin.config.JmeterProperties;
import cn.testin.extranal.io.constants.BackendListenerConstants;
import cn.testin.extranal.io.constants.RunModeConstants;
import cn.testin.extranal.io.dto.JmeterRunRequestDTO;
import cn.testin.extranal.io.jmeter.JMeterBase;
import cn.testin.extranal.io.jmeter.LocalRunner;
import cn.testin.service.jmeter.listener.TestinApiBackendListener;
import cn.testin.service.jmeter.listener.TestinDebugListener;
import cn.testin.service.jmeter.tools.FixedCapacityUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;

/**
 * @title: JmeterService
 * @description: jmeter服务
 */
@Service
public class JmeterService {

    public static final String BASE_URL = "http://%s:%d";

    @Resource
    private JmeterProperties jmeterProperties;

    @PostConstruct
    private void init() {
        String JMETER_HOME = getJmeterHome();

        String JMETER_PROPERTIES = JMETER_HOME + "/bin/jmeter.properties";
        JMeterUtils.loadJMeterProperties(JMETER_PROPERTIES);
        JMeterUtils.setJMeterHome(JMETER_HOME);
        JMeterUtils.setLocale(LocaleContextHolder.getLocale());
    }

    public String getJmeterHome() {
        String home = getClass().getResource("/").getPath() + "jmeter";
        try {
            File file = new File(home);
            if (file.exists()) {
                return home;
            } else {
                return jmeterProperties.getHome();
            }
        } catch (Exception e) {
            return jmeterProperties.getHome();
        }
    }

    public void run(JmeterRunRequestDTO request) {

        CommonBeanFactory.getBean(ExecThreadPoolExecutor.class).addTask(request);
    }

    public void addQueue(JmeterRunRequestDTO request) {
        this.runLocal(request);
    }

    /**
     * 添加调试监听
     *
     * @param testId
     * @param testPlan
     */
    private void addDebugListener(String reportId,String testId,String name ,HashTree testPlan) {
        TestinDebugListener resultCollector = new TestinDebugListener();
        resultCollector.setName(name);
        resultCollector.setProperty("reportId",reportId);
        resultCollector.setProperty("testId",testId);
        resultCollector.setProperty(TestElement.TEST_CLASS, TestinDebugListener.class.getName());
        resultCollector.setProperty(TestElement.GUI_CLASS, SaveService.aliasToClass("ViewResultsFullVisualizer"));
        resultCollector.setEnabled(true);

        // 添加DEBUG标示
        HashTree test = ArrayUtils.isNotEmpty(testPlan.getArray()) ? testPlan.getTree(testPlan.getArray()[0]) : null;
        if (test != null && ArrayUtils.isNotEmpty(test.getArray()) && test.getArray()[0] instanceof ThreadGroup) {
            ThreadGroup group = (ThreadGroup) test.getArray()[0];
            group.setProperty(BackendListenerConstants.TEST_DEBUG.name(), true);
        }
        testPlan.add(testPlan.getArray()[0], resultCollector);
    }

    private void runLocal(JmeterRunRequestDTO request) {
        // 初始化jmeter配置
        init();
        if (!FixedCapacityUtils.jmeterLogTask.containsKey(request.getReportId())) {
            FixedCapacityUtils.jmeterLogTask.put(request.getReportId(), System.currentTimeMillis());
        }

        LogUtil.debug("监听MessageCache.tasks当前容量：" + FixedCapacityUtils.jmeterLogTask.size());

        if (StringUtils.equals(request.getRunMode(),ApiRunMode.DEBUG.name().toLowerCase())) {
            LogUtil.debug("为请求 [ " + request.getReportId() + " ] 添加Debug Listener");
            addDebugListener(request.getReportId(),request.getTestId(),request.getName() ,request.getHashTree());
        } else {
            LogUtil.debug("为请求 [ " + request.getReportId() + " ] 添加同步接收结果 Listener");
            JMeterBase.addBackendListener(request, request.getHashTree(), TestinApiBackendListener.class.getCanonicalName());
        }

        LogUtil.info("资源：[" + request.getName() + "] 加入JMETER中开始执行", request.getReportId());
        LocalRunner runner = new LocalRunner(request.getHashTree(),request.getTestId());
        runner.run(request.getReportId());
    }
}
