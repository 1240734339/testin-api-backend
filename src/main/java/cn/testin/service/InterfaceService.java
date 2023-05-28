package cn.testin.service;

import cn.testin.commons.constants.APITestStatus;
import cn.testin.commons.exception.TIException;
import cn.testin.mapper.TestInterfaceDoMapper;
import cn.testin.mapper.TestProjectDoMapper;
import cn.testin.mapper.ext.ExtTestInterfaceDoMapper;
import cn.testin.pojo.domain.TestInterfaceDo;
import cn.testin.pojo.domain.TestInterfaceDoExample;
import cn.testin.pojo.domain.TestInterfaceDoWithBLOBs;
import cn.testin.pojo.domain.TestProjectDo;
import cn.testin.pojo.dto.request.SaveInterfaceRequest;
import cn.testin.pojo.dto.request.TestInterfaceRequest;
import cn.testin.pojo.vo.request.RequestType;
import cn.testin.pojo.vo.result.TestInterfaceResult;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackFor = Exception.class)
public class InterfaceService {

    @Resource
    private TestInterfaceDoMapper testInterfaceDoMapper;

    @Resource
    private ExtTestInterfaceDoMapper extTestInterfaceDoMapper;

    @Resource
    private TestModuleService testModuleService;

    public List<TestInterfaceResult> list(TestInterfaceRequest request) {
        if (request.getModuleId()!=null){
            request.setModuleIds(testModuleService.listModuleIds(request.getModuleId()));
        }
        List<TestInterfaceResult> resList = extTestInterfaceDoMapper.list(request);
        return resList;
    }


    public TestInterfaceDoWithBLOBs add(SaveInterfaceRequest request){
        checkNameExist(request, false);
        TestInterfaceDoWithBLOBs test = new TestInterfaceDoWithBLOBs();
        test.setId(request.getId());
        test.setName(request.getName());
        test.setProtocol(request.getProtocol());
        test.setMethod(request.getMethod());
        test.setPath(request.getPath());
        test.setCreateUserId("");
        test.setProjectId(request.getProjectId());
        request.getRequest().setId(request.getId());
        test.setRequestParam(JSONObject.toJSONString(request.getRequest()));
        test.setCreateTime(System.currentTimeMillis());
        test.setUpdateTime(System.currentTimeMillis());
        test.setStatus(APITestStatus.Underway.name());
        test.setModulePath(request.getModulePath());
        test.setModuleId(request.getModuleId());
        test.setDescription(request.getDescription());
        test.setEnvironmentId(request.getEnvironmentId());
        test.setInterfaceNumber(getNextNum(request.getProjectId()));
        if (StringUtils.isNotEmpty(request.getTags()) && !StringUtils.equals(request.getTags(), "[]")) {
            test.setTags(request.getTags());
        } else {
            test.setTags("");
        }
        if (testInterfaceDoMapper.selectByPrimaryKey(test.getId()) == null) {
            testInterfaceDoMapper.insert(test);
        }
        return testInterfaceDoMapper.selectByPrimaryKey(request.getId());
    }

    private void checkNameExist(SaveInterfaceRequest request, Boolean moduleIdNotExist) {
        TestInterfaceDoExample example = new TestInterfaceDoExample();
        TestInterfaceDoExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(request.getProtocol()) && request.getProtocol().equals(RequestType.HTTP)) {
            criteria.andMethodEqualTo(request.getMethod()).andStatusNotEqualTo("Trash")
                    .andProtocolEqualTo(request.getProtocol()).andPathEqualTo(request.getPath())
                    .andProjectIdEqualTo(request.getProjectId()).andIdNotEqualTo(request.getId())
                    .andNameEqualTo(request.getName());
            if (testInterfaceDoMapper.countByExample(example) > 0) {
                TIException.throwException("名字已存在");
            }
        } else {
            criteria.andProtocolEqualTo(request.getProtocol()).andStatusNotEqualTo("Trash")
                    .andNameEqualTo(request.getName()).andProjectIdEqualTo(request.getProjectId())
                    .andIdNotEqualTo(request.getId()).andNameEqualTo(request.getName());
            if (testInterfaceDoMapper.countByExample(example) > 0) {
                TIException.throwException("名字已存在");
            }
        }
    }

    public int getNextNum(String projectId) {
        TestInterfaceDo testInterfaceDo = extTestInterfaceDoMapper.getNextNum(projectId);
        if (testInterfaceDo == null || testInterfaceDo.getInterfaceNumber() == null) {
            return 100001;
        } else {
            return Optional.of(testInterfaceDo.getInterfaceNumber() + 1).orElse(100001);
        }
    }

    public void delete(String apiId) {
        testInterfaceDoMapper.deleteByPrimaryKey(apiId);
    }


    public TestInterfaceDoWithBLOBs update(SaveInterfaceRequest request) {
        checkNameExist(request, false);
        TestInterfaceDoWithBLOBs test = new TestInterfaceDoWithBLOBs();
        test.setName(request.getName());
        test.setId(request.getId());
        test.setProtocol(request.getProtocol());
        test.setMethod(request.getMethod());
        test.setPath(request.getPath());
        test.setCreateUserId("");
        test.setProjectId(request.getProjectId());
        request.getRequest().setId(request.getId());
        test.setRequestParam(JSONObject.toJSONString(request.getRequest()));
        test.setUpdateTime(System.currentTimeMillis());
        test.setStatus(APITestStatus.Underway.name());
        test.setModulePath(request.getModulePath());
        test.setModuleId(request.getModuleId());
        test.setDescription(request.getDescription());
        test.setEnvironmentId(request.getEnvironmentId());
        test.setInterfaceNumber(getNextNum(request.getProjectId()));
        if (StringUtils.isNotEmpty(request.getTags()) && !StringUtils.equals(request.getTags(), "[]")) {
            test.setTags(request.getTags());
        } else {
            test.setTags("");
        }
        TestInterfaceDoExample example = new TestInterfaceDoExample();
        example.createCriteria().andIdEqualTo(test.getId());
        testInterfaceDoMapper.updateByExampleSelective(test,example);
        return testInterfaceDoMapper.selectByPrimaryKey(request.getId());
    }

    public TestInterfaceResult getById(String id) {
        TestInterfaceRequest request = new TestInterfaceRequest();
        request.setId(id);
        List<TestInterfaceResult> list = extTestInterfaceDoMapper.list(request);
        TestInterfaceResult result = null;
        if (CollectionUtils.isNotEmpty(list)) {
            result = list.get(0);
        }
        return result;
    }

    public List<TestInterfaceResult> listByModule(String moduleId){
        TestInterfaceRequest request = new TestInterfaceRequest();
        request.setModuleId(moduleId);
        return extTestInterfaceDoMapper.list(request);
    }

    public List<TestInterfaceResult> listAllByModule(String moduleId){
        TestInterfaceRequest request = new TestInterfaceRequest();
        List<String> moduleIds = testModuleService.listModuleIds(moduleId);
        request.setModuleIds(moduleIds);
        return extTestInterfaceDoMapper.list(request);
    }

}
