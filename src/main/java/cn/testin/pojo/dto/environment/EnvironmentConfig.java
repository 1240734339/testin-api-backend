package cn.testin.pojo.dto.environment;

import cn.testin.pojo.dto.request.TCPConfig;
import cn.testin.pojo.dto.request.ssl.ssl.KeyStoreConfig;
import cn.testin.pojo.vo.request.element.TestinAssertions;
import cn.testin.pojo.vo.request.element.TestinJSR223PostProcessor;
import cn.testin.pojo.vo.request.element.TestinJSR223PreProcessor;
import cn.testin.pojo.vo.request.element.TestinJSR223Processor;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @title: EnvironmentConfig
 * @description:
 */
@Data
public class EnvironmentConfig {

    private String apiEnvironmentid;
    private CommonConfig commonConfig;
    private List<HttpConfig> httpConfigs;
    private List<DatabaseConfig> databaseConfigs;
    private TCPConfig tcpConfig;
    private KeyStoreConfig sslConfig;
    //全局前后置脚本（每个请求都跑一遍）
    private TestinJSR223PreProcessor preProcessor;
    private TestinJSR223PostProcessor postProcessor;
    //全局前后置脚本步骤（只在全部步骤都前后做处理）
    private TestinJSR223Processor preStepProcessor;
    private TestinJSR223Processor postStepProcessor;
    //全局前后置脚本都配置
    private GlobalScriptConfig globalScriptConfig;
    private JSONObject authManager;
    private List<TestinAssertions> assertions;
    private boolean useErrorCode;
    private boolean higherThanSuccess;
    private boolean higherThanError;

    public EnvironmentConfig() {
        this.commonConfig = new CommonConfig();
        this.httpConfigs = new ArrayList<>();
        this.databaseConfigs = new ArrayList<>();
        this.tcpConfig = new TCPConfig();
        this.sslConfig = new KeyStoreConfig();
    }
}
