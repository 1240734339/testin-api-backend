package cn.testin.pojo.vo.request.element;

import cn.testin.pojo.dto.request.dubbo.TestinConfigCenter;
import cn.testin.pojo.dto.request.dubbo.TestinConsumerAndService;
import cn.testin.pojo.dto.request.dubbo.TestinRegistryCenter;
import cn.testin.pojo.dto.request.KeyValue;
import cn.testin.pojo.vo.request.base.TestinElement;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author WangG
 * @title: TestinDubboSampler
 * @description:
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JSONType(typeName = "DubboSampler")
public class TestinDubboSampler extends TestinElement {

    private String clazzName = TestinDubboSampler.class.getCanonicalName();

    /**
     * type 必须放最前面，以便能够转换正确的类
     */
    private String type = "DubboSampler";

    @JSONField(ordinal = 52)
    private final String protocol = "dubbo://";
    @JsonProperty(value = "interface")
    @JSONField(ordinal = 53, name = "interface")
    private String _interface;
    @JSONField(ordinal = 54)
    private String method;

    @JSONField(ordinal = 55)
    private TestinConfigCenter configCenter;
    @JSONField(ordinal = 56)
    private TestinRegistryCenter registryCenter;
    @JSONField(ordinal = 57)
    private TestinConsumerAndService consumerAndService;

    @JSONField(ordinal = 58)
    private List<KeyValue> args;
    @JSONField(ordinal = 59)
    private List<KeyValue> attachmentArgs;

    @JSONField(ordinal = 60)
    private String useEnvironment;

