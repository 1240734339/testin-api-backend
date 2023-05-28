package cn.testin.pojo.vo.request.element;

import cn.testin.pojo.dto.request.extract.TestinExtractCommon;
import cn.testin.pojo.dto.request.extract.TestinExtractJSONPath;
import cn.testin.pojo.dto.request.extract.TestinExtractRegex;
import cn.testin.pojo.dto.request.extract.TestinExtractXPath;
import cn.testin.pojo.vo.request.base.TestinElement;
import cn.testin.pojo.dto.environment.ParameterConfig;
import com.alibaba.fastjson.annotation.JSONType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.jmeter.extractor.JSR223PostProcessor;
import org.apache.jmeter.extractor.RegexExtractor;
import org.apache.jmeter.extractor.XPath2Extractor;
import org.apache.jmeter.extractor.XPathExtractor;
import org.apache.jmeter.extractor.json.jsonpath.JSONPostProcessor;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jorphan.collections.HashTree;

import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

/**
 * @title: TestinExtract
 * @description:
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JSONType(typeName = "Extract")
public class TestinExtract extends TestinElement {

    private String clazzName = TestinExtract.class.getCanonicalName();
    private String xpathType;

    private List<TestinExtractRegex> regex;
    private List<TestinExtractJSONPath> json;
    private List<TestinExtractXPath> xpath;
    private String type = "Extract";

    @Override
    public void toHashTree(HashTree tree, List<TestinElement> hashTree, ParameterConfig config) {
        // 非导出操作，且不是启用状态则跳过执行
        if (!this.isEnable()) {
            return;
        }
        addRequestExtractors(tree, config);
    }

    private void addRequestExtractors(HashTree samplerHashTree, ParameterConfig config) {
        StringJoiner extract = new StringJoiner(";");

        if (CollectionUtils.isNotEmpty(this.getRegex())) {
            this.getRegex().stream().filter(TestinExtractRegex::isValid).forEach(extractRegex ->
                    samplerHashTree.add(regexExtractor(extractRegex, extract))
            );
        }
        if (CollectionUtils.isNotEmpty(this.getXpath())) {
            if (StringUtils.isEmpty(this.xpathType) || StringUtils.equalsAnyIgnoreCase(this.xpathType, "html")) {
                this.getXpath().stream().filter(TestinExtractCommon::isValid).forEach(extractXPath ->
                        samplerHashTree.add(xpathExtractor(extractXPath, extract))
                );
            } else {
                this.getXpath().stream().filter(TestinExtractCommon::isValid).forEach(extractXPath ->
                        samplerHashTree.add(xpath2Extractor(extractXPath, extract))
                );
            }
        }
        if (CollectionUtils.isNotEmpty(this.getJson())) {
            this.getJson().stream().filter(TestinExtractCommon::isValid).forEach(extractJSONPath ->
                    samplerHashTree.add(jsonPostProcessor(extractJSONPath, extract))
            );
        }
        if (Optional.ofNullable(extract).orElse(extract).length() > 0 && !config.isOperating()) {
            JSR223PostProcessor shell = new JSR223PostProcessor();
            shell.setEnabled(true);
            shell.setName(StringUtils.isEmpty(this.getName()) ? "JSR223PostProcessor" : this.getName());
            shell.setProperty(TestElement.TEST_CLASS, JSR223PostProcessor.class.getName());
            shell.setProperty(TestElement.GUI_CLASS, SaveService.aliasToClass("TestBeanGUI"));
            shell.setProperty("cacheKey", false);
            shell.setProperty("script", "cn.testin.extranal.io.utils.JMeterVars.addVars(prev.getResourceId(),vars," + "\"" + extract.toString() + "\"" + ");");
            samplerHashTree.add(shell);
        }
    }

    private RegexExtractor regexExtractor(TestinExtractRegex extractRegex, StringJoiner extract) {

        RegexExtractor extractor = new RegexExtractor();
        extractor.setEnabled(this.isEnable());
        extractor.setName(StringUtils.isNotEmpty(extractRegex.getVariable()) ? extractRegex.getVariable() : this.getName());
        if (StringUtils.isEmpty(extractor.getName())) {
            extractor.setName("RegexExtractor");
        }
        extractor.setName(StringUtils.isNotEmpty(this.getName()) ? this.getName() : " RegexExtractor");
        extractor.setProperty(TestElement.TEST_CLASS, RegexExtractor.class.getName());
        extractor.setProperty(TestElement.GUI_CLASS, SaveService.aliasToClass("RegexExtractorGui"));
        extractor.setRefName(extractRegex.getVariable());
        extractor.setRegex(extractRegex.getExpression());
        extractor.setUseField(extractRegex.getUseHeaders());
        if (extractRegex.isMultipleMatching()) {
            extractor.setMatchNumber(-1);
        }
        extractor.setTemplate("$1$");
        extract.add(extractor.getRefName());

        return extractor;
    }

    /**
     * 处理html
     *
     * @param extractXPath
     * @param extract
     * @return
     */
    private XPathExtractor xpathExtractor(TestinExtractXPath extractXPath, StringJoiner extract) {
        XPathExtractor extractor = new XPathExtractor();
        extractor.setEnabled(this.isEnable());
        extractor.setTolerant(true);
        extractor.setName(StringUtils.isNotEmpty(extractXPath.getVariable()) ? extractXPath.getVariable() : this.getName());
        if (StringUtils.isEmpty(extractor.getName())) {
            extractor.setName("XPathExtractor");
        }
        extractor.setProperty(TestElement.TEST_CLASS, XPathExtractor.class.getName());
        extractor.setProperty(TestElement.GUI_CLASS, SaveService.aliasToClass("XPathExtractorGui"));
        extractor.setRefName(extractXPath.getVariable());
        extractor.setXPathQuery(extractXPath.getExpression());
        if (extractXPath.isMultipleMatching()) {
            extractor.setMatchNumber(-1);
        }
        extract.add(extractor.getRefName());
        return extractor;
    }

    /**
     * 处理xml
     *
     * @param extractXPath
     * @param extract
     * @return
     */
    private XPath2Extractor xpath2Extractor(TestinExtractXPath extractXPath, StringJoiner extract) {
        XPath2Extractor extractor = new XPath2Extractor();
        extractor.setEnabled(this.isEnable());
        extractor.setName(StringUtils.isNotEmpty(extractXPath.getVariable()) ? extractXPath.getVariable() : this.getName());
        if (StringUtils.isEmpty(extractor.getName())) {
            extractor.setName("XPath2Extractor");
        }
        extractor.setProperty(TestElement.TEST_CLASS, XPathExtractor.class.getName());
        extractor.setProperty(TestElement.GUI_CLASS, SaveService.aliasToClass("XPath2ExtractorGui"));
        extractor.setRefName(extractXPath.getVariable());
        extractor.setXPathQuery(extractXPath.getExpression());
        if (extractXPath.isMultipleMatching()) {
            extractor.setMatchNumber(-1);
        }
        extract.add(extractor.getRefName());
        return extractor;
    }

    private JSONPostProcessor jsonPostProcessor(TestinExtractJSONPath extractJSONPath, StringJoiner extract) {
        JSONPostProcessor extractor = new JSONPostProcessor();
        extractor.setEnabled(this.isEnable());
        extractor.setName(StringUtils.isNotEmpty(extractJSONPath.getVariable()) ? extractJSONPath.getVariable() : this.getName());
        if (StringUtils.isEmpty(extractor.getName())) {
            extractor.setName("JSONPostProcessor");
        }
        /*extractor.setName(StringUtils.isNotEmpty(this.getName()) ? this.getName() : " JSONExtractor");*/
        extractor.setProperty(TestElement.TEST_CLASS, JSONPostProcessor.class.getName());
        extractor.setProperty(TestElement.GUI_CLASS, SaveService.aliasToClass("JSONPostProcessorGui"));
        extractor.setRefNames(extractJSONPath.getVariable());
        extractor.setJsonPathExpressions(extractJSONPath.getExpression());
        extractor.setComputeConcatenation(extractJSONPath.isMultipleMatching());
        if (extractJSONPath.isMultipleMatching()) {
            extractor.setMatchNumbers("-1");
        }
        extract.add(extractor.getRefNames());
        return extractor;
    }
}
