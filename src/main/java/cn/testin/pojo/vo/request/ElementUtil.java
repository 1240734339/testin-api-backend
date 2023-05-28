package cn.testin.pojo.vo.request;

import cn.testin.commons.constants.DelimiterConstants;
import cn.testin.commons.constants.LoopConstants;
import cn.testin.commons.constants.TestElementConstants;
import cn.testin.commons.utils.CommonBeanFactory;
import cn.testin.commons.utils.FileUtils;
import cn.testin.commons.utils.LogUtil;
import cn.testin.extranal.io.constants.RunModeConstants;
import cn.testin.pojo.domain.TestEnvironmentDo;
import cn.testin.pojo.dto.environment.DatabaseConfig;
import cn.testin.pojo.dto.environment.EnvironmentConfig;
import cn.testin.pojo.dto.request.BodyFile;
import cn.testin.pojo.dto.request.KeyValue;
import cn.testin.pojo.dto.request.variable.ScenarioVariable;
import cn.testin.pojo.vo.request.base.TestinElement;
import cn.testin.pojo.dto.environment.ParameterConfig;
import cn.testin.pojo.vo.request.element.TestinHTTPSamplerProxy;
import cn.testin.pojo.vo.request.element.TestinLoopController;
import cn.testin.pojo.vo.request.element.TestinScenario;
import cn.testin.service.EnvironmentService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.metersphere.jmeter.utils.ScriptEngineUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.config.RandomVariableConfig;
import org.apache.jmeter.modifiers.CounterConfig;
import org.apache.jmeter.modifiers.JSR223PreProcessor;
import org.apache.jmeter.modifiers.UserParameters;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.AbstractTestElement;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jorphan.collections.HashTree;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class ElementUtil {
    private static final String PRE = "PRE";
    private static final String POST = "POST";
    private static final String ASSERTIONS = "ASSERTIONS";
    private static final String BODY_FILE_DIR = FileUtils.BODY_FILE_DIR;

    public static Arguments addArguments(ParameterConfig config, String projectId, String name) {
        if (config.isEffective(projectId) && config.getConfig().get(projectId).getCommonConfig() != null
                && CollectionUtils.isNotEmpty(config.getConfig().get(projectId).getCommonConfig().getVariables())) {
            Arguments arguments = new Arguments();
            arguments.setEnabled(true);
            arguments.setName(StringUtils.isNoneBlank(name) ? name : "Arguments");
            arguments.setProperty(TestElement.TEST_CLASS, Arguments.class.getName());
            arguments.setProperty(TestElement.GUI_CLASS, SaveService.aliasToClass("ArgumentsPanel"));
            config.getConfig().get(projectId).getCommonConfig().getVariables().stream().filter(ScenarioVariable::isConstantValid).filter(ScenarioVariable::isEnable).forEach(keyValue ->
                    arguments.addArgument(keyValue.getName(), keyValue.getValue(), "=")
            );
            if (arguments.getArguments().size() > 0) {
                return arguments;
            }
        }
        return null;
    }


    public static Map<String, EnvironmentConfig> getEnvironmentConfig(String environmentId, String projectId) {
        EnvironmentService environmentService = CommonBeanFactory.getBean(EnvironmentService.class);
        TestEnvironmentDo environment = environmentService.findByEnvId(environmentId);
        if (environment != null && environment.getConfig() != null) {
            if (StringUtils.isEmpty(projectId)) {
                projectId = environment.getProjectId();
            }
            // 单独接口执行
            if (StringUtils.isEmpty(projectId)) {
                projectId = environment.getProjectId();
            }
            Map<String, EnvironmentConfig> map = new HashMap<>();
            map.put(projectId, JSONObject.parseObject(environment.getConfig(), EnvironmentConfig.class));
            return map;
        }


        return null;
    }
//    public static void addCsvDataSet(HashTree tree, List<ScenarioVariable> variables, ParameterConfig config, String shareMode) {
//        if (CollectionUtils.isNotEmpty(variables)) {
//            List<ScenarioVariable> list = variables.stream().filter(ScenarioVariable::isCSVValid).filter(ScenarioVariable::isEnable).collect(Collectors.toList());
//            if (CollectionUtils.isEmpty(list) && CollectionUtils.isNotEmpty(config.getTransferVariables())) {
//                list = config.getTransferVariables().stream().filter(ScenarioVariable::isCSVValid).filter(ScenarioVariable::isEnable).collect(Collectors.toList());
//            }
//            if (CollectionUtils.isNotEmpty(list)) {
//                FileMetadataService fileMetadataService = CommonBeanFactory.getBean(FileMetadataService.class);
//                list.forEach(item -> {
//                    CSVDataSet csvDataSet = new CSVDataSet();
//                    csvDataSet.setEnabled(true);
//                    csvDataSet.setProperty(TestElement.TEST_CLASS, CSVDataSet.class.getName());
//                    csvDataSet.setProperty(TestElement.GUI_CLASS, SaveService.aliasToClass("TestBeanGUI"));
//                    csvDataSet.setName(StringUtils.isEmpty(item.getName()) ? "CSVDataSet" : item.getName());
//                    csvDataSet.setProperty("fileEncoding", StringUtils.isEmpty(item.getEncoding()) ? "UTF-8" : item.getEncoding());
//                    if (CollectionUtils.isEmpty(item.getFiles())) {
//                        TIException.throwException(StringUtils.isEmpty(item.getName()) ? "CSVDataSet" : item.getName() + "：[ " + Translator.get("csv_no_exist") + " ]");
//                    } else {
//                        boolean isRef = false;
//                        String fileId = null;
//                        boolean isRepository = false;
//                        BodyFile file = item.getFiles().get(0);
//                        String path = BODY_FILE_DIR + "/" + item.getFiles().get(0).getId() + "_" + item.getFiles().get(0).getName();
//                        if (StringUtils.equalsIgnoreCase(file.getStorage(), StorageConstants.FILE_REF.name())) {
//                            isRef = true;
//                            fileId = file.getFileId();
//                            if (fileMetadataService != null) {
//                                FileMetadata fileMetadata = fileMetadataService.getFileMetadataById(fileId);
//                                if (fileMetadata != null && StringUtils.equals(fileMetadata.getStorage(), StorageConstants.GIT.name())) {
//                                    isRepository = true;
//                                }
//                            }
//                            path = FileUtils.getFilePath(file);
//                        }
//                        if (!config.isOperating() && !isRepository && !new File(path).exists()) {
//                            TIException.throwException(StringUtils.isEmpty(item.getName()) ? "CSVDataSet" : item.getName() + "：[ " + Translator.get("csv_no_exist") + " ]");
//                        }
//                        csvDataSet.setProperty("filename", path);
//                        csvDataSet.setProperty("isRef", isRef);
//                        csvDataSet.setProperty("fileId", fileId);
//                    }
//                    csvDataSet.setIgnoreFirstLine(false);
//                    csvDataSet.setProperty("shareMode", shareMode);
//                    csvDataSet.setProperty("recycle", true);
//                    csvDataSet.setProperty("delimiter", item.getDelimiter());
//                    csvDataSet.setProperty("quotedData", item.isQuotedData());
//                    csvDataSet.setComment(StringUtils.isEmpty(item.getDescription()) ? "" : item.getDescription());
//                    tree.add(csvDataSet);
//                });
//            }
//        }
//    }

    public static void addCounter(HashTree tree, List<ScenarioVariable> variables, boolean isInternal) {
        if (CollectionUtils.isNotEmpty(variables)) {
            List<ScenarioVariable> list = variables.stream().filter(ScenarioVariable::isCounterValid).filter(ScenarioVariable::isEnable).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(list)) {
                list.forEach(item -> {
                    CounterConfig counterConfig = new CounterConfig();
                    counterConfig.setEnabled(true);
                    counterConfig.setProperty(TestElement.TEST_CLASS, CounterConfig.class.getName());
                    counterConfig.setProperty(TestElement.GUI_CLASS, SaveService.aliasToClass("CounterConfigGui"));
                    counterConfig.setName(item.getName());
                    if (isInternal) {
                        counterConfig.setStart((item.getStartNumber() + 1));
                    } else {
                        counterConfig.setStart(item.getStartNumber());
                    }
                    counterConfig.setEnd(item.getEndNumber());
                    counterConfig.setVarName(item.getName());
                    counterConfig.setIncrement(item.getIncrement());
                    counterConfig.setFormat(item.getValue());
                    counterConfig.setComment(StringUtils.isEmpty(item.getDescription()) ? "" : item.getDescription());
                    tree.add(counterConfig);
                });
            }
        }
    }

    public static void addRandom(HashTree tree, List<ScenarioVariable> variables) {
        if (CollectionUtils.isNotEmpty(variables)) {
            List<ScenarioVariable> list = variables.stream().filter(ScenarioVariable::isRandom).filter(ScenarioVariable::isEnable).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(list)) {
                list.forEach(item -> {
                    RandomVariableConfig randomVariableConfig = new RandomVariableConfig();
                    randomVariableConfig.setEnabled(true);
                    randomVariableConfig.setProperty(TestElement.TEST_CLASS, RandomVariableConfig.class.getName());
                    randomVariableConfig.setProperty(TestElement.GUI_CLASS, SaveService.aliasToClass("TestBeanGUI"));
                    randomVariableConfig.setName(item.getName());
                    randomVariableConfig.setProperty("variableName", item.getName());
                    randomVariableConfig.setProperty("outputFormat", item.getValue());
                    randomVariableConfig.setProperty("minimumValue", item.getMinNumber());
                    randomVariableConfig.setProperty("maximumValue", item.getMaxNumber());
                    randomVariableConfig.setComment(StringUtils.isEmpty(item.getDescription()) ? "" : item.getDescription());
                    tree.add(randomVariableConfig);
                });
            }
        }
    }

    public static String getFullPath(TestinElement element, String path) {
        if (element.getParent() == null) {
            return path;
        }
        if (TestElementConstants.LoopController.name().equals(element.getType())) {
            TestinLoopController loopController = (TestinLoopController) element;
            if (StringUtils.equals(loopController.getLoopType(), LoopConstants.WHILE.name()) && loopController.getWhileController() != null) {
                path = "While 循环" + DelimiterConstants.STEP_DELIMITER.toString() + "While 循环-" + "${MS_LOOP_CONTROLLER_CONFIG}";
            }
            if (StringUtils.equals(loopController.getLoopType(), LoopConstants.FOREACH.name()) && loopController.getForEachController() != null) {
                path = "ForEach 循环" + DelimiterConstants.STEP_DELIMITER.toString() + " ForEach 循环-" + "${MS_LOOP_CONTROLLER_CONFIG}";
            }
            if (StringUtils.equals(loopController.getLoopType(), LoopConstants.LOOP_COUNT.name()) && loopController.getCountController() != null) {
                path = "次数循环" + DelimiterConstants.STEP_DELIMITER.toString() + "次数循环-" + "${MS_LOOP_CONTROLLER_CONFIG}";
            }
        } else {
            path = StringUtils.isEmpty(element.getName()) ? element.getType() : element.getName() + DelimiterConstants.STEP_DELIMITER.toString() + path;
        }
        return getFullPath(element.getParent(), path);
    }

    public static String getParentName(TestinElement parent) {
        if (parent != null) {
            // 获取全路径以备后面使用
            String fullPath = getFullPath(parent, new String());
            return fullPath + DelimiterConstants.SEPARATOR.toString() + parent.getName();
        }
        return "";
    }

    public static String getFullIndexPath(TestinElement element, String path) {
        if (element == null || element.getParent() == null) {
            return path;
        }
        path = element.getIndex() + "_" + path;
        return getFullIndexPath(element.getParent(), path);
    }

    public static boolean isURL(String str) {
        try {
            if (StringUtils.isEmpty(str)) {
                return false;
            }
            new URL(str);
            return true;
        } catch (Exception e) {
            // 支持包含变量的url
            if (str.matches("^(http|https|ftp)://.*$") && str.matches(".*://\\$\\{.*$")) {
                return true;
            }
            return false;
        }
    }

    public static <T> List<T> findFromHashTreeByType(TestinElement hashTree, Class<T> clazz, List<T> requests) {
        if (requests == null) {
            requests = new ArrayList<>();
        }
        if (clazz.isInstance(hashTree)) {
            requests.add((T) hashTree);
        } else {
            if (hashTree != null) {
                LinkedList<TestinElement> childHashTree = hashTree.getHashTree();
                if (CollectionUtils.isNotEmpty(childHashTree)) {
                    for (TestinElement item : childHashTree) {
                        findFromHashTreeByType(item, clazz, requests);
                    }
                }
            }
        }
        return requests;
    }


    public final static HashMap<String, String> clazzMap = new HashMap<String, String>() {
        {
            // todo value值需要修改
            put("scenario", "cn.testin.pojo.vo.request.element.TestinScenario");
            put("HTTPSamplerProxy", "cn.testin.pojo.vo.request.element.TestinHTTPSamplerProxy");
            put("DubboSampler", "cn.testin.pojo.vo.request.element.TestinDubboSampler");
            put("JDBCSampler", "cn.testin.pojo.vo.request.element.TestinJDBCSampler");
            put("TCPSampler", "cn.testin.pojo.vo.request.element.TestinTCPSampler");
            put("IfController", "cn.testin.pojo.vo.request.element.TestinIfController");
            put("TransactionController", "cn.testin.pojo.vo.request.element.TestinTransactionController");
            put("LoopController", "cn.testin.pojo.vo.request.element.TestinLoopController");
            put("ConstantTimer", "cn.testin.pojo.vo.request.element.TestinConstantTimer");
            put("JSR223Processor", "cn.testin.pojo.vo.request.element.TestinJSR223Processor");
            put("JSR223PreProcessor", "cn.testin.pojo.vo.request.element.TestinJSR223PreProcessor");
            put("JSR223PostProcessor", "cn.testin.pojo.vo.request.element.TestinJSR223PostProcessor");
            put("JDBCPreProcessor", "cn.testin.pojo.vo.request.element.TestinJDBCPreProcessor");
            put("JDBCPostProcessor", "cn.testin.pojo.vo.request.element.TestinJDBCPostProcessor");
            put("Assertions", "cn.testin.pojo.vo.request.element.TestinAssertions");
            put("Extract", "cn.testin.pojo.vo.request.element.TestinExtract");
            put("JmeterElement", "cn.testin.pojo.vo.request.element.TestinJmeterElement");
            put("TestPlan", "cn.testin.pojo.vo.request.element.TestinTestPlan");
            put("ThreadGroup", "cn.testin.pojo.vo.request.element.TestinThreadGroup");
            put("DNSCacheManager", "cn.testin.pojo.vo.request.element.TestinDNSCacheManager");
            put("DebugSampler", "cn.testin.pojo.vo.request.element.TestinDebugSampler");
            put("AuthManager", "cn.testin.pojo.vo.request.element.TestinAuthManager");
        }
    };

    public final static List<String> requests = new ArrayList<String>() {{
        this.add("HTTPSamplerProxy");
        this.add("MsUiCommand");
        this.add("DubboSampler");
        this.add("JDBCSampler");
        this.add("TCPSampler");
        this.add("JSR223Processor");
        this.add("JSR223PreProcessor");
        this.add("JSR223PostProcessor");
        this.add("JDBCPreProcessor");
        this.add("JDBCPostProcessor");
        this.add("JmeterElement");
        this.add("TestPlan");
        this.add("ThreadGroup");
        this.add("DNSCacheManager");
        this.add("DebugSampler");
        this.add("AuthManager");
        this.add("AbstractSampler");
    }};

    private static void formatSampler(JSONObject element) {
        if (element == null || StringUtils.isEmpty(element.getString("type"))) {
            return;
        }
        if (element.get("clazzName") == null && element.getString("type").equals("TCPSampler")) {
            if (element.getString("tcpPreProcessor") != null) {
                JSONObject tcpPreProcessor = JSON.parseObject(element.getString("tcpPreProcessor"), Feature.DisableSpecialKeyDetect);
                if (tcpPreProcessor != null && tcpPreProcessor.get("clazzName") == null) {
                    tcpPreProcessor.fluentPut("clazzName", clazzMap.get(tcpPreProcessor.getString("type")));
                    element.fluentPut("tcpPreProcessor", tcpPreProcessor);
                }
            }
        } else if (element.getString("type").equals("HTTPSamplerProxy")) {
            if (element.getString("authManager") != null) {
                JSONObject authManager = JSON.parseObject(element.getString("authManager"), Feature.DisableSpecialKeyDetect);
                if (authManager != null && authManager.get("clazzName") == null) {
                    authManager.fluentPut("clazzName", clazzMap.get(authManager.getString("type")));
                    element.fluentPut("authManager", authManager);
                }
            }
        }
    }

    /**
     * 只找出场景直接依赖
     *
     * @param hashTree
     * @param referenceRelationships
     */
    public static void relationships(JSONArray hashTree, List<String> referenceRelationships) {
        for (int i = 0; i < hashTree.size(); i++) {
            JSONObject element = hashTree.getJSONObject(i);
            if (element != null && StringUtils.equals(element.get("type").toString(), "scenario") && StringUtils.equals(element.get("referenced").toString(), "REF")) {
                if (!referenceRelationships.contains(element.get("id").toString())) {
                    referenceRelationships.add(element.get("id").toString());
                }
            } else {
                if (element.containsKey("hashTree")) {
                    JSONArray elementJSONArray = element.getJSONArray("hashTree");
                    relationships(elementJSONArray, referenceRelationships);
                }
            }
        }
    }

    public static void dataFormatting(JSONArray hashTree) {
        for (int i = 0; i < hashTree.size(); i++) {
            JSONObject element = hashTree.getJSONObject(i);
            if (element == null) {
                continue;
            }
            formatSampler(element);
            if (element.get("clazzName") == null && clazzMap.containsKey(element.getString("type"))) {
                element.fluentPut("clazzName", clazzMap.get(element.getString("type")));
            }
            if (element.containsKey("hashTree")) {
                JSONArray elementJSONArray = element.getJSONArray("hashTree");
                dataFormatting(elementJSONArray);
            }
        }
    }

    public static void dataFormatting(JSONObject element) {
        if (element != null && element.get("clazzName") == null && clazzMap.containsKey(element.getString("type"))) {
            element.fluentPut("clazzName", clazzMap.get(element.getString("type")));
        }
        formatSampler(element);
        if (element != null && element.containsKey("hashTree")) {
            JSONArray elementJSONArray = element.getJSONArray("hashTree");
            dataFormatting(elementJSONArray);
        }
    }

    public static void dataSetDomain(JSONArray hashTree, ParameterConfig msParameter) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            for (int i = 0; i < hashTree.size(); i++) {
                JSONObject element = hashTree.getJSONObject(i);
                boolean isScenarioEnv = false;
                ParameterConfig config = new ParameterConfig();
                if (element != null && element.get("type").toString().equals("scenario")) {
                    TestinScenario scenario = JSONObject.toJavaObject(element, TestinScenario.class);
//                    if (scenario.isEnvironmentEnable()) {
//                        isScenarioEnv = true;
//                        Map<String, String> environmentMap = new HashMap<>();
//                        ApiScenarioMapper apiScenarioMapper = CommonBeanFactory.getBean(ApiScenarioMapper.class);
//                        EnvironmentGroupProjectService environmentGroupProjectService = CommonBeanFactory.getBean(EnvironmentGroupProjectService.class);
//                        ApiScenarioWithBLOBs apiScenarioWithBLOBs = apiScenarioMapper.selectByPrimaryKey(scenario.getId());
//                        String environmentType = apiScenarioWithBLOBs.getEnvironmentType();
//                        String environmentGroupId = apiScenarioWithBLOBs.getEnvironmentGroupId();
//                        String environmentJson = apiScenarioWithBLOBs.getEnvironmentJson();
//                        if (StringUtils.equals(environmentType, EnvironmentType.GROUP.name())) {
//                            environmentMap = environmentGroupProjectService.getEnvMap(environmentGroupId);
//                        } else if (StringUtils.equals(environmentType, EnvironmentType.JSON.name())) {
//                            environmentMap = JSON.parseObject(environmentJson, Map.class, Feature.DisableSpecialKeyDetect);
//                        }
//                        Map<String, EnvironmentConfig> envConfig = new HashMap<>(16);
//                        if (environmentMap != null && !environmentMap.isEmpty()) {
//                            Map<String, String> finalEnvironmentMap = environmentMap;
//                            environmentMap.keySet().forEach(projectId -> {
//                                ApiTestEnvironmentService environmentService = CommonBeanFactory.getBean(ApiTestEnvironmentService.class);
//                                ApiTestEnvironmentWithBLOBs environment = environmentService.get(finalEnvironmentMap.get(projectId));
//                                if (environment != null && environment.getConfig() != null) {
//                                    EnvironmentConfig env = JSONObject.parseObject(environment.getConfig(), EnvironmentConfig.class);
//                                    env.setApiEnvironmentid(environment.getId());
//                                    envConfig.put(projectId, env);
//                                }
//                            });
//                            config.setConfig(envConfig);
//                        }
//                    }
                } else if (element != null && element.get("type").toString().equals("HTTPSamplerProxy")) {
                    TestinHTTPSamplerProxy httpSamplerProxy = JSON.toJavaObject(element, TestinHTTPSamplerProxy.class);
                    if (httpSamplerProxy != null
                            && (!httpSamplerProxy.isCustomizeReq() || (httpSamplerProxy.isCustomizeReq() && BooleanUtils.isTrue(httpSamplerProxy.getIsRefEnvironment())))) {
                        // 多态JSON普通转换会丢失内容，需要通过 ObjectMapper 获取
                        if (element != null && StringUtils.isNotEmpty(element.getString("hashTree"))) {
                            LinkedList<TestinElement> elements = mapper.readValue(element.getString("hashTree"),
                                    new TypeReference<LinkedList<TestinElement>>() {
                                    });
                            httpSamplerProxy.setHashTree(elements);
                        }
                        HashTree tmpHashTree = new HashTree();
                        httpSamplerProxy.toHashTree(tmpHashTree, null, msParameter);
                        if (tmpHashTree != null && tmpHashTree.getArray().length > 0) {
                            HTTPSamplerProxy object = (HTTPSamplerProxy) tmpHashTree.getArray()[0];
                            // 清空Domain
                            element.fluentPut("domain", "");
                            if (object != null && StringUtils.isNotEmpty(object.getDomain())) {
                                element.fluentPut("domain", StringUtils.isNotEmpty(object.getProtocol()) ? object.getProtocol() + "://" + object.getDomain() : object.getDomain());
                            }
                        }
                    }
                }
                if (element.containsKey("hashTree")) {
                    JSONArray elementJSONArray = element.getJSONArray("hashTree");
                    if (isScenarioEnv) {
                        dataSetDomain(elementJSONArray, config);
                    } else {
                        dataSetDomain(elementJSONArray, msParameter);
                    }
                }
            }
        } catch (Exception e) {
            LogUtil.error(e.getMessage());
        }
    }

    public static void mergeHashTree(TestinElement element, LinkedList<TestinElement> targetHashTree) {
        try {
            LinkedList<TestinElement> sourceHashTree = element.getHashTree();
            if (CollectionUtils.isNotEmpty(sourceHashTree) && CollectionUtils.isNotEmpty(targetHashTree) && sourceHashTree.size() < targetHashTree.size()) {
                element.setHashTree(targetHashTree);
                return;
            }
            List<String> sourceIds = new ArrayList<>();
            List<String> delIds = new ArrayList<>();
            Map<String, TestinElement> updateMap = new HashMap<>();
            if (CollectionUtils.isEmpty(sourceHashTree)) {
                if (CollectionUtils.isNotEmpty(targetHashTree)) {
                    element.setHashTree(targetHashTree);
                }
                return;
            }
            if (CollectionUtils.isNotEmpty(targetHashTree)) {
                for (TestinElement item : targetHashTree) {
                    if (StringUtils.isNotEmpty(item.getId())) {
                        updateMap.put(item.getId(), item);
                    }
                }
            }
            // 找出待更新内容和源已经被删除的内容
            if (CollectionUtils.isNotEmpty(sourceHashTree)) {
                for (int i = 0; i < sourceHashTree.size(); i++) {
                    TestinElement source = sourceHashTree.get(i);
                    if (source != null) {
                        sourceIds.add(source.getId());
                        if (!StringUtils.equals(source.getLabel(), "SCENARIO-REF-STEP") && StringUtils.isNotEmpty(source.getId())) {
                            if (updateMap.containsKey(source.getId())) {
                                sourceHashTree.set(i, updateMap.get(source.getId()));
                            } else {
                                delIds.add(source.getId());
                            }
                        }
                        // 历史数据兼容
                        if (StringUtils.isEmpty(source.getId()) && !StringUtils.equals(source.getLabel(), "SCENARIO-REF-STEP") && i < targetHashTree.size()) {
                            sourceHashTree.set(i, targetHashTree.get(i));
                        }
                    }
                }
            }

            // 删除多余的步骤
            sourceHashTree.removeIf(item -> item != null && delIds.contains(item.getId()));
            // 补充新增的源引用步骤
            if (CollectionUtils.isNotEmpty(targetHashTree)) {
                for (TestinElement item : targetHashTree) {
                    if (!sourceIds.contains(item.getId())) {
                        sourceHashTree.add(item);
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(sourceHashTree)) {
                element.setHashTree(sourceHashTree);
            }
        } catch (Exception e) {
            element.setHashTree(targetHashTree);
        }
    }

    public static List<JSONObject> mergeHashTree(List<JSONObject> sourceHashTree, List<JSONObject> targetHashTree) {
        try {
            List<String> sourceIds = new ArrayList<>();
            List<String> delIds = new ArrayList<>();
            Map<String, JSONObject> updateMap = new HashMap<>();

            if (CollectionUtils.isNotEmpty(targetHashTree)) {
                for (int i = 0; i < targetHashTree.size(); i++) {
                    JSONObject item = targetHashTree.get(i);
                    item.put("disabled", true);
                    if (StringUtils.isNotEmpty(item.getString("id"))) {
                        updateMap.put(item.getString("id"), item);
                    }
                }
            }
            // 找出待更新内容和源已经被删除的内容
            if (CollectionUtils.isNotEmpty(sourceHashTree)) {
                for (int i = 0; i < sourceHashTree.size(); i++) {
                    JSONObject source = sourceHashTree.get(i);
                    if (source != null) {
                        sourceIds.add(source.getString("id"));
                        if (!StringUtils.equals(source.getString("label"), "SCENARIO-REF-STEP") && StringUtils.isNotEmpty(source.getString("id"))) {
                            if (updateMap.containsKey(source.getString("id"))) {
                                sourceHashTree.set(i, updateMap.get(source.getString("id")));
                            } else {
                                delIds.add(source.getString("id"));
                            }
                        }
                        // 历史数据兼容
                        if (StringUtils.isEmpty(source.getString("id")) && !StringUtils.equals(source.getString("label"), "SCENARIO-REF-STEP") && i < targetHashTree.size()) {
                            sourceHashTree.set(i, targetHashTree.get(i));
                        }
                    }
                }
            }

            // 删除多余的步骤
            for (int i = 0; i < sourceHashTree.size(); i++) {
                JSONObject source = sourceHashTree.get(i);
                if (delIds.contains(source.getString("id"))) {
                    sourceHashTree.remove(i);
                }
            }
            // 补充新增的源引用步骤
            if (CollectionUtils.isNotEmpty(targetHashTree)) {
                for (int i = 0; i < targetHashTree.size(); i++) {
                    JSONObject item = sourceHashTree.get(i);
                    if (!sourceIds.contains(item.getString("id"))) {
                        sourceHashTree.add(item);
                    }
                }
            }
        } catch (Exception e) {
            return targetHashTree;
        }
        return sourceHashTree;
    }

    public static String hashTreeToString(HashTree hashTree) {
        try (ByteArrayOutputStream bas = new ByteArrayOutputStream()) {
            SaveService.saveTree(hashTree, bas);
            return bas.toString();
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.warn("HashTree error, can't log jmx scenarioDefinition");
        }
        return null;
    }

    public static String getResourceId(String resourceId, ParameterConfig config, TestinElement parent, String indexPath) {
        if (StringUtils.isNotEmpty(config.getCaseId()) && StringUtils.equals(config.getReportType(), RunModeConstants.SET_REPORT.toString())) {
            resourceId = config.getCaseId() + "=" + resourceId;
        }
        // 跳过失败重试层
        if (parent != null && StringUtils.equalsAnyIgnoreCase("RetryLoopController", parent.getType())) {
            parent = parent.getParent();
        }
        return resourceId + "_" + ElementUtil.getFullIndexPath(parent, indexPath);
    }

    public static JSR223PreProcessor argumentsToProcessor(Arguments arguments) {
        JSR223PreProcessor processor = new JSR223PreProcessor();
        processor.setEnabled(true);
        processor.setName("User Defined Variables");
        processor.setProperty("scriptLanguage", "beanshell");
        processor.setProperty(TestElement.TEST_CLASS, JSR223PreProcessor.class.getName());
        processor.setProperty(TestElement.GUI_CLASS, SaveService.aliasToClass("TestBeanGUI"));
        StringBuffer script = new StringBuffer();
        if (arguments != null) {
            for (int i = 0; i < arguments.getArguments().size(); ++i) {
                String argValue = arguments.getArgument(i).getValue();
                script.append("vars.put(\"" + arguments.getArgument(i).getName() + "\",\"" + argValue + "\");").append("\n");
            }
            processor.setProperty("script", script.toString());
        }
        return processor;
    }

    public static UserParameters argumentsToUserParameters(Arguments arguments) {
        UserParameters processor = new UserParameters();
        processor.setEnabled(true);
        processor.setName("User Defined Variables");
        processor.setPerIteration(true);
        processor.setProperty(TestElement.TEST_CLASS, UserParameters.class.getName());
        processor.setProperty(TestElement.GUI_CLASS, SaveService.aliasToClass("UserParametersGui"));
        if (arguments != null && arguments.getArguments().size() > 0) {
            List<String> names = new LinkedList<>();
            List<Object> values = new LinkedList<>();
            List<Object> threadValues = new LinkedList<>();
            for (int i = 0; i < arguments.getArguments().size(); ++i) {
                String argValue = arguments.getArgument(i).getValue();
                String name = arguments.getArgument(i).getName();
                names.add(name);
                values.add(argValue);
            }
            processor.setNames(names);
            threadValues.add(values);
            processor.setThreadLists(threadValues);
        }
        return processor;
    }

    public static void setBaseParams(AbstractTestElement sampler, TestinElement parent, ParameterConfig config, String id, String indexPath) {
        sampler.setProperty("TEST-ID", id);
        sampler.setProperty("TEST-RESOURCE-ID", ElementUtil.getResourceId(id, config, parent, indexPath));
    }

    public static void accuracyHashTree(HashTree hashTree) {
        Map<Object, HashTree> objects = new LinkedHashMap<>();
        Object groupHashTree = hashTree;
        if (hashTree != null && hashTree.size() > 0) {
            for (Object key : hashTree.keySet()) {
                if (key instanceof TestPlan) {
                    for (Object node : hashTree.get(key).keySet()) {
                        if (node instanceof ThreadGroup) {
                            groupHashTree = hashTree.get(key).get(node);
                        }
                    }
                } else {
                    objects.put(key, hashTree.get(key));
                }
            }
        }
        if (!objects.isEmpty() && groupHashTree instanceof HashTree) {
            for (Object key : objects.keySet()) {
                hashTree.remove(key);
                ((HashTree) groupHashTree).add(key, objects.get(key));
            }
        }
    }

    private static final List<String> preOperates = new ArrayList<String>() {{
        this.add("JSR223PreProcessor");
        this.add("JDBCPreProcessor");
        this.add("ConstantTimer");
    }};
    private static final List<String> postOperates = new ArrayList<String>() {{
        this.add("JSR223PostProcessor");
        this.add("JDBCPostProcessor");
        this.add("Extract");
    }};


    public static void copyBean(JSONObject target, JSONObject source) {
        if (source == null || target == null) {
            return;
        }
        for (String key : target.keySet()) {
            if (source.containsKey(key) && !StringUtils.equalsIgnoreCase(key, "hashTree")) {
                target.put(key, source.get(key));
            }
        }
    }

    private static List<TestinElement> orderList(List<TestinElement> list) {
        if (CollectionUtils.isNotEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                if (StringUtils.isEmpty(list.get(i).getIndex())) {
                    list.get(i).setIndex(String.valueOf(i));
                }
            }
        }
        return list;
    }

    public static Map<String, List<JSONObject>> group(JSONArray elements) {
        Map<String, List<JSONObject>> groupMap = new LinkedHashMap<>();
        if (CollectionUtils.isNotEmpty(elements)) {
            for (int i = 0; i < elements.size(); i++) {
                JSONObject item = elements.getJSONObject(i);
                if ("Assertions".equals(item.getString("type"))) {
                    if (groupMap.containsKey(ASSERTIONS)) {
                        groupMap.get(ASSERTIONS).add(item);
                    } else {
                        groupMap.put(ASSERTIONS, new LinkedList<JSONObject>() {{
                            this.add(item);
                        }});
                    }
                } else if (preOperates.contains(item.getString("type"))) {
                    if (groupMap.containsKey(PRE)) {
                        groupMap.get(PRE).add(item);
                    } else {
                        groupMap.put(PRE, new LinkedList<JSONObject>() {{
                            this.add(item);
                        }});
                    }
                } else if (postOperates.contains(item.getString("type"))) {
                    if (groupMap.containsKey(POST)) {
                        groupMap.get(POST).add(item);
                    } else {
                        groupMap.put(POST, new LinkedList<JSONObject>() {{
                            this.add(item);
                        }});
                    }
                }
            }
        }
        return groupMap;
    }

    public static List<TestinElement> order(List<TestinElement> elements) {
        List<TestinElement> elementList = new LinkedList<>();
        if (CollectionUtils.isNotEmpty(elements)) {
            Map<String, List<TestinElement>> groupMap = new LinkedHashMap<>();
            elements.forEach(item -> {
                if ("Assertions".equals(item.getType())) {
                    if (groupMap.containsKey(ASSERTIONS)) {
                        groupMap.get(ASSERTIONS).add(item);
                    } else {
                        groupMap.put(ASSERTIONS, new LinkedList<TestinElement>() {{
                            this.add(item);
                        }});
                    }
                } else if (preOperates.contains(item.getType())) {
                    if (groupMap.containsKey(PRE)) {
                        groupMap.get(PRE).add(item);
                    } else {
                        groupMap.put(PRE, new LinkedList<TestinElement>() {{
                            this.add(item);
                        }});
                    }
                } else if (postOperates.contains(item.getType())) {
                    if (groupMap.containsKey(POST)) {
                        groupMap.get(POST).add(item);
                    } else {
                        groupMap.put(POST, new LinkedList<TestinElement>() {{
                            this.add(item);
                        }});
                    }
                } else {
                    elementList.add(item);
                }
            });
            if (CollectionUtils.isNotEmpty(groupMap.get(PRE))) {
                elementList.addAll(orderList(groupMap.get(PRE)));
            }
            if (CollectionUtils.isNotEmpty(groupMap.get(POST))) {
                elementList.addAll(orderList(groupMap.get(POST)));
            }
            if (CollectionUtils.isNotEmpty(groupMap.get(ASSERTIONS))) {
                elementList.addAll(groupMap.get(ASSERTIONS));
            }
        }
        return elementList;
    }

    public static String getEvlValue(String evlValue) {
        try {
            if (StringUtils.startsWith(evlValue, "@")) {
                return ScriptEngineUtils.calculate(evlValue);
            } else {
                return ScriptEngineUtils.buildFunctionCallString(evlValue);
            }
        } catch (Exception e) {
            return evlValue;
        }
    }

    public static Arguments getConfigArguments(ParameterConfig config, String name, String projectId, List<ScenarioVariable> variables) {
        Arguments arguments = new Arguments();
        arguments.setEnabled(true);
        arguments.setName(StringUtils.isNotEmpty(name) ? name : "Arguments");
        arguments.setProperty(TestElement.TEST_CLASS, Arguments.class.getName());
        arguments.setProperty(TestElement.GUI_CLASS, SaveService.aliasToClass("ArgumentsPanel"));

        // 场景变量
        if (CollectionUtils.isNotEmpty(variables)) {
            variables.stream().filter(ScenarioVariable::isConstantValid).forEach(keyValue ->
                    arguments.addArgument(keyValue.getName(), keyValue.getValue(), "=")
            );

            List<ScenarioVariable> variableList = variables.stream().filter(ScenarioVariable::isListValid).collect(Collectors.toList());
            variableList.forEach(item -> {
                String[] arrays = item.getValue().split(",");
                for (int i = 0; i < arrays.length; i++) {
                    arguments.addArgument(item.getName() + "_" + (i + 1), arrays[i], "=");
                }
            });
        }
        // 环境通用变量
        if (config.isEffective(projectId) && config.getConfig().get(projectId).getCommonConfig() != null
                && CollectionUtils.isNotEmpty(config.getConfig().get(projectId).getCommonConfig().getVariables())) {
            //常量
            List<ScenarioVariable> constants = config.getConfig().get(projectId).getCommonConfig().getVariables().stream().filter(ScenarioVariable::isConstantValid).filter(ScenarioVariable::isEnable).collect(Collectors.toList());
            constants.forEach(keyValue ->
                    arguments.addArgument(keyValue.getName(), keyValue.getValue(), "=")
            );
            // List类型的变量
            List<ScenarioVariable> variableList = config.getConfig().get(projectId).getCommonConfig().getVariables().stream().filter(ScenarioVariable::isListValid).filter(ScenarioVariable::isEnable).collect(Collectors.toList());
            variableList.forEach(item -> {
                String[] arrays = item.getValue().split(",");
                for (int i = 0; i < arrays.length; i++) {
                    arguments.addArgument(item.getName() + "_" + (i + 1), arrays[i], "=");
                }
            });
            // 清空变量，防止重复添加
            config.getConfig().get(projectId).getCommonConfig().getVariables().removeAll(constants);
            config.getConfig().get(projectId).getCommonConfig().getVariables().removeAll(variableList);
        }

        if (arguments.getArguments() != null && arguments.getArguments().size() > 0) {
            return arguments;
        }
        return null;
    }

    public static void addOtherVariables(ParameterConfig config, HashTree httpSamplerTree, String projectId) {
        if (config.isEffective(projectId) && config.getConfig().get(projectId).getCommonConfig() != null
                && CollectionUtils.isNotEmpty(config.getConfig().get(projectId).getCommonConfig().getVariables())) {
//            ElementUtil.addCsvDataSet(httpSamplerTree, config.getConfig().get(projectId).getCommonConfig().getVariables(), config, "shareMode.group");
            ElementUtil.addCounter(httpSamplerTree, config.getConfig().get(projectId).getCommonConfig().getVariables(), false);
            ElementUtil.addRandom(httpSamplerTree, config.getConfig().get(projectId).getCommonConfig().getVariables());
        }
    }

    public static DatabaseConfig dataSource(String projectId, String dataSourceId, EnvironmentConfig envConfig) {
        try {
            EnvironmentService environmentService = CommonBeanFactory.getBean(EnvironmentService.class);
            List<TestEnvironmentDo> environments = environmentService.list(projectId);
            EnvironmentConfig dataConfig = null;
            List<String> dataName = new ArrayList<>();
            List<TestEnvironmentDo> orgDataSource = environments.stream().filter(TestEnvironmentDo -> TestEnvironmentDo.getConfig().contains(dataSourceId)).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(orgDataSource)) {
                dataConfig = JSONObject.parseObject(orgDataSource.get(0).getConfig(), EnvironmentConfig.class);
                if (CollectionUtils.isNotEmpty(dataConfig.getDatabaseConfigs())) {
                    dataName = dataConfig.getDatabaseConfigs().stream().filter(DatabaseConfig -> DatabaseConfig.getId().equals(dataSourceId)).map(DatabaseConfig::getName).collect(Collectors.toList());
                }
            }
            List<String> finalDataName = dataName;
            List<DatabaseConfig> collect = envConfig.getDatabaseConfigs().stream().filter(DatabaseConfig -> DatabaseConfig.getName().equals(finalDataName.get(0))).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(collect)) {
                return collect.get(0);
            }
        } catch (Exception e) {
            LogUtil.error(e);
        }
        return null;
    }

    public static void replaceFileMetadataId(TestinElement testElement, String newFileMetadataId, String oldFileMetadataId) {
        if (testElement != null && testElement instanceof TestinHTTPSamplerProxy) {
            if (((TestinHTTPSamplerProxy) testElement).getBody() != null && CollectionUtils.isNotEmpty(((TestinHTTPSamplerProxy) testElement).getBody().getKvs())) {
                for (KeyValue keyValue : ((TestinHTTPSamplerProxy) testElement).getBody().getKvs()) {
                    if (CollectionUtils.isNotEmpty(keyValue.getFiles())) {
                        for (BodyFile bodyFile : keyValue.getFiles()) {
                            if (StringUtils.equals(bodyFile.getFileId(), oldFileMetadataId)) {
                                bodyFile.setFileId(newFileMetadataId);
                            }
                        }
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(testElement.getHashTree())) {
                for (TestinElement childElement : testElement.getHashTree()) {
                    replaceFileMetadataId(childElement, newFileMetadataId, oldFileMetadataId);

                }
            }
        }
    }
}