    @JSONField(ordinal = 61)
    private boolean customizeReq;


//    @Override
//    public void toHashTree(HashTree tree, List<TestinElement> hashTree, ParameterConfig config) {
//        // 非导出操作，且不是启用状态则跳过执行
//        if (!config.isOperating() && !this.isEnable()) {
//            return;
//        } else if (config.isOperating() && StringUtils.isNotEmpty(config.getOperatingSampleTestName())) {
//            this.setName(config.getOperatingSampleTestName());
//        }
//        if (this.getReferenced() != null && "Deleted".equals(this.getReferenced())) {
//            return;
//        }
//        if (this.getReferenced() != null && TestElementConstants.REF.name().equals(this.getReferenced())) {
//            boolean ref = this.setRefElement();
//            if (!ref) {
//                return;
//            }
//            hashTree = this.getHashTree();
//        }
//
//        final HashTree testPlanTree = tree.add(dubboSample(config));
//
//        //添加全局前后置脚本
//        EnvironmentConfig envConfig = null;
//        if (config.getConfig() != null) {
//            envConfig = config.getConfig().get(this.getProjectId());
//        }
//        //处理全局前后置脚本(步骤内)
//        String environmentId = this.getEnvironmentId();
//        if (environmentId == null) {
//            if (StringUtils.isEmpty(this.useEnvironment) && envConfig != null) {
//                environmentId = envConfig.getApiEnvironmentid();
//            } else {
//                environmentId = this.useEnvironment;
//            }
//        }
//        //根据配置将脚本放置在私有脚本之前
//        JMeterScriptUtil.setScriptByEnvironmentConfig(envConfig, testPlanTree, GlobalScriptFilterRequest.DUBBO.name(), environmentId, config, false);
//        if (CollectionUtils.isNotEmpty(hashTree)) {
//            hashTree = ElementUtil.order(hashTree);
//            hashTree.forEach(el -> {
//                el.toHashTree(testPlanTree, el.getHashTree(), config);
//            });
//        }
//
//        //根据配置将脚本放置在私有脚本之后
//        JMeterScriptUtil.setScriptByEnvironmentConfig(envConfig, testPlanTree, GlobalScriptFilterRequest.DUBBO.name(), environmentId, config, true);
//    }
//
//    private boolean setRefElement() {
//        try {
//            ApiDefinitionService apiDefinitionService = CommonBeanFactory.getBean(ApiDefinitionService.class);
//            ObjectMapper mapper = new ObjectMapper();
//            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//            MsDubboSampler proxy = null;
//            if (StringUtils.equals(this.getRefType(), "CASE")) {
//                ApiTestCaseService apiTestCaseService = CommonBeanFactory.getBean(ApiTestCaseService.class);
//                ApiTestCaseWithBLOBs bloBs = apiTestCaseService.get(this.getId());
//                if (bloBs != null) {
//                    this.setProjectId(bloBs.getProjectId());
//                    JSONObject element = JSON.parseObject(bloBs.getRequest(), Feature.DisableSpecialKeyDetect);
//                    ElementUtil.dataFormatting(element);
//                    proxy = mapper.readValue(element.toJSONString(), new TypeReference<MsDubboSampler>() {
//                    });
//                    this.setName(bloBs.getName());
//                }
//            } else {
//                ApiDefinitionWithBLOBs apiDefinition = apiDefinitionService.getBLOBs(this.getId());
//                if (apiDefinition != null) {
//                    this.setProjectId(apiDefinition.getProjectId());
//                    proxy = mapper.readValue(apiDefinition.getRequest(), new TypeReference<MsDubboSampler>() {
//                    });
//                    this.setName(apiDefinition.getName());
//                }
//            }
//            if (proxy != null) {
//                if (StringUtils.equals(this.getRefType(), "CASE")) {
//                    ElementUtil.mergeHashTree(this, proxy.getHashTree());
//                } else {
//                    this.setHashTree(proxy.getHashTree());
//                }
//                this.setMethod(proxy.getMethod());
//                this.set_interface(proxy.get_interface());
//                this.setAttachmentArgs(proxy.getAttachmentArgs());
//                this.setArgs(proxy.getArgs());
//                this.setConsumerAndService(proxy.getConsumerAndService());
//                this.setRegistryCenter(proxy.getRegistryCenter());
//                this.setConfigCenter(proxy.getConfigCenter());
//                return true;
//            }
//        } catch (Exception ex) {
//            LogUtil.error(ex);
//        }
//        return false;
//    }
//
//    private DubboSample dubboSample(ParameterConfig config) {
//        DubboSample sampler = new DubboSample();
//        sampler.setEnabled(this.isEnable());
//        sampler.setName(this.getName());
//        if (StringUtils.isEmpty(this.getName())) {
//            sampler.setName("DubboSamplerProxy");
//        }
//        if (config.isOperating()) {
//            String[] testNameArr = sampler.getName().split("<->");
//            if (testNameArr.length > 0) {
//                String testName = testNameArr[0];
//                sampler.setName(testName);
//            }
//        }
//        sampler.setProperty(TestElement.TEST_CLASS, DubboSample.class.getName());
//        sampler.setProperty(TestElement.GUI_CLASS, SaveService.aliasToClass("DubboSampleGui"));
//        ElementUtil.setBaseParams(sampler, this.getParent(), config, this.getId(), this.getIndex());
//        sampler.addTestElement(configCenter(this.getConfigCenter()));
//        sampler.addTestElement(registryCenter(this.getRegistryCenter()));
//        sampler.addTestElement(consumerAndService(this.getConsumerAndService()));
//
//        Constants.setRpcProtocol(this.getProtocol(), sampler);
//        Constants.setInterfaceName(this.get_interface(), sampler);
//        Constants.setMethod(this.getMethod(), sampler);
//
//        if (CollectionUtils.isNotEmpty(this.getArgs())) {
//            List<MethodArgument> methodArgs = this.getArgs().stream().filter(KeyValue::isValid).filter(KeyValue::isEnable)
//                    .map(keyValue -> new MethodArgument(keyValue.getName(), keyValue.getValue())).collect(Collectors.toList());
//            Constants.setMethodArgs(methodArgs, sampler);
//        }
//        if (CollectionUtils.isNotEmpty(this.getAttachmentArgs())) {
//            List<MethodArgument> attachmentArgs = this.getAttachmentArgs().stream().filter(KeyValue::isValid).filter(KeyValue::isEnable)
//                    .map(keyValue -> new MethodArgument(keyValue.getName(), keyValue.getValue())).collect(Collectors.toList());
//            Constants.setAttachmentArgs(attachmentArgs, sampler);
//        }
//        return sampler;
//    }
//
//    private ConfigTestElement configCenter(MsConfigCenter configCenter) {
//        ConfigTestElement configTestElement = new ConfigTestElement();
//        if (configCenter != null && StringUtils.isNotEmpty(configCenter.getAddress()) && StringUtils.isNotEmpty(configCenter.getProtocol()) && StringUtils.isNotEmpty(configCenter.getUsername()) && StringUtils.isNotEmpty(configCenter.getPassword())) {
//            Constants.setConfigCenterProtocol(configCenter.getProtocol(), configTestElement);
//            Constants.setConfigCenterGroup(configCenter.getGroup(), configTestElement);
//            Constants.setConfigCenterNamespace(configCenter.getNamespace(), configTestElement);
//            Constants.setConfigCenterUserName(configCenter.getUsername(), configTestElement);
//            Constants.setConfigCenterPassword(configCenter.getPassword(), configTestElement);
//            Constants.setConfigCenterAddress(configCenter.getAddress(), configTestElement);
//            Constants.setConfigCenterTimeout(configCenter.getTimeout(), configTestElement);
//        }
//        return configTestElement;
//    }
//
//    private ConfigTestElement registryCenter(MsRegistryCenter registryCenter) {
//        ConfigTestElement configTestElement = new ConfigTestElement();
//        if (registryCenter != null) {
//            Constants.setRegistryProtocol(registryCenter.getProtocol(), configTestElement);
//            Constants.setRegistryGroup(registryCenter.getGroup(), configTestElement);
//            Constants.setRegistryUserName(registryCenter.getUsername(), configTestElement);
//            Constants.setRegistryPassword(registryCenter.getPassword(), configTestElement);
//            Constants.setRegistryTimeout(registryCenter.getTimeout(), configTestElement);
//            Constants.setAddress(registryCenter.getAddress(), configTestElement);
//        }
//        return configTestElement;
//    }
//
//    private ConfigTestElement consumerAndService(MsConsumerAndService consumerAndService) {
//        ConfigTestElement configTestElement = new ConfigTestElement();
//        if (consumerAndService != null) {
//            Constants.setTimeout(consumerAndService.getTimeout(), configTestElement);
//            Constants.setVersion(consumerAndService.getVersion(), configTestElement);
//            Constants.setGroup(consumerAndService.getGroup(), configTestElement);
//            Constants.setConnections(consumerAndService.getConnections(), configTestElement);
//            Constants.setLoadbalance(consumerAndService.getLoadBalance(), configTestElement);
//            Constants.setAsync(consumerAndService.getAsync(), configTestElement);
//            Constants.setCluster(consumerAndService.getCluster(), configTestElement);
//        }
//        return configTestElement;
//    }
}
