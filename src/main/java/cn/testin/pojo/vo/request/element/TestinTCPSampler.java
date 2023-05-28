package cn.testin.pojo.vo.request.element;

import cn.testin.pojo.dto.request.TcpTreeTableDataStruct;
import cn.testin.pojo.dto.request.KeyValue;
import cn.testin.pojo.vo.request.base.TestinElement;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @title: TestinTCPSampler
 * @description:
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JSONType(typeName = "TCPSampler")
public class TestinTCPSampler extends TestinElement {

    @JSONField(ordinal = 20)
    private String type = "TCPSampler";
    private String clazzName = TestinTCPSampler.class.getCanonicalName();

    @JSONField(ordinal = 21)
    private String classname = "";
    @JSONField(ordinal = 22)
    private String server = "";
    @JSONField(ordinal = 23)
    private String port = "";
    @JSONField(ordinal = 24)
    private String ctimeout = "";
    @JSONField(ordinal = 25)
    private String timeout = "";
    @JSONField(ordinal = 26)
    private boolean reUseConnection = true;
    @JSONField(ordinal = 27)
    private boolean nodelay;
    @JSONField(ordinal = 28)
    private boolean closeConnection;
    @JSONField(ordinal = 29)
    private String soLinger = "";
    @JSONField(ordinal = 30)
    private String eolByte = "";
    @JSONField(ordinal = 31)
    private String username = "";
    @JSONField(ordinal = 32)
    private String password = "";
    @JSONField(ordinal = 33)
    private String request;
    @JSONField(ordinal = 35)
    private List<KeyValue> parameters;
    @JSONField(ordinal = 36)
    private String useEnvironment;
    @JSONField(ordinal = 37)
    private TestinJSR223PreProcessor tcpPreProcessor;
    @JSONField(ordinal = 38)
    private String protocol = "TCP";
    @JSONField(ordinal = 39)
    private String projectId;
    @JSONField(ordinal = 40)
    private String connectEncoding;
    @JSONField(ordinal = 41)
    private String reportType;
    @JSONField(ordinal = 42)
    private List<TcpTreeTableDataStruct> xmlDataStruct;
    @JSONField(ordinal = 43)
    private String jsonDataStruct;
    @JSONField(ordinal = 44)
    private String rawDataStruct;

    @JSONField(ordinal = 45)
    private boolean customizeReq;
}
