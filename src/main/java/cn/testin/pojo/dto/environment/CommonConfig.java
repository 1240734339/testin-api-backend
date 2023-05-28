package cn.testin.pojo.dto.environment;

import cn.testin.pojo.dto.request.variable.ScenarioVariable;
import lombok.Data;

import java.util.List;

@Data
public class CommonConfig {
    private List<ScenarioVariable> variables;
    private boolean enableHost;
    private List<Host> hosts;
    private int requestTimeout;
    private int responseTimeout;
}
