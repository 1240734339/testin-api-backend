package cn.testin.pojo.dto.request;

import cn.testin.pojo.vo.Response;
import cn.testin.pojo.vo.request.base.TestinElement;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SaveInterfaceRequest {


    private String id;

    private String reportId;

    private String projectId;

    private String name;

    private String path;

    private String protocol;

    private String moduleId;

    private String status;

    private String description;

    private String modulePath;

    private String method;

    private TestinElement request;

    private Response response;

    private String environmentId;

    private String userId;

    private List<String> follows;

    private String remark;

    private String versionId;

    private Schedule schedule;

    private String triggerMode;

    private List<String> bodyUploadIds;

    private String tags;

    //ESB参数。  可为null
    private String esbDataStruct;
    private String backEsbDataStruct;
    private String backScript;

    // 创建新版本时用到的
    private boolean newVersionRemark;
    private boolean newVersionDeps;
    private boolean newVersionCase;
    private boolean newVersionMock;
    // 复制的请求Id
    private String sourceId;
    //是否进入待更新列表
    private Boolean toBeUpdated;

    //同步的内容
    private String triggerUpdate;

    //是否发送特殊通知
    private Boolean sendSpecialMessage;

    //发送信息给case创建人
    private Boolean caseCreator;

    //发送信息给场景创建人
    private Boolean scenarioCreator;

    //是否新建
    private Boolean newCreate;

}
