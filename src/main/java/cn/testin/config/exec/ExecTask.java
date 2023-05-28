package cn.testin.config.exec;

import cn.testin.commons.utils.CommonBeanFactory;
import cn.testin.commons.utils.LogUtil;
import cn.testin.extranal.io.cache.JMeterEngineCache;
import cn.testin.extranal.io.dto.JmeterRunRequestDTO;
import cn.testin.service.jmeter.JmeterService;


public class ExecTask implements Runnable {
    private JmeterRunRequestDTO request;

    public ExecTask(JmeterRunRequestDTO request) {
        this.request = request;
    }

    public JmeterRunRequestDTO getRequest() {
        return this.request;
    }

    @Override
    public void run() {
        CommonBeanFactory.getBean(JmeterService.class).addQueue(request);
        Object res = PoolExecBlockingQueueUtil.take(request.getReportId());
        if (res == null && !JMeterThreadUtils.isRunning(request.getReportId(), request.getTestId())) {
            LogUtil.info("任务执行超时", request.getReportId());
            if (JMeterEngineCache.runningEngine.containsKey(request.getReportId())) {
                JMeterEngineCache.runningEngine.remove(request.getReportId());
            }
        }
    }
}
