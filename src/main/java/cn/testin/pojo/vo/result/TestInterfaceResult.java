package cn.testin.pojo.vo.result;

import cn.testin.pojo.domain.TestInterfaceDoWithBLOBs;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestInterfaceResult extends TestInterfaceDoWithBLOBs {

    private String projectName;

    private String userName;

    private String caseTotal;

    private String caseStatus;

    private String scenarioTotal;

    private String casePassingRate;

    private String deleteUser;

    private String[] ids;

    private String caseType;

    private String scenarioType;

    private String apiType;

    private String versionName;

    private Boolean versionEnable;

    private boolean updated;

}
