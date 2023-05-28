package cn.testin.service;

import cn.testin.commons.utils.LogUtil;
import cn.testin.pojo.dto.request.KeyValue;
import cn.testin.pojo.dto.request.TcpTreeTableDataStruct;
import cn.testin.pojo.dto.request.parse.TcpTreeTableDataParser;
import cn.testin.pojo.vo.request.base.TestinElement;
import cn.testin.pojo.vo.request.element.TestinTCPSampler;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @title: TcpApiParamService
 * @description:
 */
@Service
public class TcpApiParamService {

    public void checkTestElement(TestinElement testElement) {
        try {
            if (testElement != null) {
                if (testElement instanceof TestinTCPSampler) {
                    LogUtil.info("处理TCP请求【 " + testElement.getId() + " 】开始");
//                    this.handleTcpRequest(testElement);
                    LogUtil.info("处理TCP请求【 " + testElement.getId() + " 】完成");
                }
                if (testElement.getHashTree() != null) {
                    for (TestinElement itemElement : testElement.getHashTree()) {
                        this.checkTestElement(itemElement);
                    }
                }
            }

        } catch (Exception e) {
            LogUtil.error(e);
        }
    }

    public TestinTCPSampler handleTcpRequest(TestinElement testElement) {
        TestinTCPSampler tcpSampler = null;
        try {
            if (testElement instanceof TestinTCPSampler) {
                tcpSampler = (TestinTCPSampler) testElement;
                String protocol = tcpSampler.getProtocol();
                    String reportType = tcpSampler.getReportType();
                    if (StringUtils.isNotEmpty(reportType)) {
                        switch (reportType) {
                            case "raw":
                                tcpSampler.setRequest(tcpSampler.getRawDataStruct());
                                break;
                            case "xml":
                                String xmlDataStruct = this.genValueFromEsbDataStructByParam(tcpSampler.getXmlDataStruct());
                                tcpSampler.setRequest(xmlDataStruct);
                                break;
                            case "json":
                                tcpSampler.setRequest(tcpSampler.getJsonDataStruct());
                                break;
                        }
                    }
                }
        } catch (Exception e) {
            LogUtil.error(e);
        }
        return tcpSampler;
    }

    //通过报文模版中的变量参数，解析报文数据结构，生成对应的xml数据
    private String genValueFromEsbDataStructByParam(List<TcpTreeTableDataStruct> dataStructRequestList) {
        String returnValue = TcpTreeTableDataParser.treeTableData2Xml(dataStructRequestList);
        return returnValue;
    }
}
