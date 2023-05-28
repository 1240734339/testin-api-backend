package cn.testin.pojo.dto.request;

import cn.testin.pojo.domain.TestScriptDo;
import cn.testin.pojo.domain.TestScriptDoWithBLOBs;
import cn.testin.pojo.vo.request.base.TestinElement;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SaveScriptRequest extends TestScriptDo {
    private TestinElement request;

    private String description;

    private String response;

    private String crateUserId;

    private List<String> bodyUploadIds;

    private List<String> follows;

    private String versionId;

    //ESB参数。  可为null
    private String esbDataStruct;
    private String backEsbDataStruct;
}
