package cn.testin.pojo.vo.request.element;

import cn.testin.pojo.dto.environment.EnvironmentConfig;
import cn.testin.pojo.dto.environment.Host;
import cn.testin.pojo.dto.request.http.HttpConfig;
import cn.testin.pojo.dto.request.variable.ScenarioVariable;
import cn.testin.pojo.vo.request.base.TestinElement;
import cn.testin.pojo.dto.environment.ParameterConfig;
import com.alibaba.fastjson.annotation.JSONType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.http.control.DNSCacheManager;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jorphan.collections.HashTree;

import java.util.ArrayList;
import java.util.List;

/**
 * @title: TestinDNSCacheManager
 * @description:
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JSONType(typeName = "DNSCacheManager")
public class TestinDNSCacheManager extends TestinElement {

    private String clazzName = TestinDNSCacheManager.class.getCanonicalName();

    @Override
    public void toHashTree(HashTree tree, List<TestinElement> hashTree, ParameterConfig config) {
        // 非导出操作，且不是启用状态则跳过执行
        if (!this.isEnable()) {
            return;
        }
        for (TestinElement el : hashTree) {
            el.toHashTree(tree, el.getHashTree(), config);
        }
    }

    public static void addEnvironmentVariables(HashTree samplerHashTree, String name, EnvironmentConfig config) {
        name += "Environment Variables";
        if (CollectionUtils.isNotEmpty(config.getCommonConfig().getVariables())) {
            samplerHashTree.add(arguments(name, config.getCommonConfig().getVariables()));
        }
    }

    public static void addEnvironmentDNS(HashTree samplerHashTree, String name, EnvironmentConfig config, HttpConfig httpConfig) {
        if (config.getCommonConfig().isEnableHost() && CollectionUtils.isNotEmpty(config.getCommonConfig().getHosts()) && httpConfig != null && httpConfig.getDomain() != null) {
            String domain = httpConfig.getDomain().trim();
            List<Host> hosts = new ArrayList<>();
            config.getCommonConfig().getHosts().forEach(host -> {
                if (StringUtils.isNotBlank(host.getDomain())) {
                    String hostDomain = host.getDomain().trim().replace("http://", "").replace("https://", "");
                    if (StringUtils.equals(hostDomain, domain)) {
                        host.setDomain(hostDomain); // 域名去掉协议
                        hosts.add(host);
                    }
                }
            });
            if (CollectionUtils.isNotEmpty(hosts)) {
                samplerHashTree.add(dnsCacheManager(name + " DNSCacheManager", hosts));
            }
        }
    }

    private static Arguments arguments(String name, List<ScenarioVariable> variables) {
        Arguments arguments = new Arguments();
        arguments.setEnabled(true);
        arguments.setName(name);
        arguments.setProperty(TestElement.TEST_CLASS, Arguments.class.getName());
        arguments.setProperty(TestElement.GUI_CLASS, SaveService.aliasToClass("ArgumentsPanel"));
        variables.stream().filter(ScenarioVariable::isConstantValid).filter(ScenarioVariable::isEnable).forEach(ScenarioVariable ->
                arguments.addArgument(ScenarioVariable.getName(), ScenarioVariable.getValue(), "=")
        );
        return arguments;
    }

    private static DNSCacheManager dnsCacheManager(String name, List<Host> hosts) {
        DNSCacheManager dnsCacheManager = new DNSCacheManager();
        dnsCacheManager.setEnabled(true);
        dnsCacheManager.setName(name);
        dnsCacheManager.setProperty(TestElement.TEST_CLASS, DNSCacheManager.class.getName());
        dnsCacheManager.setProperty(TestElement.GUI_CLASS, SaveService.aliasToClass("DNSCachePanel"));
        dnsCacheManager.setCustomResolver(false);
        dnsCacheManager.setClearEachIteration(true);
        hosts.forEach(host -> dnsCacheManager.addHost(host.getDomain(), host.getIp()));

        return dnsCacheManager;
    }
}
