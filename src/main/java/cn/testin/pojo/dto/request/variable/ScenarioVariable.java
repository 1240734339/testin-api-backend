package cn.testin.pojo.dto.request.variable;

import cn.testin.commons.constants.VariableTypeConstants;
import cn.testin.pojo.dto.request.BodyFile;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @title: ScenarioVariable
 * @description:
 */
@Data
public class ScenarioVariable {

    /**
     * CONSTANT LIST CSV COUNTER RANDOM
     */
    private String type = VariableTypeConstants.CONSTANT.name();
    private String id;
    private String name;

    /**
     * 常量值，列表值[] ,计数器输出格式，随机数输出格式
     */
    private String value;
    private String description;
    /**
     * csv
     */
    private List<BodyFile> files;
    private String delimiter;
    private boolean quotedData;
    private String encoding;
    /**
     * counter
     */
    private int startNumber;
    private int endNumber;
    private int increment;
    /**
     * random
     */
    private String minNumber;
    private String maxNumber;

    private boolean enable = true;

    public ScenarioVariable() {

    }

    public ScenarioVariable(String key, String value, String description, String type) {
        this.name = key;
        this.value = value;
        this.description = description;
        this.type = type;
    }

    public boolean isConstantValid() {
        if (StringUtils.isEmpty(this.type) || (StringUtils.equals("text", this.type) && StringUtils.isNotEmpty(name)) || (StringUtils.equals(this.type, VariableTypeConstants.CONSTANT.name()) && StringUtils.isNotEmpty(name))) {
            return true;
        }
        return false;
    }

    public boolean isCSVValid() {
        if (StringUtils.equals(this.type, VariableTypeConstants.CSV.name()) && StringUtils.isNotEmpty(name)) {
            return true;
        }
        return false;
    }

    public boolean isListValid() {
        if (StringUtils.equals(this.type, VariableTypeConstants.LIST.name()) && StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(value) && value.indexOf(",") != -1) {
            return true;
        }
        return false;
    }

    public boolean isCounterValid() {
        if (StringUtils.equals(this.type, VariableTypeConstants.COUNTER.name()) && StringUtils.isNotEmpty(name)) {
            return true;
        }
        return false;
    }

    public boolean isRandom() {
        if (StringUtils.equals(this.type, VariableTypeConstants.RANDOM.name()) && StringUtils.isNotEmpty(name)) {
            return true;
        }
        return false;
    }
}
