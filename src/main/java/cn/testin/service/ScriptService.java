package cn.testin.service;

import cn.testin.commons.constants.APITestStatus;
import cn.testin.commons.exception.TIException;
import cn.testin.mapper.TestInterfaceDoMapper;
import cn.testin.mapper.TestScriptDoMapper;
import cn.testin.mapper.ext.ExtTestScriptDoMapper;
import cn.testin.pojo.domain.TestInterfaceDoWithBLOBs;
import cn.testin.pojo.domain.TestScriptDo;
import cn.testin.pojo.domain.TestScriptDoWithBLOBs;
import cn.testin.pojo.dto.TestScriptDTO;
import cn.testin.pojo.dto.request.SaveScriptRequest;
import cn.testin.pojo.dto.request.TestScriptRequest;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ScriptService {

    @Resource
    private ExtTestScriptDoMapper extTestScriptDoMapper;

    @Resource
    private TcpApiParamService tcpApiParamService;

    @Resource
    private TestScriptDoMapper testScriptDoMapper;

    @Resource
    private TestInterfaceDoMapper testInterfaceDoMapper;

    public List<TestScriptDTO> listSimple(TestScriptRequest request) {
        List<TestScriptDTO> apiTestCases = extTestScriptDoMapper.listSimple(request);
        return apiTestCases;
    }

    public TestScriptDo add(SaveScriptRequest request){
        checkNameExist(request);
        request.setRequest(tcpApiParamService.handleTcpRequest(request.getRequest()));
        final TestScriptDoWithBLOBs test = new TestScriptDoWithBLOBs();
        test.setId(request.getId());
        test.setName(request.getName());
        test.setStatus(request.getStatus());
        test.setProtocol(request.getProtocol());
        test.setPath(request.getPath());
        test.setMethod(request.getMethod());
        if (StringUtils.isEmpty(request.getStatus())) {
            test.setStatus(APITestStatus.Underway.name());
        }
        test.setInterfaceId(request.getInterfaceId());
        test.setProjectId(request.getProjectId());
        test.setRequest(JSONObject.toJSONString(request.getRequest()));
        test.setCreateTime(System.currentTimeMillis());
        test.setUpdateTime(System.currentTimeMillis());
        test.setDescription(request.getDescription());
        test.setScriptNumber(getNextNum(request.getInterfaceId()));
        if (StringUtils.equals("[]", request.getTags())) {
            test.setTags("");
        } else {
            test.setTags(request.getTags());
        }
        TestScriptDoWithBLOBs apiTestCaseWithBLOBs = testScriptDoMapper.selectByPrimaryKey(test.getId());
        if (apiTestCaseWithBLOBs == null) {
            testScriptDoMapper.insert(test);
        }
        return test;
    }

    public void checkNameExist(SaveScriptRequest request) {
        List<TestScriptDo> testScriptDos = extTestScriptDoMapper.checkName(request);
        if (CollectionUtils.isNotEmpty(testScriptDos)) {
            TIException.throwException("load_test_already_exists");
        }
    }

    public int getNextNum(String interfaceId) {
        TestScriptDo testScriptDo = extTestScriptDoMapper.getNextNum(interfaceId);
        TestInterfaceDoWithBLOBs testInterfaceDoWithBLOBs = testInterfaceDoMapper.selectByPrimaryKey(interfaceId);
        if (testScriptDo == null) {
            int n = 10000;
            if(testInterfaceDoWithBLOBs!=null){
                n = testInterfaceDoWithBLOBs.getInterfaceNumber();
            }
            return n * 1000 + 1;
        } else {
            return (testScriptDo.getScriptNumber() + 1);
        }
    }

    public TestScriptDo update(SaveScriptRequest request){
        checkNameExist(request);
        request.setRequest(tcpApiParamService.handleTcpRequest(request.getRequest()));
        final TestScriptDoWithBLOBs test = new TestScriptDoWithBLOBs();
        test.setId(request.getId());
        test.setName(request.getName());
        test.setStatus(request.getStatus());
        test.setProtocol(request.getProtocol());
        test.setPath(request.getPath());
        test.setMethod(request.getMethod());
        if (StringUtils.isEmpty(request.getStatus())) {
            test.setStatus(APITestStatus.Underway.name());
        }
        test.setInterfaceId(request.getInterfaceId());
        test.setProjectId(request.getProjectId());
        test.setRequest(JSONObject.toJSONString(request.getRequest()));
        test.setUpdateTime(System.currentTimeMillis());
        test.setDescription(request.getDescription());
        test.setScriptNumber(getNextNum(request.getInterfaceId()));
        if (StringUtils.equals("[]", request.getTags())) {
            test.setTags("");
        } else {
            test.setTags(request.getTags());
        }
        TestScriptDoWithBLOBs apiTestCaseWithBLOBs = testScriptDoMapper.selectByPrimaryKey(test.getId());
        if (apiTestCaseWithBLOBs == null) {
            testScriptDoMapper.updateByPrimaryKeySelective(test);
        }
        return test;
    }

    public void delete(String testId) {
        testScriptDoMapper.deleteByPrimaryKey(testId);
    }
}
