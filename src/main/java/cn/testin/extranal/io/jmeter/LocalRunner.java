package cn.testin.extranal.io.jmeter;


import cn.testin.commons.utils.LogUtil;
import cn.testin.extranal.io.cache.JMeterEngineCache;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.jmeter.engine.JMeterEngineException;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jorphan.collections.HashTree;

import java.util.List;
import java.util.Properties;

public class LocalRunner {
    private HashTree jmxTree;

    private String requestId;

    public LocalRunner(HashTree jmxTree) {
        this.jmxTree = jmxTree;
    }

    public LocalRunner(HashTree jmxTree,String requestId) {
        this.jmxTree = jmxTree;
        this.requestId = requestId;
    }

    public LocalRunner() {
    }

    public void run(String report) {
        StandardJMeterEngine engine = new StandardJMeterEngine();
        engine.configure(jmxTree);
        try {
            LogUtil.info("LocalRunner 开始执行报告",report);
            engine.runTest();
            JMeterEngineCache.runningEngine.put(report, engine);
        } catch (JMeterEngineException e) {
            engine.stopTest(true);
        }
    }

    public void stop(List<String> reports) {
        if (CollectionUtils.isNotEmpty(reports)) {
            for (String report : reports) {
                StandardJMeterEngine engine = JMeterEngineCache.runningEngine.get(report);
                if (engine != null) {
                    engine.stopTest();
                    JMeterEngineCache.runningEngine.remove(report);
                }
            }
        }
    }

    public void stop(String report) {
        if (StringUtils.isNotEmpty(report)) {
            StandardJMeterEngine engine = JMeterEngineCache.runningEngine.get(report);
            if (engine != null) {
                engine.stopTest();
                JMeterEngineCache.runningEngine.remove(report);
            }
        }
    }
}